package app;

import models.Connector;
import models.LanguageCERFLevel;
import models.User;
import models.WordSet;
import observers.ReviewScheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AppContext {
    private User CurrentUser = new User("maciek1234", "MaciekZKlanu4321", LanguageCERFLevel.A1);
    private WordSet CurrentWordSet;
    private final ReviewScheduler reviewScheduler = new ReviewScheduler();

    public User getCurrentUser() {
        return CurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        CurrentUser = currentUser;
    }

    public WordSet getCurrentWordSet() {
        return CurrentWordSet;
    }

    public void setCurrentWordSet(WordSet currentWordSet) {
        CurrentWordSet = currentWordSet;
    }

    public ReviewScheduler getReviewScheduler() {
        return reviewScheduler;
    }

    public boolean isUserLoggedIn() {
        return CurrentUser != null;
    }

    public boolean isDatabaseSelected() {
        return CurrentWordSet != null;
    }

    public List<String> getDatabaseList() {
        try {
            return Connector.getInstance().getAviableDatabases();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WordSet getWordSet(String fileName) {
        try {
            return Connector.getInstance().readWordSetFromFile(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

