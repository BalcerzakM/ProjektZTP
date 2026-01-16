package observers;

import java.util.ArrayList;
import java.util.List;
import models.Word;

public class ReviewScheduler implements AnswerObserver {
    private final List<Word> reviewWords = new ArrayList<>();
    private boolean wasAlertShown = false;

    @Override
    public void onAnswer(Word w, boolean correct) {
        if (!correct && !reviewWords.contains(w)) {
            reviewWords.add(w);
        }
        else if (!correct) {
            reviewWords.remove(w);
        }

        if (!correct && wasAlertShown) {
            wasAlertShown = false;
        }

        if (reviewWords.size() % 5 == 0 && !reviewWords.isEmpty() && !wasAlertShown) {
            System.out.printf("\n-- Masz już %d słów do powtórki! Sprawdź lekcję powtórzeniową. --\n", reviewWords.size());
            wasAlertShown = true;
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