package controllers;

import LearningModes.MillionaireMode;
import LearningModes.ModeType;
import models.LearningSession;
import models.WordSet;
import views.MainFrame;
import views.MillionairePanel;

import javax.swing.*;

public class MillionaireController {

    private final MainFrame frame;
    private final LearningSession session;
    private final MillionaireMode mode;
    private final ModeType enumMode = ModeType.MILLIONAIRE;
    private final Runnable onFinish;

    public MillionaireController(
            MainFrame frame,
            LearningSession session,
            WordSet wordSet,
            int questions,
            Runnable onFinish
    ) {
        this.frame = frame;
        this.session = session;
        this.mode = new MillionaireMode(wordSet, questions);
        this.onFinish = onFinish;
    }

    public void start() {
        if (session.hasMemento(enumMode)) {
            session.restore(enumMode);
            mode.setCurrentQuestionIndex(session.getCurrentIndex());
            mode.setOptions(session.getAnswers());
        }
        showNext();
    }

    private void showNext() {
        if (!mode.hasNext()) {
            onFinish.run();
            return;
        }

        mode.nextQuestion();

        MillionairePanel panel = new MillionairePanel();
        panel.setQuestion(
                "Jakie jest tłumaczenie słowa: \"" +
                        mode.getWord().getSource() + "\""
        );

        panel.setOptions(mode.getOptions(), this::handleAnswer);

        panel.setProgress(
                mode.getCurrentQuestionIndex(),
                mode.getTotalQuestions()
        );

        panel.setOnBack(this::saveAndExit);

        frame.setView(panel, "MILLIONAIRE");
    }

    private void handleAnswer(String selected) {
        boolean correct = mode.checkAnswer(selected);
        session.notifyObservers(mode.getWord(), correct);

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

    private void saveAndExit() {
        session.setCurrentIndex(mode.getCurrentQuestionIndex());
        session.setAnswers(mode.getOptions());
        session.saveMemento(ModeType.MILLIONAIRE);
        onFinish.run();
    }
}
