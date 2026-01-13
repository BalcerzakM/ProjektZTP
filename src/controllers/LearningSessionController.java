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

    public LearningSessionController(AppRouter router) {
        this.router = router;
    }

    @Override
    public void run(AppContext context) {

        model.registerObserver(context.getReviewScheduler());


        LearningSessionPanel panel = new LearningSessionPanel();

        panel.onFlashCard(() -> {
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new FlashCardController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    () -> {
                        model.unregisterObserver(stats);
                        SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
                        sessionStatsPanel.setStatistics(stats);
                        sessionStatsPanel.showInDialog(router.getMainFrame());
                        router.setView(panel,"CHOSING LEARNING MODE");
                    }
            ).start();
        });

        panel.onConnect(() -> {
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new ConnectController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    () -> {
                        model.unregisterObserver(stats);
                        SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
                        sessionStatsPanel.setStatistics(stats);
                        sessionStatsPanel.showInDialog(router.getMainFrame());
                        router.setView(panel,"CHOSING LEARNING MODE");
                    }
            ).start();
        });

        panel.onMillionaire(() -> {
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new MillionaireController(
                    router,
                    model,
                    context.getCurrentWordSet(),
                    10,
                    () -> {
                        model.unregisterObserver(stats);
                        SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
                        sessionStatsPanel.setStatistics(stats);
                        sessionStatsPanel.showInDialog(router.getMainFrame());
                        router.setView(panel,"MENU");
                    }
            ).start();
        });

        panel.onTyping(() ->{
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new TypingController(
                router,
                model,
                context.getCurrentWordSet(), 4,
                () -> {
                    model.unregisterObserver(stats);
                    SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
                    sessionStatsPanel.setStatistics(stats);
                    sessionStatsPanel.showInDialog(router.getMainFrame());
                    router.setView(panel,"MENU");
                }
            ).start();
        });

        panel.onBack(() -> {
            router.switchState(AppState.MainMenu);
        });


        router.setView(panel, "LEARNING_SESSION");
    }
}

