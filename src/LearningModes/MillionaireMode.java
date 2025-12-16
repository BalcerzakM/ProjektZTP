package LearningModes;

import models.LearningSession;
import models.Word;
import models.WordSet;
import observers.SessionStatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class MillionaireMode implements LearningMode {
    @Override
    public void start(WordSet wordSet, LearningSession learningSession) {
        Scanner scanner = new Scanner(System.in);
        List<Word> ws = wordSet.getWords();
        List<String> options = new ArrayList<String>();

        System.out.println("*******************************");
        System.out.println("          Tryb Milionerów!");
        System.out.println("*******************************");
        System.out.println();

        System.out.println("Ile pytań chcesz przerobić?");
        int ile = scanner.nextInt();

        for(int i=0;i<ile;i++) {
            System.out.println();
            System.out.println("*******************************");

        Word w = ws.get((int) (Math.random() * ws.size()));

        options.add(w.getTarget());

        if (ws.size() > 3) {
            while (options.size() < 4) {
                Word option = ws.get((int) (Math.random() * ws.size()));
                if (!options.contains(option.getTarget())) {
                    options.add(option.getTarget());
                }
            }
        }
        else {
            System.out.println("models.WordSet nie posiada wystarczająco wyrazów");
        }

        Collections.shuffle(options);


        System.out.println("Jakie jest poprawne tłumaczenie słowa: \"" + w.getSource() +"\"");
        int k=1;
        for (String option : options) {
            System.out.println(k+") " + option);
            k++;
        }
        int odp = scanner.nextInt() - 1;

        if (options.get(odp).equals(w.getTarget())) {
            System.out.println("            Dobrze! ");
            learningSession.notifyObservers(w, true);
        }

        else {
            System.out.println("            Źle! ");
            learningSession.notifyObservers(w, false);
        }
        options.clear();
        System.out.println("*******************************");
        String placeholder= scanner.nextLine();;
        placeholder = scanner.nextLine();
        }

        System.out.println("Ilość poprawnych odpowiedzi: " + learningSession.getStatistics().getCorrectCount());
        System.out.println("Super Wynik! Gratulacje!");
    }




}
