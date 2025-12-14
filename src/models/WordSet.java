package models;

import java.util.List;

public class WordSet {
    String name;
    List<Word> words;
    String difficulty;

    public WordSet(String name, List<Word> words, String difficulty) {
        this.name = name;
        this.words = words;
        this.difficulty = difficulty;
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



    public void addWord(Word word) {
        words.add(word);
    }
}
