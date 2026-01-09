package LearningModes;

import models.LearningSession;
import models.Word;
import models.WordSet;

import java.util.List;

public class TypingMode {

    private final List<Word> words;
    private int index = 0;
    private int totalQuestions;

    public TypingMode(WordSet wordSet, int totalQuestions) {
        this.words = wordSet.getWords();
        this.totalQuestions = totalQuestions;
    }

    public boolean hasNext() {
        return index < totalQuestions;
    }

    public Word nextWord() {
        return words.get(index++);
    }

    public boolean checkAnswer(Word word, String input) {
        return input.equalsIgnoreCase(word.getTarget());
    }
}
