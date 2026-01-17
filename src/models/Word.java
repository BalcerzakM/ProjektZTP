package models;

public class Word {
    private final String source;
    private final String target;


    public Word(String source, String target) {
        this.source = source;
        this.target = target;
    }



    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

}
