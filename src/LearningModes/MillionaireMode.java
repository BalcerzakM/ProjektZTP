package LearningModes;

import models.Word;
import models.WordSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Model trybu nauki typu „Milionerzy”.
 *
 * Tryb polega na wyborze jednej poprawnej odpowiedzi spośród czterech opcji.
 * Każde pytanie dotyczy jednego słowa i jego tłumaczenia.
 *
 * Klasa pełni rolę Modelu we wzorcu MVC i odpowiada wyłącznie
 * za logikę generowania pytań oraz weryfikację odpowiedzi.
 *
 * Model wspiera wzorzec Memento poprzez możliwość odtworzenia
 * sekwencji pytań na podstawie indeksu i seedu generatora losowego.
 */
public class MillionaireMode implements LearningMode {

    /**
     * Lista słów wykorzystywana do generowania pytań.
     */
    private final List<Word> words;
    private final int totalQuestions;
    private int currentQuestion = 0;
    /**
     * Generator losowy kontrolowany przez sesję nauki.
     */
    private Random rand = new Random();
    private Word currentWord;
    private List<String> options;

    /**
     * Tworzy model trybu „Milionerzy”.
     *
     * @param wordSet zbiór słów używany w sesji
     * @param totalQuestions liczba pytań w trybie
     */
    public MillionaireMode(WordSet wordSet, int totalQuestions) {
        this.words = wordSet.getWords();
        this.totalQuestions = totalQuestions;
    }

    public boolean hasNext() {
        return currentQuestion < totalQuestions;
    }

    /**
     * Generuje kolejne pytanie.
     *
     * Metoda powinna być wywoływana przed prezentacją pytania w widoku.
     */
    public void nextQuestion() {
        generateQuestion();
    }

    /**
     * Odtwarza stan trybu na podstawie zapisanego indeksu i seedu.
     *
     * Metoda realizuje mechanizm Memento, umożliwiając powrót
     * do trybu w identycznym stanie logicznym.
     *
     * @param index indeks aktualnego pytania
     * @param seed seed generatora losowego
     */
    public void restore(int index, long seed) {
        this.currentQuestion = index;
        this.rand = new Random(seed);

        for (int i = 0; i < index; i++) {
            generateQuestion();
        }
    }

    /**
     * Inicjalizuje nową sesję trybu.
     *
     * @param seed seed generatora losowego
     */
    @Override
    public void startNew(long seed) {
        this.currentQuestion = 0;
        this.rand = new Random(seed);
    }

    /**
     * Generuje pojedyncze pytanie wraz z zestawem odpowiedzi.
     *
     * Jedna odpowiedź jest poprawna, pozostałe są losowo dobranymi
     * tłumaczeniami innych słów.
     */
    private void generateQuestion() {
        currentWord = words.get(rand.nextInt(words.size()));

        options = new ArrayList<>();
        options.add(currentWord.target());

        while (options.size() < 4) {
            Word w = words.get(rand.nextInt(words.size()));
            if (!options.contains(w.target())) {
                options.add(w.target());
            }
        }

        Collections.shuffle(options, rand);
    }

    /**
     * Zwraca słowo powiązane z aktualnym pytaniem.
     */
    public Word getWord() {
        return currentWord;
    }

    /**
     * Zwraca listę dostępnych odpowiedzi.
     */
    public List<String> getOptions() {
        return options;
    }


    /**
     * Sprawdza poprawność wybranej odpowiedzi.
     *
     * @param selected odpowiedź wybrana przez użytkownika
     * @return true jeśli odpowiedź jest poprawna
     */
    public boolean checkAnswer(String selected) {
        return selected.equals(currentWord.target());
    }

    /**
     * Zwraca aktualny indeks pytania w kontekście sesji.
     * Wartość ta jest zapisywana w Memento.
     */
    public int getCurrentQuestionIndex() {
        return currentQuestion;
    }

    /**
     * Przechodzi do kolejnego pytania.
     */
    public void advance(){
        currentQuestion++;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

}
