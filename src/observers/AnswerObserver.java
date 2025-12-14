package observers;

import models.Word;

public interface AnswerObserver {
    void onAnswer(Word w, boolean correct);
}
