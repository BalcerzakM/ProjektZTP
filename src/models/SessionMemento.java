package models;
import java.util.List;

public class SessionMemento {

    private final int questionIndex;
    private final List<String> answersSnapshot;
    private final long randomSeed;

    public SessionMemento(
            int index,
            List<String> answersSnapshot,
            long randomSeed
    ) {
        this.questionIndex = index;
        this.answersSnapshot = List.copyOf(answersSnapshot);
        this.randomSeed = randomSeed;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public List<String> getAnswersSnapshot() {
        return answersSnapshot;
    }

    public long getSeed() {
        return randomSeed;
    }
}
