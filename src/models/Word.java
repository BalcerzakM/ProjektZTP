package models;

public class Word {
    private String source;
    private String target;


    public Word(String source, String target) {
        this.source = source;
        this.target = target;
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

    public boolean isAnswerCorrect(String answer) {
        if (answer.equals(target)) {
            return true;
        } else {
            return false;
        }
    }

}
