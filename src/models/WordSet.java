package models;

import java.util.List;
/**
 * Reprezentuje zestaw słów wykorzystywany w procesie nauki.
 *
 * WordSet stanowi podstawową jednostkę danych w aplikacji
 * i jest wykorzystywany przez wszystkie tryby nauki.
 * Zawiera kolekcję par słów oraz poziom trudności
 * zgodny z klasyfikacją CEFR.
 *
 * Klasa pełni rolę Modelu w architekturze MVC.
 */
public class WordSet {
    /**
     * Nazwa zestawu słów
     */
    private final String name;

    private final List<Word> words;
    private final LanguageCERFLevel cerfLevel;

    public WordSet(String name, List<Word> words, LanguageCERFLevel cerfLevel) {
        this.name = name;
        this.words = words;
        this.cerfLevel = cerfLevel;
    }


    /**
     * Zwraca listę słów należących do zestawu.
     *
     * Zestaw słów jest wykorzystywany przez tryby nauki
     * jako źródło danych do generowania zadań.
     */
    public List<Word> getWords() {
        return words;
    }


    public LanguageCERFLevel getCERFLevel() {
        return cerfLevel;
    }

    public void addWord(Word word) {
        words.add(word);
    }
}
