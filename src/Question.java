import java.util.List;

public class Question {
    String prompt;
    String correctAnswer;
    List<String> options;

    public Question(String prompt, String correctAnswer, List<String> options) {
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public String getPrompt() {
        return prompt;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Zapytanie: \"" + prompt + " \" Odpowiedż: \"" + correctAnswer + "\" Opcje " + options;
    }
}
