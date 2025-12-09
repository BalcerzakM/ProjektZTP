public class Word {
    String eng;
    String pl;
    String difficulty;
    String category;

    public Word(String eng, String pl, String difficulty, String category) {
        this.eng = eng;
        this.pl = pl;
        this.difficulty = difficulty;
        this.category = category;
    }

    public String getEng() {
        return eng;
    }

    public String getPl() {
        return pl;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }
}
