package models;

import LearningModes.LearningMode;
import LearningModes.ModeType;
import observers.AnswerObserver;
import observers.SessionStatistics;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LearningSession {
    private List<AnswerObserver> observers = new ArrayList<>();
    private final Map<ModeType, SessionMemento> mementos = new EnumMap<>(ModeType.class);
    private int currentIndex = 0;
    private List<String> currentAnswers = new ArrayList<>();
    private long seed = 0;


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

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public long getSeed() {
        return seed;
    }

    /**
     * Inicjalizacja Seedu
     */
    public void initSeedIfNeeded() {
        if (seed == 0) {
            seed = System.currentTimeMillis();
        }
    }


//    public void setAnswers(List<String> answers) {
//        this.currentAnswers = answers;
//    }
//
//    public List<String> getAnswers() {
//        return currentAnswers;
//    }

//    public void setSeed(long seed) {
//        this.seed = seed;
//    }

    public void saveMemento(ModeType mode) {
        mementos.put(mode, createMemento());
    }

    public boolean hasMemento(ModeType mode) {
        return mementos.containsKey(mode);
    }

    public void restore(ModeType mode) {
        SessionMemento m = mementos.get(mode);
        if (m != null) {
            restoreFrom(m);
        }
    }

    private SessionMemento createMemento() { return new SessionMemento(this.currentIndex,this.currentAnswers,this.seed); }
    private void restoreFrom(SessionMemento m) {
        this.currentIndex = m.getQuestionIndex();
        this.currentAnswers = new ArrayList<>(m.getAnswersSnapshot());
        this.seed = m.getSeed();
    }


//    public SessionMemento getMemento() {
//        return memento;
//    }

//    public void saveMemento(int questionIndex) {
//        this.memento = new SessionMemento(questionIndex);
//    }

}
