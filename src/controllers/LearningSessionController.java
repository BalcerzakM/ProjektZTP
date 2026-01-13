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
    //private LearningSessionView view;

    @Override
    public void run(AppContext context, AppRouter router) {

        model.registerObserver(context.getReviewScheduler());

        //view = new LearningSessionView(context.getCurrentWordSet().getName());

        LearningSessionPanel panel = new LearningSessionPanel();

        panel.onFlashCard(() -> {
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new FlashCardController(
                    frame,
                    model,
                    context.getCurrentWordSet(),
                    () -> {
                        model.unregisterObserver(stats);
                        SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
                        sessionStatsPanel.setStatistics(stats);
                        sessionStatsPanel.showInDialog(frame);
                        frame.setView(panel,"MENU");
                    }
            ).start();
        });

        panel.onConnect(() -> {
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new ConnectController(
                    frame,
                    model,
                    context.getCurrentWordSet(),
                    () -> {
                        model.unregisterObserver(stats);
                        SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
                        sessionStatsPanel.setStatistics(stats);
                        sessionStatsPanel.showInDialog(frame);
                        frame.setView(panel,"MENU");
                    }
            ).start();
        });

        panel.onMillionaire(() -> {
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new MillionaireController(
                    frame,
                    model,
                    context.getCurrentWordSet(),
                    10,
                    () -> {
                        model.unregisterObserver(stats);
                        SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
                        sessionStatsPanel.setStatistics(stats);
                        sessionStatsPanel.showInDialog(frame);
                        frame.setView(panel,"MENU");
                    }
            ).start();
        });

        panel.onTyping(() ->{
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new TypingController(
                frame,
                model,
                context.getCurrentWordSet(), 4,
                () -> {
                    model.unregisterObserver(stats);
                    SessionStatisticsPanel sessionStatsPanel = new SessionStatisticsPanel();
                    sessionStatsPanel.setStatistics(stats);
                    sessionStatsPanel.showInDialog(frame);
                    frame.setView(panel,"MENU");
                }
            ).start();
        });

        panel.onBack(() -> {
            frame.switchState(AppState.ChoosingDatabase);
        });


        frame.setView(panel, "LEARNING_SESSION");
        return null;
        //return AppState.LearningSession; // GUI steruje dalej
    }


}

