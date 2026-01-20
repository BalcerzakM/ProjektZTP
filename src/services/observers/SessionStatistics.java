package services.observers;

import services.events.SessionEventBus;
import models.Word;

import java.util.HashSet;
import java.util.Set;

/**
 * Obserwator odpowiedzi użytkownika odpowiedzialny za zbieranie
 * statystyk bieżącej sesji nauki.
 *
 * Klasa implementuje wzorzec Observer i reaguje na odpowiedzi
 * przesyłane przez model sesji. Na podstawie tych zdarzeń:
 * - zlicza poprawne i błędne odpowiedzi,
 * - śledzi serie poprawnych odpowiedzi (streak),
 * - oblicza procent poprawnych odpowiedzi,
 * - gromadzi słowa uznane za opanowane,
 * - generuje zdarzenia zwrotne poprzez SessionEventBus.
 *
 * Dane zgromadzone przez tę klasę są wykorzystywane do:
 * - podsumowania sesji,
 * - aktualizacji poziomu użytkownika,
 * - prezentowania informacji zwrotnych w trakcie nauki.
 */
public class SessionStatistics implements AnswerObserver {
    //statystyki na lekcje
    private int correctCount = 0;
    private int incorrectCount = 0;

    /**
     * Aktualna seria poprawnych odpowiedzi w ramach sesji.
     */
    private int streak = 0;

    /**
     * Najdłuższa seria poprawnych odpowiedzi osiągnięta w tej sesji.
     */
    private int maxSessionStreak = 0;

    /**
     * Procent poprawnych odpowiedzi obliczany na bieżąco
     * na podstawie odpowiedzi niebędących fiszkami.
     */
    private int correctPercent = 0;

    /**
     * Liczba interakcji w trybie fiszek.
     *
     * Wartość różna od zera pozwala rozróżnić sesję fiszkową
     * od klasycznych trybów z oceną poprawności.
     */
    private int flashCardCount = 0;

    /**
     * Zbiór słów, które zostały poprawnie rozwiązane przynajmniej raz
     * w trakcie sesji i mogą zostać uznane za opanowane.
     */
    private final Set<Word> learnedWords = new HashSet<>();

    /**
     * Magistrala zdarzeń sesji, wykorzystywana do przekazywania
     * informacji zwrotnej, np. o długiej serii poprawnych odpowiedzi.
     */
    private SessionEventBus eventBus;

    /**
     * Reaguje na odpowiedź użytkownika w bieżącej sesji.
     *
     * Metoda aktualizuje statystyki w zależności od:
     * - poprawności odpowiedzi,
     * - trybu nauki (fiszkowy lub oceniany),
     * - aktualnej serii poprawnych odpowiedzi.
     *
     * W przypadku osiągnięcia odpowiednio długiej serii poprawnych
     * odpowiedzi generowane jest zdarzenie feedbacku.
     *
     * @param w słowo, którego dotyczy odpowiedź; null oznacza tryb fiszek
     * @param correct informacja, czy odpowiedź była poprawna
     */
    @Override
    public void onAnswer(Word w, boolean correct) {
        if (w == null) {
            System.out.print(flashCardCount);
            flashCardCount++;
            return;
        }
        if (correct) {
            correctCount++;
            streak++;
            if (streak > maxSessionStreak) {
                maxSessionStreak = streak;
            }
            learnedWords.add(w);
        } else {
            incorrectCount++;
            streak = 0;
        }
        if (correctCount + incorrectCount > 0) {
            correctPercent = (correctCount*100)/(correctCount + incorrectCount);
        }

        if (streak > 4) {
            eventBus.streakFeedback(streak);
        }

    }

    /**
     * Ustawia magistralę zdarzeń wykorzystywaną do przekazywania feedbacku.
     *
     * @param eventBus magistrala zdarzeń sesji
     */
    public void setEventBus(SessionEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public int getCorrectCount() { return correctCount; }

    public int getIncorrectCount() { return incorrectCount; }

    public int getCorrectPercent() { return correctPercent; }

    public int getMaxSessionStreak() { return maxSessionStreak; }

    public Set<Word> getLearnedWords() { return learnedWords; }

    public int getFlashCardCount() { return flashCardCount; }

    /**
     * Informuje, czy sesja była w trybie fiszek.
     */
    public boolean isFlashCardSession() {
        return flashCardCount > 0;
    }

    /**
     * Informuje, czy wszystkie odpowiedzi w sesji były poprawne.
     */
    public boolean isPerfect() {
        return correctPercent == 100;
    }

    /**
     * Sprawdza, czy w sesji zapisano jakiekolwiek dane statystyczne.
     */
    public boolean hasAnyData() {
        return getCorrectCount() != 0 || getIncorrectCount() != 0 || getFlashCardCount() != 0;
    }

    /**
     * Resetuje wszystkie statystyki sesji.
     *
     * Metoda wywoływana po zapisaniu wyników sesji
     */
    public void resetStatistics() {
        correctCount = 0;
        incorrectCount = 0;
        streak = 0;
        maxSessionStreak = 0;
        correctPercent = 0;
        flashCardCount = 0;
        learnedWords.clear();
    }
}
