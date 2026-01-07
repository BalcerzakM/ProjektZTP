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

        panel.onFlashCard(() -> startMode(new FlashCardMode(), context));
        //panel.onConnect(() -> startMode(new ConnectMode(context.getCurrentWordSet()), context));
        panel.onConnect(() -> {
                    new ConnectController(
                            frame,
                            model,
                            context.getCurrentWordSet()
                    ).start();
                });
        panel.onMillionaire(() -> startMode(new MillionaireMode(), context));
        panel.onTyping(() -> startMode(new TypingMode(), context));


        frame.setView(panel, "LEARNING_SESSION");
        return null;
        //return AppState.LearningSession; // GUI steruje dalej
    }

    //ta metoda jest do synchronicznych mode(terminal)
    private void startMode(LearningMode mode, AppContext context) {
        SessionStatistics stats = new SessionStatistics();
        model.registerObserver(stats);

        model.setMode(mode);
        model.getMode().start(context.getCurrentWordSet(), model);

        model.unregisterObserver(stats);

        JOptionPane.showMessageDialog(
                frame,
                stats.showStatistics(),
                "Session summary",
                JOptionPane.INFORMATION_MESSAGE
        );
        //STATYSTYKI RACZEJ DO KONTROLERA
    }
}

