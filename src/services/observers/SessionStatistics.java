package services.observers;

import services.events.SessionEventBus;
import models.Word;

import java.util.HashSet;
import java.util.Set;

/**
 * Obserwator odpowiedzi użytkownika odpowiedzialny za
 * zbieranie statystyk bieżącej sesji nauki oraz
 * generowanie informacji zwrotnej.
 */

public class SessionStatistics implements AnswerObserver {
    //statystyki na lekcje
    private int correctCount = 0;
    private int incorrectCount = 0;

    private int streak = 0;
    private int maxSessionStreak = 0;
    private int correctPercent = 0;
    private int flashCardCount = 0;
    private final Set<Word> learnedWords = new HashSet<>();

    private SessionEventBus eventBus;

    /**
     * Reaguje na odpowiedź użytkownika i aktualizuje
     * statystyki bieżącej sesji.
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
