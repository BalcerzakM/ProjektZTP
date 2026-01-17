package models;

import java.util.List;

public class WordSet {
    private final String name;
    private final List<Word> words;
    private final LanguageCERFLevel cerfLevel;

    public WordSet(String name, List<Word> words, LanguageCERFLevel cerfLevel) {
        this.name = name;
        this.words = words;
        this.cerfLevel = cerfLevel;
    }



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
