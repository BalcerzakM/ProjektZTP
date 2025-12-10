import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Word w = new Word("duck", "kaczka", "B1", "animals");
        List<Word> animals = new ArrayList<Word>();
        animals.add(new Word("spider", "pająk", "A2", "animals"));
        animals.add(new Word("deer", "jelen", "B1", "animals"));
        animals.add(new Word("frog", "zaba", "B1", "animals"));
        animals.add(new Word("dog", "pies", "A1", "animals"));

        WordSet ws = new WordSet("animals", animals);

        QuestionFactory factory = new MillionaireTypeQuestionFactory();
        Question q1 = factory.createQuestion(w, ws);
        System.out.println(q1);

        //LearningMode mode1 = new FlashCardMode();
        //mode1.start(ws);
        //LearningMode mode2 = new TypingMode();
        //mode2.start(ws);
        //LearningMode mode3 = new ConnectMode();
        //mode3.start(ws);
        LearningMode mode4 = new MillionaireMode();
        mode4.start(ws);

    }
}