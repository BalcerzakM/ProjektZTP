package models;


import LearningModes.ModeType;
import services.observers.AnswerObserver;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Reprezentuje sesję nauki użytkownika.
 *
 * Klasa stanowi centralny element logiki aplikacji i:
 * - zarządza obserwatorami odpowiedzi (Observer),
 * - przechowuje i odtwarza stan trybów nauki (Memento),
 * - zapewnia spójność przebiegu sesji pomiędzy widokami.
 *
 * LearningSession pełni rolę Modelu w architekturze MVC.
 */
public class LearningSession {
    /**
     * Lista obserwatorów reagujących na odpowiedzi użytkownika.
     */
    private final List<AnswerObserver> observers = new ArrayList<>();
    /**
     * Zapisane stany poszczególnych trybów nauki.
     */
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

    /**
     * Powiadamia obserwatorów o udzielonej odpowiedzi.
     */
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
     * Inicjalizuje seed losowości, jeśli nie zostało jeszcze ustawione.
     *
     * Zapewnia spójność losowania po przywróceniu stanu trybu.
     */
    public void initSeedIfNeeded() {
        if (seed == 0) {
            seed = System.currentTimeMillis();
        }
    }

    /**
     * Zapisuje aktualny stan sesji dla danego trybu.
     */
    public void saveMemento(ModeType mode) {
        mementos.put(mode, createMemento());
    }

    public boolean hasMemento(ModeType mode) {
        return mementos.containsKey(mode);
    }

    /**
     * Przywraca zapisany stan sesji dla wskazanego trybu.
     */
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

    public void removeMemento(ModeType mode) {
        mementos.remove(mode);
    }

    /**
     * Usuwa wszystkie zapisane stany trybów
     * i resetuje generator losowości.
     */
    public void flushMementos() {
        this.mementos.clear();
        resetSeed();
    }

    public void resetSeed() {
        this.seed = 0;
    }

}
