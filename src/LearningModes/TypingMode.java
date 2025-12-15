package LearningModes;

import models.LearningSession;
import models.Word;
import models.WordSet;
import java.util.Scanner;

public class TypingMode implements LearningMode {
    @Override
    public void start(WordSet wordSet, LearningSession learningSession) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*******************************");
        System.out.println("          Tryb Wpisywania!");
        System.out.println("*******************************");
        System.out.println();
        for (Word word : wordSet.getWords()) {
            System.out.println("*******************************");
            System.out.println();
            System.out.println("            "+word.getSource());
            System.out.print("Wpisz tłumaczenie:");
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
