package controllers;

import LearningModes.MillionaireMode;
import LearningModes.ModeType;
import app.AppRouter;
import models.LearningSession;
import models.WordSet;
import views.MillionairePanel;

import javax.swing.*;

public class MillionaireController {
    private static final ModeType MODE_KEY = ModeType.MILLIONAIRE;

    private final AppRouter router;
    private final LearningSession session;
    private final MillionaireMode mode;

    private final Runnable onFinish;

    public MillionaireController(
            AppRouter router,
            LearningSession session,
            WordSet wordSet,
            int questions,
            Runnable onFinish
    ) {
        this.router = router;
        this.session = session;
        this.mode = new MillionaireMode(wordSet, questions);
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

        router.setPanel(panel, "MILLIONAIRE");
    }

    private void handleAnswer(String selected) {
        boolean correct = mode.checkAnswer(selected);
        session.notifyObservers(mode.getWord(), correct);

        JOptionPane.showMessageDialog(
                router.getMainFrame(),
                correct ? "Dobrze!" : "Źle! \n Poprawna Odpowiedź to: "+ mode.getWord().getTarget(),
                "Odpowiedź",
                correct
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE
        );
        mode.advance();
        showNext();
    }

    private void saveAndExit() {
        session.setCurrentIndex(mode.getCurrentQuestionIndex());
        session.saveMemento(MODE_KEY);
        onFinish.run();
    }
}
