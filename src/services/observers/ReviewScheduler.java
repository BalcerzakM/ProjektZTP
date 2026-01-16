package services.observers;

import java.util.ArrayList;
import java.util.List;

import events.SessionEventBus;
import models.Word;

public class ReviewScheduler implements AnswerObserver {
    private final List<Word> reviewWords = new ArrayList<>();
    private boolean wasAlertShown = false;

    private SessionEventBus eventBus;

    public void attachEventBus(SessionEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void detachEventBus(SessionEventBus eventBus) {
        this.eventBus = null;
    }

    @Override
    public void onAnswer(Word w, boolean correct) {

        if (!correct && !reviewWords.contains(w)) {
            reviewWords.add(w);
        }

        if (reviewWords.size() % 5 == 0 && !reviewWords.isEmpty() && !wasAlertShown) {
            if (eventBus != null) {
                eventBus.reviewFeedback(reviewWords.size());
            }
            wasAlertShown = true;
        }

        if (reviewWords.size() % 5 != 0) {
            wasAlertShown = false;
        }
    }

    public List<Word> getReviewWords() {
        return reviewWords;
    }

    public List<Word> loadReviewWords() {
        List<Word> reviewWordsCopy = new ArrayList<>(reviewWords);
        reviewWords.clear();
        wasAlertShown = false;
        return reviewWordsCopy;
    }
}