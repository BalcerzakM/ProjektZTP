package controllers;
import LearningModes.ModeType;
import app.AppRouter;
import models.LearningSession;
import models.WordSet;
import views.FlashCardPanel;

public class FlashCardController implements Controller {

    private static final ModeType MODE_KEY = ModeType.FLASHCARD;

    private enum Side { FRONT, BACK }
    private final AppRouter router;
    private final LearningSession session;
    private final WordSet wordSet;
    private final Runnable onFinish;

    private FlashCardPanel panel;
    private int index = 0;
    private Side side = Side.FRONT;

    private boolean wasRevealed = false;


    public FlashCardController(
            AppRouter router,
            LearningSession session,
            WordSet wordSet,
            Runnable onFinish
    ) {
        this.router = router;
        this.session = session;
        this.wordSet = wordSet;
        this.onFinish = onFinish;
    }

    public void run() {
        panel = new FlashCardPanel();

        if (session.hasMemento(MODE_KEY)) {
            session.restore(MODE_KEY);
            index = session.getCurrentIndex();
        }

        panel.setOnClick(this::handleClick);
        panel.setOnBack(this::saveAndExit);

        panel.setOnNext(this::nextCard);
        panel.setOnPrev(this::prevCard);

        showFront();
        router.setPanel(panel, "FLASHCARD");
    }

    private void handleClick() {
        if (side == Side.FRONT) {
            showBack();
        } else {
            showFront();
        }
    }

    private void showFront() {
        if (index >= wordSet.getWords().size()) {
            onFinish.run();
            return;
        }

        side = Side.FRONT;
        panel.setText(wordSet.getWords().get(index).source());
        updateProgress();
    }

    private void showBack() {
        side = Side.BACK;
        panel.setText(wordSet.getWords().get(index).target());
        wasRevealed = true;
    }

    private void nextCard() {
        if (wasRevealed) {
            session.notifyObservers(null, true);
        }
        wasRevealed = false;

        if (index < wordSet.getWords().size() - 1) {
            index++;
            showFront();
        }
    }

    private void prevCard() {
        if (index > 0) {
            index--;
            showFront();
        }
    }

    /**
     * Metoda do powrotu i zapisania Memento
     */
    private void saveAndExit() {
        if (wasRevealed) {
            session.notifyObservers(null, true);
        }
        session.setCurrentIndex(index);
        session.saveMemento(MODE_KEY);
        onFinish.run();
    }

    /**
     * Metoda do aktualizowania numeru fiszki w naszym widoku
     */
    private void updateProgress() {
        panel.setProgress(index, wordSet.getWords().size());
    }
}
