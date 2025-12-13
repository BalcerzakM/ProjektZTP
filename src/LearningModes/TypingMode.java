package LearningModes;

import models.LearningSession;
import models.Word;
import models.WordSet;
import questions.FlashCardQuestionFactory;
import questions.QuestionFactory;

import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class TypingMode implements LearningMode {
    @Override
    public void start(LearningSession learningSession) {
        Scanner scanner = new Scanner(System.in);
        QuestionFactory factory = new FlashCardQuestionFactory();
        System.out.println("*******************************");
        System.out.println("          Tryb Wpisywania!");
        System.out.println("*******************************");
        System.out.println();
        for (Word word : learningSession.getWordSet().getWords()) {
            //models.Question q = factory.createQuestion(word, wordSet);
            System.out.println("*******************************");
            System.out.println();
            System.out.println("            "+word.getSource());
            System.out.printf("Wpisz tłumaczenie:");
            String line = scanner.nextLine();
            while(!line.equalsIgnoreCase(word.getTarget())) {
                System.out.println("            Źle!");
                learningSession.notifyObservers(word, false);
                line = scanner.nextLine();
            }
            System.out.println("            Dobrze!");
            learningSession.notifyObservers(word, true);
            line = scanner.nextLine();
            System.out.println("*******************************");
        }


    }
}
