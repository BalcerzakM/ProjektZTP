package app;

import models.WordSet;

public class AppContext {
    private String CurrentUser = "user";
    private WordSet CurrentWordSet;
    public Connector connector = Connector.getInstance();

    public AppContext(WordSet CurrentWordSet) {
        this.CurrentWordSet = CurrentWordSet;
    }

    public String getCurrentUser() {
        return CurrentUser;
    }

    public void setCurrentUser(String currentUser) {
        CurrentUser = currentUser;
    }

    public WordSet getCurrentWordSet() {
        return CurrentWordSet;
    }

    public void setCurrentWordSet(WordSet currentWordSet) {
        CurrentWordSet = currentWordSet;
    }
}

