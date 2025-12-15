package LearningModes;

import models.LearningSession;
import models.Word;
import models.WordSet;


import java.util.List;
import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class FlashCardMode implements LearningMode {
    @Override
    public void start(WordSet wordSet, LearningSession learningSession) {
        Scanner scanner = new Scanner(System.in);
        List<Word> words = wordSet.getWords();
        int currentIndex = 0;

        System.out.println("*******************************");
        System.out.println("          Tryb Fiszek!");
        System.out.println("*******************************");
        System.out.println();

        if(learningSession.getMemento() != null) {
            currentIndex = learningSession.getMemento().getQuestionIndex();
        }
        for (; currentIndex < wordSet.getWords().size() ; currentIndex++) {
            System.out.println("*******************************");
            System.out.println();
            System.out.println("            "+words.get(currentIndex).getSource());
            String line = scanner.nextLine();
            System.out.println("            "+words.get(currentIndex).getTarget());
            line = scanner.nextLine();
            System.out.println("*******************************");
            if(line.equals("ESCAPE")) {
                learningSession.saveMemento(currentIndex);
                break;
            }
        }


    }
}
