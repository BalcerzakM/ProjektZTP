package controllers;

import LearningModes.*;
import app.AppContext;
import app.AppState;
import observers.SessionStatistics;
import models.LearningSession;
import views.MainFrame;
import views.LearningSessionPanel;

import javax.swing.*;

public class LearningSessionController implements Controller {

    private LearningSession model = new LearningSession();
    //private LearningSessionView view;
    private final MainFrame frame;

    public LearningSessionController(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public AppState run(AppContext context) {

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
                        JOptionPane.showMessageDialog(
                                frame,
                                stats.showStatistics(),
                                "Session summary",
                                JOptionPane.INFORMATION_MESSAGE
                        );
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
                            context.getCurrentWordSet()
                    ).start();
                });

        panel.onMillionaire(() -> {
            SessionStatistics stats = new SessionStatistics();
            model.registerObserver(stats);
            new MillionaireController(
                    frame,
                    model,
                    context.getCurrentWordSet(),
                    4,
                    () -> {
                        model.unregisterObserver(stats);
                        JOptionPane.showMessageDialog(
                                frame,
                                stats.showStatistics(),
                                "Session summary",
                                JOptionPane.INFORMATION_MESSAGE
                        );
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
                    JOptionPane.showMessageDialog(
                            frame,
                            stats.showStatistics(),
                            "Session summary",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    frame.setView(panel,"MENU");
                }
        ).start();});


        frame.setView(panel, "LEARNING_SESSION");
        return null;
        //return AppState.LearningSession; // GUI steruje dalej
    }


}

