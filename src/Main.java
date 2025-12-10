import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        LearningMode mode1 = new FlashCardMode();
        LearningMode mode2 = new TypingMode();
        LearningMode mode4 = new MillionaireMode();


        LearningSession learningSession = new LearningSession();
        Statistics stats = new Statistics();
        learningSession.registerObserver(stats);

        Word w = new Word("duck", "kaczka");
        List<Word> animals = new ArrayList<Word>();
        animals.add(new Word("spider", "pająk"));
        animals.add(new Word("deer", "jelen"));
        animals.add(new Word("frog", "zaba"));
        animals.add(new Word("dog", "pies"));

        WordSet ws = new WordSet("animals", animals, "B1");

        QuestionFactory factory = new MillionaireTypeQuestionFactory();
        Question q1 = factory.createQuestion(w, ws);
        System.out.println(q1);

        //LearningMode mode1 = new FlashCardMode();
        //mode1.start(ws);
        //LearningMode mode2 = new TypingMode();
        //mode2.start(ws);
        //LearningMode mode3 = new ConnectMode();
        //mode3.start(ws);
        learningSession.setMode(mode4);
        mode4.start(ws, learningSession);
        System.out.println(stats.showStatistics());

        learningSession.setMode(mode1);
        mode1.start(ws, learningSession);
        System.out.println(stats.showStatistics());

        learningSession.setMode(mode2);
        mode2.start(ws, learningSession);
        System.out.println(stats.showStatistics());
    }
}