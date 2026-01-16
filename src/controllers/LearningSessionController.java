package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.WordSet;
import observers.SessionStatistics;
import models.LearningSession;
import views.DatabaseSelectionPanel;
import views.MainFrame;
import views.LearningSessionPanel;
import views.SessionStatisticsPanel;

import javax.swing.*;
import java.awt.*;

public class LearningSessionController implements Controller {
    private final AppRouter router;
    private final AppContext context;
    private final LearningSession model = new LearningSession();
    private final DatabaseSelectionPanel dbSelectionPanel;
    private final LearningSessionPanel learningSessionPanel;

    private LearningSession model = new LearningSession();
    AppRouter router;
    AppContext context;

    public LearningSessionController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
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
        router.setPanel(dbSelectionPanel, "DB_SELECTION");

        model.registerObserver(context.getReviewScheduler());

        SessionStatistics stats = new SessionStatistics();
        model.registerObserver(stats);

        LearningSessionPanel panel = new LearningSessionPanel();

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
            router.setPanel(panel,"LEARNING_SESSION");
        };

        panel.onFlashCard(() -> {
            new FlashCardController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    onFinishSession
            ).start();
        });

        panel.onConnect(() -> {
            new ConnectController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    onFinishSession
            ).start();
        });

        panel.onMillionaire(() -> {
            new MillionaireController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    10,
                    onFinishSession
            ).start();
        });

        panel.onTyping(() ->{
            new TypingController(
                router,
                model,
                context.getCurrentWordSet(),
                10,
                onFinishSession
            ).start();
        });

        panel.onBack(() -> {
            model.unregisterObserver(stats);
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
                router.switchState(AppState.StartMenu);
            }
        });


        router.setPanel(learningSessionPanel, "LEARNING_SESSION");
    }

    private void handleLoad() {
        String selected = dbSelectionPanel.getSelectedFile();

        if (selected == null) {
            dbSelectionPanel.showError("Wybierz plik z listy.");
            return;
        }

        WordSet ws = context.getNewWordSetFromDb(selected);
        context.setCurrentWordSet(ws);
        router.setPanel(learningSessionPanel, "MENU");
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
                "review"
        );
        System.out.println(review.getWords().size());

        context.setCurrentWordSet(review);
        router.setPanel(learningSessionPanel, "LEARNING_SESSION");
    }
}

