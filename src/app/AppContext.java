package app;

import models.*;
import services.WordSetProvider.WordSetLoader;
import services.WordSetProvider.WordSetLoaderProxy;
import services.WordSetProvider.WordsetProvider;
import services.observers.ReviewScheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AppContext {
    private User currentUser;
    private Statistics currentUsersStatistics;
    private WordSet currentWordSet;
    private final ReviewScheduler reviewScheduler = new ReviewScheduler();
    private final WordsetProvider wordSetProvider;

    public AppContext() {
        WordSetLoader realLoader = new WordSetLoader(this);
        this.wordSetProvider = new WordSetLoaderProxy(realLoader);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public Statistics getCurrentUserStatistics() {
        return currentUsersStatistics;
    }

    public void setUserStatistics(Statistics statistics) {
        this.currentUsersStatistics = statistics;
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

    public WordSet loadWordSetSecurely(String filename) throws Exception {
        return wordSetProvider.getWordSet(filename, currentUser);
    }

    public boolean isDatabaseSelected() {
        return currentWordSet != null;
    }

    public List<String> getDatabaseNamesList() {
        try {
            return Connector.getInstance().getAviableDatabaseNames();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WordSet getNewWordSetFromDb(String fileName) {
        try {
            return Connector.getInstance().readWordSetFromFile(fileName + ".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getUserNamesList() {
        try {
            return Connector.getInstance().getUsersList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserPasswordFromDb(String username) {
        try {
            return Connector.getInstance().readUserPasswordFromFile(username + ".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public User getNewUserFromDb(String username) {
        try {
            return Connector.getInstance().readUserFromFile(username + ".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Statistics getNewStatisticsFromDb(String username) {
        try {
            return Connector.getInstance().readStatisticsFromFile(username + ".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveNewUserToDb(User user) {
        Statistics stats = new Statistics(0, 0, 0, 0, 0, 0, 0);
        try {
            Connector.getInstance().saveUserToFile(user, stats);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToDbAndExit() {
        if (currentUser != null & currentUsersStatistics != null){
            try {
                Connector.getInstance().saveUserToFile(this.currentUser, this.currentUsersStatistics);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

