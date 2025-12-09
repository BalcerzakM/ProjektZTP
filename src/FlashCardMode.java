import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class FlashCardMode implements LearningMode{
    @Override
    public void start(WordSet wordSet) {
        Scanner scanner = new Scanner(System.in);
        QuestionFactory factory = new FlashCardQuestionFactory();
        System.out.println("*******************************");
        System.out.println("          Tryb Fiszek!");
        System.out.println("*******************************");
        System.out.println();
        for (Word word : wordSet.getWords()) {
            Question q = factory.createQuestion(word, wordSet);
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
