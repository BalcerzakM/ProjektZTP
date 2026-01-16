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

    public LearningSessionController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
        this.model = new LearningSession();
        this.dbSelectionPanel = new DatabaseSelectionPanel(this.context.getDatabaseNamesList());
        this.learningSessionPanel = new LearningSessionPanel();
        initDbSelectionLogic();
    }

    private void initDbSelectionLogic() {
        dbSelectionPanel.onLoadBtn(e -> handleLoad());
        dbSelectionPanel.onReviewBtn(e -> handleReview());
    }

    @Override
    public void run() {
        if(!context.isDatabaseSelected()) {
            router.setPanel(dbSelectionPanel, "DB_SELECTION");
        } else {
            SessionEventBus eventBus = new SessionEventBus();
            ReviewScheduler reviewScheduler = context.getReviewScheduler();

            reviewScheduler.attachEventBus(eventBus);
            model.registerObserver(reviewScheduler);


            SessionStatistics stats = new SessionStatistics(eventBus);
            model.registerObserver(stats);

            Runnable onFinishSession = () -> {
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

            learningSessionPanel.onFlashCard(() -> new FlashCardController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    onFinishSession
            ).start());

            learningSessionPanel.onConnect(() -> new ConnectController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    onFinishSession,
                    eventBus
            ).start());

            learningSessionPanel.onMillionaire(() -> new MillionaireController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    10,
                    onFinishSession,
                    eventBus
            ).start());

            learningSessionPanel.onTyping(() -> new TypingController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    10,
                    onFinishSession,
                    eventBus
            ).start());

            learningSessionPanel.onBack(() -> {
                model.unregisterObserver(stats);
                reviewScheduler.detachEventBus(eventBus);

                int result = JOptionPane.showOptionDialog(
                        router.getMainFrame(),
                        "Czy na pewno chcesz skończyć lekcję?",
                        "Zakończyć lekcję?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new Object[]{"TAK", "NIE"},
                        "NIE"
                );

                if (result == JOptionPane.YES_OPTION) {
                    model.flushMementos();
                    context.setCurrentWordSet(null);
                    router.switchState(AppState.MainMenu);
                }
            });


            router.setPanel(learningSessionPanel, "LEARNING_SESSION");
        }
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
            router.switchState(AppState.LearningSession);
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
        router.switchState(AppState.LearningSession);
    }
}

