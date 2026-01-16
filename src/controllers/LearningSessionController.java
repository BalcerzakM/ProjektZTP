package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.LanguageCERFLevel;
import events.SessionEventBus;
import models.WordSet;
import services.observers.SessionStatistics;
import services.observers.ReviewScheduler;
import models.LearningSession;
import views.DatabaseSelectionPanel;
import views.LearningSessionPanel;
import views.SessionStatisticsPanel;

import javax.swing.*;

public class LearningSessionController implements Controller {
    private final AppRouter router;
    private final AppContext context;
    private final LearningSession model;
    private final DatabaseSelectionPanel dbSelectionPanel;
    private final LearningSessionPanel learningSessionPanel;
    private final SessionStatistics stats = new SessionStatistics();

    public LearningSessionController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
        this.model = new LearningSession();
        this.dbSelectionPanel = new DatabaseSelectionPanel(this.context.getDatabaseNamesList());
        this.learningSessionPanel = new LearningSessionPanel();
        initDbSelectionLogic();
        initLearningSessionLogic();
    }

    private void initDbSelectionLogic() {
        dbSelectionPanel.onLoadBtn(e -> handleLoad());
        dbSelectionPanel.onReviewBtn(e -> handleReview());
    }

    private void initLearningSessionLogic() {
        learningSessionPanel.onFlashCard(() -> new FlashCardController(
                router,
                model,
                context.getCurrentWordSet(),
                onFinishSession()
        ).start());

        learningSessionPanel.onConnect(() -> new ConnectController(
                router,
                model,
                context.getCurrentWordSet(),
                onFinishSession(),
                stats.getEventBus()
        ).start());

        learningSessionPanel.onMillionaire(() -> new MillionaireController(
                router,
                model,
                context.getCurrentWordSet(),
                10,
                onFinishSession(),
                stats.getEventBus()
        ).start());

        learningSessionPanel.onTyping(() -> new TypingController(
                router,
                model,
                context.getCurrentWordSet(),
                10,
                onFinishSession(),
                stats.getEventBus()

        ).start());

        learningSessionPanel.onBack(() -> {
            model.unregisterObserver(stats);
            context.getReviewScheduler().detachEventBus(stats.getEventBus());

            int result = learningSessionPanel.showQuestionMessage();

            if (result == JOptionPane.YES_OPTION) {
                model.flushMementos();
                context.setCurrentWordSet(null);
                router.switchState(AppState.MainMenu);
            }
        });

    }

    private Runnable onFinishSession() {
        return () -> {
        if (stats.hasAnyData()) {
            context.getCurrentUserStatistics().addToStatistics(stats);

            int progress = context.getCurrentUserStatistics().calculateLevelProgress();
            context.getCurrentUser().updateLanguageLevel(progress);

            SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
            sessionStatsPanel.setStatistics(stats);
            sessionStatsPanel.showInDialog(router.getMainFrame());
            stats.resetStatistics();
        }
        router.setPanel(learningSessionPanel, "LEARNING_SESSION");
        };
    }

    @Override
    public void run() {
            ReviewScheduler reviewScheduler = context.getReviewScheduler();
            SessionEventBus eventBus = new SessionEventBus();
            reviewScheduler.attachEventBus(eventBus);
            model.registerObserver(reviewScheduler);

            model.registerObserver(stats);
            router.setPanel(dbSelectionPanel, "DB_SELECTION");
    }

    private void handleLoad() {
        String selected = dbSelectionPanel.getSelectedFile();

        if (selected == null) {
            dbSelectionPanel.showError("Wybierz plik z listy.");
            return;
        }

        try {
            WordSet ws = context.loadWordSetSecurely(selected);
            context.setCurrentWordSet(ws);
            router.setPanel(learningSessionPanel, "LEARNING_SESSION");
        } catch (Exception e) {
            dbSelectionPanel.showError(e.getMessage());
        }
    }

    private void handleReview() {

        if (context.getReviewScheduler().getReviewWords().size() < 5) {
            JOptionPane.showMessageDialog(
                    dbSelectionPanel,
                    "Za mało słów do powtórzenia!",
                    "Review",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        WordSet review = new WordSet(
                "review",
                context.getReviewScheduler().loadReviewWords(),
                LanguageCERFLevel.A1
        );
        System.out.println(review.getWords().size());

        context.setCurrentWordSet(review);
        router.setPanel(learningSessionPanel, "LEARNING_SESSION");
    }
}

