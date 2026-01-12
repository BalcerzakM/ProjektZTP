package controllers;
import models.LearningSession;
import models.WordSet;
import views.MainFrame;
import views.FlashCardPanel;

public class FlashCardController {

    private enum Side { FRONT, BACK }

    private final MainFrame frame;
    private final LearningSession session;
    private final WordSet wordSet;
    private final Runnable onFinish;

    private FlashCardPanel panel;
    private int index = 0;
    private Side side = Side.FRONT;

    public FlashCardController(
            MainFrame frame,
            LearningSession session,
            WordSet wordSet,
            Runnable onFinish
    ) {
        this.frame = frame;
        this.session = session;
        this.wordSet = wordSet;
        this.onFinish = onFinish;
    }

    public void start() {
        panel = new FlashCardPanel();

        if (session.getMemento() != null) {
            index = session.getMemento().getQuestionIndex();
        }

        panel.setOnClick(this::handleClick);
        panel.setOnBack(this::saveAndExit);

        showFront();
        frame.setView(panel, "FLASHCARD");
    }

    private void handleClick() {
        if (side == Side.FRONT) {
            showBack();
        } else {
            nextCard();
        }
    }

    private void showFront() {
        if (index >= wordSet.getWords().size()) {
            onFinish.run();
            return;
        }

        side = Side.FRONT;
        panel.setText(wordSet.getWords().get(index).getSource());
        updateProgress();
    }

    private void showBack() {
        side = Side.BACK;
        panel.setText(wordSet.getWords().get(index).getTarget());
        session.notifyObservers(null, true);
    }

    private void nextCard() {
        index++;
        showFront();
    }

    private void saveAndExit() {
        session.saveMemento(index);
        onFinish.run();
    }

    /**
     * Metoda do aktualizowania numeru fiszki w naszym widoku
     */
    private void updateProgress() {
        panel.setProgress(index, wordSet.getWords().size());
    }
}
