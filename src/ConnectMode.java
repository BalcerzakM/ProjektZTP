import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class ConnectMode implements LearningMode{
    @Override
    public void start(WordSet wordSet) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*******************************");
        System.out.println("          Tryb Łączenia!");
        System.out.println("*******************************");
        System.out.println();

        List<Word> ws = wordSet.getWords();
        List<Word> tab1 = new ArrayList<Word>();
        List<String> tab2 = new ArrayList<String>();

        if (ws.size() > 3) {
            while (tab1.size() < 4 && tab2.size() < 4) {
                Word option = ws.get((int) (Math.random() * ws.size()));
                if (!tab1.contains(option)) {
                    tab1.add(option);
                    tab2.add(option.getTarget());
                }
            }
        }
        else {
            System.out.println("WordSet nie posiada wystarczająco wyrazów");
        }

        Collections.shuffle(tab2);


        while (!tab1.isEmpty()) {

            connectPrint(tab2, tab1);

            System.out.println("Wybierz element z lewej kolumny (1-" + tab1.size() + "): ");
            int left = scanner.nextInt()-1;

            System.out.println("Wybierz element z prawej kolumny (1-" + tab2.size() + "): ");
            int right = scanner.nextInt()-1;

            if (tab1.get(left).getTarget().equals(tab2.get(right))) {
                System.out.println("            Dobrze!");
                tab1.remove(left);
                tab2.remove(right);

            } else {
                System.out.println("            Źle! Spróbuj ponownie.");
            }
        }

        System.out.println("Wszystkie połączone! Gratulacje!");
    }

    private static void connectPrint(List<String> tab2, List<Word> tab1) {
        System.out.println("*******************************");
        for (int i = 0; i < tab2.size(); i++) {
            System.out.println();
            System.out.println(i+1+"  "+ tab1.get(i).getSource() + "  |  " + tab2.get(i));
            System.out.println();
        }
        System.out.println("*******************************");
    }


}
