package controllers;

import LearningModes.ModeType;
import LearningModes.TypingMode;
import app.AppRouter;
import models.LearningSession;
import models.Word;
import models.WordSet;
import views.MainFrame;
import views.TypingPanel;

import javax.swing.*;

public class TypingController {

    private static final ModeType MODE_KEY = ModeType.TYPING;

    private final AppRouter router;
    private final LearningSession session;
    private final TypingMode mode;
    private final Runnable onFinish;

    private Word currentWord;

    public TypingController(
            AppRouter router,
            LearningSession session,
            WordSet wordSet,
            int questions,
            Runnable onFinish
    ) {
        this.router = router;
        this.session = session;
        this.mode = new TypingMode(wordSet, questions);
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

        showNext();
    }

    private void showNext() {
        if (!mode.hasNext()) {
            session.removeMemento(MODE_KEY);
            session.resetSeed();
            onFinish.run();
            return;
        }

        currentWord = mode.nextWord();

        TypingPanel panel = new TypingPanel();
        panel.setWord(currentWord.getSource());
        panel.setProgress(
                mode.getIndex() ,
                mode.getTotalQuestions()
        );

        panel.onCheck(() -> handleAnswer(panel));
        panel.setOnBack(this::saveAndExit);

        router.setPanel(panel, "TYPING");
    }

    private void handleAnswer(TypingPanel panel) {
        boolean correct = mode.checkAnswer(currentWord, panel.getInput());
        session.notifyObservers(currentWord, correct);

        JOptionPane.showMessageDialog(
                panel,
                correct ? "Dobrze!" : "Źle! \n Poprawna Odpowiedź to: "+ currentWord.getTarget(),
                "Odpowiedź",
                correct
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE
        );
        mode.advance();

        showNext();
    }

    private void saveAndExit() {
        session.setCurrentIndex(mode.getIndex());
        session.saveMemento(MODE_KEY);
        onFinish.run();
    }
}
