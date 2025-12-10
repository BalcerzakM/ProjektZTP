public class Word {
    String source;
    String target;
    String difficulty;
    String category;

    public Word(String source, String target, String difficulty, String category) {
        this.source = source;
        this.target = target;
        this.difficulty = difficulty;
        this.category = category;
    }

    //metoda switch, aby zamieniała z pl na ang? ablo sie bawić w gety w klientach????

    public void setSource(String source) {
        this.source = source;
    }

    public void setTarget(String target) {
        this.target = target;
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
