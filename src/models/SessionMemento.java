package models;
import java.util.List;

public class SessionMemento {

    private final int questionIndex;

    //private final List<String> rightWords;

    public SessionMemento(int questionIndex) {
        this.questionIndex = questionIndex;
        //this.rightWords = rightWords;
    }
    public int getQuestionIndex() {
        return questionIndex;
    }

//   public List<String> getRightWords() {
//        return rightWords;
//    }
}
