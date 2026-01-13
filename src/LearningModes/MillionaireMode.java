package LearningModes;

import models.Word;
import models.WordSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MillionaireMode {

    private final List<Word> words;
    //private final int totalQuestions;
    private int currentQuestion = 0;
    private Random rand = new Random();
    private Word currentWord;
    private List<String> options;

    public MillionaireMode(WordSet wordSet, int totalQuestions) {
        this.words = wordSet.getWords();
        //this.totalQuestions = totalQuestions;
    }

    public boolean hasNext() {
        return currentQuestion < words.size();
    }

    public void nextQuestion() {
        generateQuestion();
    }

    public void restore(int index, long seed) {
        this.currentQuestion = index;
        this.rand = new Random(seed);

        for (int i = 0; i < index; i++) {
            generateQuestion();
        }
    }

    public void startNew(long seed) {
        this.currentQuestion = 0;
        this.rand = new Random(seed);
    }

    private void generateQuestion() {
        currentWord = words.get(rand.nextInt(words.size()));

        options = new ArrayList<>();
        options.add(currentWord.getTarget());

        while (options.size() < 4) {
            Word w = words.get(rand.nextInt(words.size()));
            if (!options.contains(w.getTarget())) {
                options.add(w.getTarget());
            }
        }

        Collections.shuffle(options, rand);
    }

    public Word getWord() {
        return currentWord;
    }

    public List<String> getOptions() {
        return options;
    }


    public boolean checkAnswer(String selected) {
        return selected.equals(currentWord.getTarget());
    }

    public int getCurrentQuestionIndex() {
        return currentQuestion;
    }

    public void advance(){
        currentQuestion++;
    }

    public int getTotalQuestions() {
        return words.size();
    }

}
