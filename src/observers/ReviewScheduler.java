package observers;

import java.util.ArrayList;
import java.util.List;
import models.Word;
import models.WordSet;


public class ReviewScheduler implements AnswerObserver {
    private List<Word> reviewWords = new ArrayList<Word>();

    @Override
    public void onAnswer(Word w, boolean correct) {
        if (!correct && !reviewWords.contains(w)) {
            reviewWords.add(w);
        }
    }

    public List<Word> getReviewWords() {
        return reviewWords;
    }

    public WordSet createReviewSet() {
        return new WordSet("powtorzenie", reviewWords, "RV");
    }
}