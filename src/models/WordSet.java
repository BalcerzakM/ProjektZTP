package models;

import java.util.List;

public class WordSet {
    private String name;
    private List<Word> words;
    private final LanguageCERFLevel cerfLevel;

    public WordSet(String name, List<Word> words, LanguageCERFLevel cerfLevel) {
        this.name = name;
        this.words = words;
        this.cerfLevel = cerfLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public LanguageCERFLevel getCERFLevel() {
        return cerfLevel;
    }

    public void addWord(Word word) {
        words.add(word);
    }
}
