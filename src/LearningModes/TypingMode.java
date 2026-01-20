package LearningModes;

import models.Word;
import models.WordSet;


import java.util.List;
import java.util.Random;

/**
 * Model trybu nauki polegającego na samodzielnym wpisywaniu tłumaczenia słowa.
 *
 * Klasa odpowiada za wybór kolejnych słów oraz weryfikację poprawności
 * odpowiedzi użytkownika.
 *
 * Model wspiera wzorzec Memento poprzez możliwość odtworzenia
 * sekwencji słów na podstawie indeksu i seedu generatora losowego.
 */
public class TypingMode implements LearningMode {

    private final List<Word> words;
    private int index = 0;
    private final int totalQuestions;
    /**
     * Generator losowości kontrolowany przez sesję nauki.
     */
    private Random rand = new Random();


    /**
     * Tworzy model trybu wpisywania.
     *
     * @param wordSet zbiór słów używany w sesji
     * @param totalQuestions liczba pytań
     */
    public TypingMode(WordSet wordSet, int totalQuestions) {
        this.words = wordSet.getWords();
        this.totalQuestions = totalQuestions;
    }


    /**
     * Odtwarza stan trybu na podstawie indeksu i seedu.
     *
     * @param index indeks aktualnego pytania
     * @param seed seed generatora losowego
     */
    public void restore(int index, long seed) {
        this.index = index;
        this.rand = new Random(seed);

        for (int i = 0; i < index; i++) {
            nextWord();
        }
    }

    /**
     * Inicjalizuje nową sesję trybu.
     *
     * @param seed seed generatora losowego
     */
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

    /**
     * Przechodzi do kolejnego pytania.
     */
    public void advance() {
        index++;
    }

    /**
     * Sprawdza poprawność wpisanej odpowiedzi.
     *
     * @param word słowo, którego dotyczy pytanie
     * @param input odpowiedź użytkownika
     * @return true jeśli odpowiedź jest poprawna
     */
    public boolean checkAnswer(Word word, String input) {
        return input.equalsIgnoreCase(word.target());
    }

    /**
     * Zwraca indeks aktualnego pytania.
     */
    public int getIndex() {
        return index;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }
}
