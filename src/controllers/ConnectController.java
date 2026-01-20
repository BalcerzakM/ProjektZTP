package controllers;

import LearningModes.ModeType;
import app.AppRouter;
import services.events.SessionEventBus;
import services.events.SessionFeedbackBuffer;
import models.LearningSession;
import models.*;
import views.ConnectPanel;
import LearningModes.ConnectMode;

import javax.swing.*;

/**
 * Kontroler trybu łączenia.
 *
 * Klasa pełni rolę kontrolera we wzorcu MVC. Odpowiada za:
 * - inicjalizację trybu nauki,
 * - komunikację pomiędzy modelem a widokiem,
 * - obsługę zdarzeń użytkownika,
 * - zapisywanie i przywracanie stanu sesji (Memento),
 * - powiadamianie obserwatorów o wynikach odpowiedzi.
 */

public class ConnectController implements Controller {
    /**
     * Klucz identyfikujący tryb w mechanizmie zapisu stanu sesji.
     */
    private static final ModeType MODE_KEY = ModeType.CONNECT;
    private final AppRouter router;
    private final LearningSession session;
    private final ConnectMode mode;
    /**
     * Akcja wykonywana po zakończeniu trybu lub opuszczeniu go przez użytkownika.
     */
    private final Runnable onFinish;

    /**
     * Magistrala zdarzeń sesji, wykorzystywana do komunikacji
     * pomiędzy trybami a obserwatorami statystyk.
     */
    private final SessionEventBus eventBus;

    /**
     * Bufor komunikatów generowanych przez obserwatorów,
     * wyświetlanych użytkownikowi po odpowiedzi.
     */
    private final SessionFeedbackBuffer feedbackBuffer = new SessionFeedbackBuffer();

    public ConnectController(AppRouter router,
                             LearningSession session,
                             WordSet wordSet, Runnable onFinish, SessionEventBus eventBus) {
        this.router = router;
        this.session = session;
        this.mode = new ConnectMode(wordSet);
        this.onFinish = onFinish;
        this.eventBus = eventBus;
    }


    public void run() {
        if (session.hasMemento(MODE_KEY)) {
            session.restore(MODE_KEY);
            mode.restore(
                    session.getSeed()
            );
        } else {
            session.initSeedIfNeeded();
            mode.startNew(session.getSeed());
        }

        eventBus.register(feedbackBuffer);

        show();
    }

    private void show() {
        ConnectPanel panel = new ConnectPanel(
                mode.getLeftSources().stream().map(Word::source).toList(),
                mode.getRightTargets()
        );

        panel.onCheck(() -> handleCheck(panel));
        panel.setOnBack(this::saveAndExit);
        router.setPanel(panel, "CONNECT");
    }

    private void handleCheck(ConnectPanel panel) {
        int l = panel.getLeftIndex();
        int r = panel.getRightIndex();

        if (l == -1 || r == -1) return;

        if (mode.check(l, r)) {
            Word w = mode.removePair(l, r);
            session.notifyObservers(w, true);

            StringBuilder message = new StringBuilder("Dobrze!");

            for (String f : feedbackBuffer.consumeMessages()){
                message.append("\n").append(f);
            }

            JOptionPane.showMessageDialog(
                    panel,
                    message,
                    "Odpowiedź",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (mode.isFinished()) {
                eventBus.unregister(feedbackBuffer);
                JOptionPane.showMessageDialog(
                        panel,
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
            session.notifyObservers(mode.getLeftSources().get(l), false);

            StringBuilder message = new StringBuilder("Źle, spróbuj ponownie");

            for (String f : feedbackBuffer.consumeMessages()){
                message.append("\n").append(f);
            }

            JOptionPane.showMessageDialog(
                    panel,
                    message,
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        panel.updateLists(
                mode.getLeftSources().stream().map(Word::source).toList(),
                mode.getRightTargets()
        );
    }

    private void saveAndExit() {
        eventBus.unregister(feedbackBuffer);
        session.saveMemento(ModeType.CONNECT);
        onFinish.run();
    }
}
