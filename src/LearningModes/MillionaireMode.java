package LearningModes;

import models.Word;
import models.WordSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MillionaireMode {

    private final List<Word> words;
    private final int totalQuestions;
    private int currentQuestion = 0;

    private Word currentWord;
    private List<String> options;

    public MillionaireMode(WordSet wordSet, int totalQuestions) {
        this.words = wordSet.getWords();
        this.totalQuestions = totalQuestions;
    }

    public boolean hasNext() {
        return currentQuestion < totalQuestions;
    }

    public void nextQuestion() {
        currentWord = words.get((int) (Math.random() * words.size()));
        options = new ArrayList<>();
        options.add(currentWord.getTarget());

        while (options.size() < 4) {
            Word w = words.get((int) (Math.random() * words.size()));
            if (!options.contains(w.getTarget())) {
                options.add(w.getTarget());
            }
        }
        Collections.shuffle(options);
        currentQuestion++;
    }

    public Word getWord() {
        return currentWord;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public boolean checkAnswer(String selected) {
        return selected.equals(currentWord.getTarget());
    }

    public int getCurrentQuestionIndex() {
        return currentQuestion;
    }

    public void setCurrentQuestionIndex(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

}
