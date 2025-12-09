public class Word {
    String source;
    String target;
    String difficulty;
    String category;

    public Word(String target, String source, String difficulty, String category) {
        this.source = source;
        this.target = target;
        this.difficulty = difficulty;
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }
}
