package controllers;

import LearningModes.MillionaireMode;
import LearningModes.ModeType;
import app.AppRouter;
import events.SessionEventBus;
import events.SessionFeedbackBuffer;
import models.LearningSession;
import models.WordSet;
import views.MillionairePanel;

import javax.swing.*;
import java.util.List;

public class MillionaireController {
    private static final ModeType MODE_KEY = ModeType.MILLIONAIRE;

    private final AppRouter router;
    private final LearningSession session;
    private final MillionaireMode mode;

    private final SessionEventBus eventBus;
    private final SessionFeedbackBuffer feedbackBuffer = new SessionFeedbackBuffer();

    private final Runnable onFinish;

    public MillionaireController(
            AppRouter router,
            LearningSession session,
            WordSet wordSet,
            int questions,
            Runnable onFinish,
            SessionEventBus eventBus
    ) {
        this.router = router;
        this.session = session;
        this.mode = new MillionaireMode(wordSet, questions);
        this.onFinish = onFinish;
        this.eventBus = eventBus;
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
        eventBus.register(feedbackBuffer);
        showNext();
    }

    private void showNext() {
        if (!mode.hasNext()) {
            eventBus.unregister(feedbackBuffer);
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

        StringBuilder message = new StringBuilder();

        if (correct) {
            message.append("Dobrze!");
        } else {
            message.append("Źle! \nPoprawna Odpowiedź to: ")
                   .append(mode.getWord().getTarget());
        }

        List<String> feedback = feedbackBuffer.consumeMessages();
        if (feedback != null) {
            for (String f : feedback) {
                message.append("\n").append(f);
            }
        }

        JOptionPane.showMessageDialog(
                router.getMainFrame(),
                message.toString(),
                "Odpowiedź",
                correct
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE
        );
        mode.advance();
        showNext();
    }

    private void saveAndExit() {
        eventBus.unregister(feedbackBuffer);
        session.setCurrentIndex(mode.getCurrentQuestionIndex());
        session.saveMemento(MODE_KEY);
        onFinish.run();
    }
}
