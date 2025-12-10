import java.util.ArrayList;
import java.util.List;

public class LearningSession {
    private List<AnswerObserver> observers;
    private LearningMode mode;


    public void registerObserver(AnswerObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(AnswerObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (AnswerObserver observer : observers) {
            //???
        }
    }
}
