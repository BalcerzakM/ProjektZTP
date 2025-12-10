import java.util.ArrayList;
import java.util.List;

public class LearningSession {
    private List<AnswerObserver> observers;
    private LearningMode mode;

    LearningSession() {
        observers = new ArrayList<>();
        this.mode = mode;
    }
    public void setMode(LearningMode mode) {
        this.mode = mode;
    }

    public void registerObserver(AnswerObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(AnswerObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Word w, boolean correct) {
        for (AnswerObserver observer : observers) {
            observer.onAnswer(w, correct);
        }
    }
}
