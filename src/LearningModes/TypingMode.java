package LearningModes;

import models.Word;
import models.WordSet;


import java.util.List;
import java.util.Random;

public class TypingMode implements LearningMode {

    private final List<Word> words;
    private int index = 0;
    private final int totalQuestions;
    private Random rand = new Random();

    public TypingMode(WordSet wordSet, int totalQuestions) {
        this.words = wordSet.getWords();
        this.totalQuestions = totalQuestions;
    }


    public void restore(int index, long seed) {
        this.index = index;
        this.rand = new Random(seed);

        for (int i = 0; i < index; i++) {
            nextWord();
        }
    }

    @Override
    public void startNew(long seed) {
        this.index = 0;
        this.rand = new Random(seed);
    }



    public boolean hasNext() {
        return index < totalQuestions;
    }

    public Word nextWord() {
        return words.get(rand.nextInt(words.size()));
    }

    public void advance() {
        index++;
    }

    public boolean checkAnswer(Word word, String input) {
        return input.equalsIgnoreCase(word.target());
    }

    public int getIndex() {
        return index;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }
}
