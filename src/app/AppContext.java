package app;

import models.WordSet;
import observers.ReviewScheduler;

public class AppContext {
    private String CurrentUser = "user";
    private WordSet CurrentWordSet;
    private ReviewScheduler reviewScheduler = new ReviewScheduler();

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

    public ReviewScheduler getReviewScheduler() {
        return reviewScheduler;
    }
}

