package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import observers.SessionStatistics;
import models.LearningSession;
import views.MainFrame;
import views.LearningSessionPanel;
import views.SessionStatisticsPanel;

import javax.swing.*;
import java.awt.*;

public class LearningSessionController implements Controller {

    private LearningSession model = new LearningSession();
    AppRouter router;
    AppContext context;

    public LearningSessionController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
    }

    @Override
    public void run() {

        model.registerObserver(context.getReviewScheduler());

        SessionStatistics stats = new SessionStatistics();
        model.registerObserver(stats);

        LearningSessionPanel panel = new LearningSessionPanel();

        Runnable onFinishSession = () -> {
            if (stats.hasAnyData()) {
                context.getCurrentUserStatistics().addToStatistics(stats);

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
                4,
                onFinishSession
            ).start();
        });

        panel.onBack(() -> {
            model.unregisterObserver(stats);
            router.switchState(AppState.StartMenu);
        });


        router.setPanel(panel, "LEARNING_SESSION");
    }
}

