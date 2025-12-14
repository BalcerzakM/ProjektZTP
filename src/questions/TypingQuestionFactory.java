package questions;

import models.Question;
import models.Word;
import models.WordSet;

public class TypingQuestionFactory implements QuestionFactory {

    @Override
    public Question createQuestion(Word w, WordSet wordSet) {
        //nie wiem czy to potrzebne, wydaje mi się że wystarczy flashcard i tam prompta dodać tego"Napisz Tłumacznie słowa" i w trybie fiszek go nie wyświetlać, a w trybie wpisywania wyświetlać.
        return new Question("Napisz Tłumacznie słowa: \""+ w.getSource()+ "\"", w.getTarget(), null);
    }
}
