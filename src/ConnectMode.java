import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class ConnectMode implements LearningMode{
    @Override
    public void start(WordSet wordSet) {
        Scanner scanner = new Scanner(System.in);
        QuestionFactory factory = new FlashCardQuestionFactory();
        System.out.println("*******************************");
        System.out.println("          Tryb Łączenia!");
        System.out.println("*******************************");
        System.out.println();

        List<String> tab1 = new ArrayList<String>();
        List<String> tab2 = new ArrayList<String>();

        for (Word word : wordSet.getWords()) {

            tab1.add(word.getSource());
            tab2.add(word.getTarget());

            //Question q = factory.createQuestion(word, wordSet);
            System.out.println("*******************************");
            System.out.println();
            System.out.println("            "+q.getPrompt());
            String line = scanner.nextLine();
            System.out.println("            "+q.getCorrectAnswer());
            line = scanner.nextLine();
            System.out.println("*******************************");
        }


    }
}
