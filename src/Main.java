import LearningModes.*;
import models.LearningSession;
import models.Question;
import models.Word;
import models.WordSet;
import observers.ReviewScheduler;
import observers.Statistics;
import questions.MillionaireTypeQuestionFactory;
import questions.QuestionFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LearningMode mode1 = new FlashCardMode();
        LearningMode mode2 = new TypingMode();
        LearningMode mode3 = new ConnectMode();
        LearningMode mode4 = new MillionaireMode();


        LearningSession learningSession = new LearningSession();
        Statistics stats = Statistics.getInstance();
        ReviewScheduler review = new ReviewScheduler();

        learningSession.registerObserver(stats);
        learningSession.registerObserver(review);

        Word w = new Word("duck", "kaczka");
        List<Word> animals = new ArrayList<Word>();
        animals.add(new Word("spider", "pająk"));
        animals.add(new Word("deer", "jelen"));
        animals.add(new Word("frog", "zaba"));
        animals.add(new Word("dog", "pies"));
        animals.add(new Word("cat", "kot"));
        animals.add(new Word("horse", "kon"));
        animals.add(new Word("fish", "ryba"));

        WordSet ws = new WordSet("animals", animals, "B1");

        QuestionFactory factory = new MillionaireTypeQuestionFactory();
        Question q1 = factory.createQuestion(w, ws);
        System.out.println(q1);

        //LearningModes.LearningMode mode1 = new LearningModes.FlashCardMode();
        //mode1.start(ws);
        //LearningModes.LearningMode mode2 = new LearningModes.TypingMode();
        //mode2.start(ws);
        //LearningModes.LearningMode mode3 = new LearningModes.ConnectMode();
        //mode3.start(ws);
//        learningSession.setMode(mode4);
        mode4.start(learningSession);
        System.out.println(stats.showStatistics());
        stats.addToOverallStats();
        stats.resetStatistics();

//        learningSession.setMode(mode3);
//        mode3.start(ws, learningSession);     //review bedzie wyrzucalo blad w LearningModes.ConnectMode bo trzeba odpowiedni models.Word podac a narazie jest null
//        System.out.println(stats.showStatistics());

//        learningSession.setMode(mode2);
        mode2.start(learningSession);
        System.out.println(stats.showStatistics());
        stats.addToOverallStats();
        stats.resetStatistics();

//        learningSession.setMode(mode1);
        WordSet reviewSet = review.createReviewSet();
        mode1.start(learningSession);
        System.out.println(stats.showStatistics());
        System.out.println(stats.showOverallStatistics());
    }
}