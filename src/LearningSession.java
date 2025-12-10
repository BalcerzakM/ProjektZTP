import java.util.ArrayList;
import java.util.List;

public class LearningSession {
    private List<AnswerObserver> observers;
    private LearningMode mode; //to moze byc niepotrzebne

    LearningSession() {
        observers = new ArrayList<>();
    }
    public void setMode(LearningMode mode) {
        this.mode = mode; //to moze byc niepotrzebne
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

    public Statistics getStatistics() {
        for (AnswerObserver observer : observers) {
            if  (observer instanceof Statistics) {
                return (Statistics) observer;
            }
        }
        return null;
    }
}
