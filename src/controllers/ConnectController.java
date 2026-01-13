package controllers;

import LearningModes.ModeType;
import app.AppState;
import models.LearningSession;
import models.*;
import views.MainFrame;
import views.ConnectPanel;
import LearningModes.ConnectMode;

import javax.swing.*;

public class ConnectController {
    private static final ModeType MODE_KEY = ModeType.CONNECT;
    private final MainFrame frame;
    private final LearningSession session;
    private final ConnectMode mode;
    private final Runnable onFinish;

    public ConnectController(MainFrame frame,
                             LearningSession session,
                             WordSet wordSet, Runnable onFinish) {
        this.frame = frame;
        this.session = session;
        this.mode = new ConnectMode(wordSet);
        this.onFinish = onFinish;
    }


    public void start() {

        if (session.hasMemento(MODE_KEY)) {
            session.restore(MODE_KEY);
            mode.restore(
                    session.getCurrentIndex(),
                    session.getSeed()
            );
        } else {
            session.initSeedIfNeeded();
            mode.startNew(session.getSeed());
        }

        show();
    }

    private void show() {
        ConnectPanel panel = new ConnectPanel(
                mode.getLeftSources().stream().map(Word::getSource).toList(),
                mode.getRightTargets()
        );

        panel.onCheck(() -> handleCheck(panel));
        panel.setOnBack(this::saveAndExit);
        frame.setView(panel, "CONNECT");
    }

    private void handleCheck(ConnectPanel panel) {
        int l = panel.getLeftIndex();
        int r = panel.getRightIndex();

        if (l == -1 || r == -1) return;

        if (mode.check(l, r)) {
            Word w = mode.removePair(l, r);
            session.notifyObservers(w, true);

            if (mode.isFinished()) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Wszystkie połączone!",
                        "Koniec",
                        JOptionPane.INFORMATION_MESSAGE
                );
                session.removeMemento(MODE_KEY);
                session.resetSeed();
                onFinish.run();
                return;
            }
        } else {
            session.notifyObservers(
                    mode.getLeftSources().get(l), false);
            JOptionPane.showMessageDialog(frame,
                    "Źle, spróbuj ponownie");
        }

        panel.updateLists(
                mode.getLeftSources().stream().map(Word::getSource).toList(),
                mode.getRightTargets()
        );
    }

    private void saveAndExit() {
        session.setCurrentIndex(mode.getProgress());
        //session.setAnswers(mode.getOptions());
        session.saveMemento(ModeType.CONNECT);
        onFinish.run();
    }
}
