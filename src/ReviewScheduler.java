import java.util.ArrayList;
import java.util.List;

public class ReviewScheduler implements AnswerObserver {
    private List<Word> reviewWords = new ArrayList<Word>();

    @Override
    public void onAnswer(Word w, boolean correct) {
        if (!correct) {
            reviewWords.add(w);
        }
    }

    public List<Word> getReviewWords() {
        return reviewWords;
    }
}