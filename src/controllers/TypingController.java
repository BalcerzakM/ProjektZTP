package controllers;

import LearningModes.ModeType;
import LearningModes.TypingMode;
import app.AppRouter;
import services.events.SessionEventBus;
import services.events.SessionFeedbackBuffer;
import models.LearningSession;
import models.Word;
import models.WordSet;
import views.TypingPanel;

import javax.swing.*;
import java.util.List;

public class TypingController implements Controller {

    private static final ModeType MODE_KEY = ModeType.TYPING;

    private final AppRouter router;
    private final LearningSession session;
    private final TypingMode mode;
    private final Runnable onFinish;

    private final SessionEventBus eventBus;
    private final SessionFeedbackBuffer feedbackBuffer = new SessionFeedbackBuffer();

    private Word currentWord;

    public TypingController(
            AppRouter router,
            LearningSession session,
            WordSet wordSet,
            int questions,
            Runnable onFinish,
            SessionEventBus eventBus
    ) {
        this.router = router;
        this.session = session;
        this.mode = new TypingMode(wordSet, questions);
        this.onFinish = onFinish;
        this.eventBus = eventBus;
    }

    public void run() {
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

        StringBuilder message = new StringBuilder();

        if (correct) {
            message.append("Dobrze!");
        } else {
            message.append("Źle! \nPoprawna Odpowiedź to: ")
                    .append(currentWord.getTarget());
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
        session.setCurrentIndex(mode.getIndex());
        session.saveMemento(MODE_KEY);
        onFinish.run();
    }
}
