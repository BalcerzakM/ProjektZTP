package app;

import models.*;
import observers.ReviewScheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AppContext {
    private User currentUser = new User("maciek1234", "MaciekZKlanu4321", LanguageCERFLevel.A1);
    private Statistics currentUserStatistics = new Statistics(0, 0, 0, 0, 0, 0, 0);
    private WordSet currentWordSet;
    private final ReviewScheduler reviewScheduler = new ReviewScheduler();

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        currentUser = currentUser;
    }

    public WordSet getCurrentWordSet() {
        return currentWordSet;
    }

    public void setCurrentWordSet(WordSet currentWordSet) {
        this.currentWordSet = currentWordSet;
    }

    public ReviewScheduler getReviewScheduler() {
        return reviewScheduler;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public boolean isDatabaseSelected() {
        return currentWordSet != null;
    }

    public List<String> getDatabaseList() {
        try {
            return Connector.getInstance().getAviableDatabases();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WordSet getNewWordSet(String fileName) {
        try {
            return Connector.getInstance().readWordSetFromFile(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Statistics getCurrentUserStatistics() {
        return currentUserStatistics;
    }
}

