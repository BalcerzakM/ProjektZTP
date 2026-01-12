package models;

import java.util.HashMap;
import java.util.Map;

public class CareTaker {
    private Map<String, SessionMemento> Mementos;
    private LearningSession learningSession; // tego nie jestem pewien
    public CareTaker(LearningSession learningSession) {
        this.learningSession = learningSession;
        Mementos = new HashMap<>();
    }

}
