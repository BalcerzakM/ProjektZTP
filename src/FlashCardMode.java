import java.util.Scanner;//jeszcze nie wiem gdzie go zostawie

public class FlashCardMode implements LearningMode{
    @Override
    public void start(WordSet wordSet) {
        Scanner scanner = new Scanner(System.in);
        QuestionFactory factory = new FlashCardQuestionFactory();
        for (Word word : wordSet.getWords()) {
            Question q = factory.createQuestion(word, wordSet);
            System.out.println(q);
            String line = scanner.nextLine();
        }


    }
}
