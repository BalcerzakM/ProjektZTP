package LearningModes;

import models.LearningSession;
import models.Word;
import models.WordSet;
import questions.FlashCardQuestionFactory;
import questions.QuestionFactory;


import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class FlashCardMode implements LearningMode {
    @Override
    public void start(WordSet wordSet, LearningSession learningSession) {
        Scanner scanner = new Scanner(System.in);
        QuestionFactory factory = new FlashCardQuestionFactory();
        System.out.println("*******************************");
        System.out.println("          Tryb Fiszek!");
        System.out.println("*******************************");
        System.out.println();
        for (Word word : wordSet.getWords()) {
            System.out.println("*******************************");
            System.out.println();
            System.out.println("            "+word.getSource());
            String line = scanner.nextLine();
            System.out.println("            "+word.getTarget());
            line = scanner.nextLine();
            System.out.println("*******************************");
            learningSession.notifyObservers(word, true);
        }


    }
}
