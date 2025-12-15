package observers;

import java.util.ArrayList;
import java.util.List;
import models.Word;
import models.WordSet;


public class ReviewScheduler implements AnswerObserver {
    private List<Word> reviewWords = new ArrayList<Word>();
    private boolean wasAlertShown = false;

    @Override
    public void onAnswer(Word w, boolean correct) {
        if (!correct && !reviewWords.contains(w)) {
            reviewWords.add(w);
        }

        if (!correct && wasAlertShown) {
            wasAlertShown = false;
        }

        if (reviewWords.size() % 5 == 0 && reviewWords.size() > 0 && !wasAlertShown) {
            System.out.printf("\n-- Masz już %d słów do powtórki! Sprawdź lekcję powtórzeniową. --\n", reviewWords.size());
            wasAlertShown = true;
        }
    }

    public List<Word> getReviewWords() {
        return reviewWords;
    }

    public WordSet createReviewSet() {
        return new WordSet("powtorzenie", reviewWords, "RV");
    }
}