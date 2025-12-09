import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class TypingMode implements LearningMode{
    @Override
    public void start(WordSet wordSet) {
        Scanner scanner = new Scanner(System.in);
        QuestionFactory factory = new FlashCardQuestionFactory();
        System.out.println("*******************************");
        System.out.println("          Tryb Wpisywania!");
        System.out.println("*******************************");
        System.out.println();
        for (Word word : wordSet.getWords()) {
            Question q = factory.createQuestion(word, wordSet);
            System.out.println("*******************************");
            System.out.println();
            System.out.println("            "+q.getPrompt());
            System.out.printf("Wpisz tłumaczenie:");
            String line = scanner.nextLine();
            while(!line.equalsIgnoreCase(q.getCorrectAnswer())) {
                System.out.println("            Źle!");
                line = scanner.nextLine();
            }
            System.out.println("            Dobrze!");
            line = scanner.nextLine();
            System.out.println("*******************************");
        }


    }
}
