package questions;

import models.Question;
import models.Word;
import models.WordSet;

public interface QuestionFactory {
    public Question createQuestion(Word w, WordSet wordSet);
}
