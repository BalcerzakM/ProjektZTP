package models;
import java.util.List;
/**
 * Obiekt przechowujący migawkę stanu sesji nauki.
 *
 * Klasa realizuje rolę Memento we wzorcu projektowym Memento
 * i umożliwia zapis oraz późniejsze odtworzenie stanu
 * konkretnego trybu nauki.
 *
 * Przechowywane dane pozwalają na:
 * - powrót do aktualnego pytania,
 * - zachowanie kolejności losowania,
 * - odtworzenie odpowiedzi.
 *
 * Obiekt nie jest modyfikowalny po utworzeniu.
 */
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
