package models;

import LearningModes.LearningMode;
import observers.AnswerObserver;
import observers.Statistics;

import java.util.ArrayList;
import java.util.List;

public class LearningSession {
    private List<AnswerObserver> observers = new ArrayList<>();
    private WordSet wordSet;
    private LearningMode mode;
    private SessionMemento memento;

    public void setMode(LearningMode mode) {
        this.mode = mode;
    }

    public LearningMode getMode() {
        return mode;
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

    public WordSet getWordSet() {
        return wordSet;
    }

    public void setWordSet(WordSet wordSet) {
        this.wordSet = wordSet;
    }

    public SessionMemento getMemento() {
        return memento;
    }

    public void saveMemento(int questionIndex) {
        this.memento = new SessionMemento(questionIndex);
    }

}
