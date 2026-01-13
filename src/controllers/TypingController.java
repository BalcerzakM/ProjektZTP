package controllers;

import LearningModes.TypingMode;
import models.LearningSession;
import models.Word;
import models.WordSet;
import views.MainFrame;
import views.TypingPanel;

import javax.swing.*;

public class TypingController {

    private final MainFrame frame;
    private final LearningSession session;
    private final TypingMode mode;
    private final Runnable onFinish;

    private Word currentWord;

    public TypingController(
            MainFrame frame,
            LearningSession session,
            WordSet wordSet,
            int questions,
            Runnable onFinish
    ) {
        this.frame = frame;
        this.session = session;
        this.mode = new TypingMode(wordSet, questions);
        this.onFinish = onFinish;
    }

    public void start() {
        showNext();
    }

    private void showNext() {
        if (!mode.hasNext()) {
            onFinish.run();
            return;
        }

        currentWord = mode.nextWord();

        TypingPanel panel = new TypingPanel();
        panel.setWord(currentWord.getSource());

        panel.onCheck(() -> handleAnswer(panel));

        frame.setView(panel, "TYPING");
    }

    private void handleAnswer(TypingPanel panel) {
        boolean correct = mode.checkAnswer(currentWord, panel.getInput());
        session.notifyObservers(currentWord, correct);

        JOptionPane.showMessageDialog(
                frame,
                correct ? "Dobrze!" : "Źle!",
                "Odpowiedź",
                correct
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE
        );

        showNext();
    }
}
