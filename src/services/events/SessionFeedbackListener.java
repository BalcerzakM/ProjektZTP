package services.events;

/**
 * Interfejs listenera informacji zwrotnej w obrębie sesji nauki.
 *
 * Definiuje kontrakt dla komponentów reagujących
 * na zdarzenia generowane podczas rozwiązywania zadań,
 * takie jak serie poprawnych odpowiedzi lub przygotowanie powtórek.
 */
public interface SessionFeedbackListener {

    /**
     * Obsługuje zdarzenie związane z serią poprawnych odpowiedzi.
     *
     * @param streak liczba poprawnych odpowiedzi z rzędu
     */
    void onStreak(int streak);

    /**
     * Obsługuje zdarzenie informujące o przygotowaniu słów do powtórki.
     *
     * @param words liczba słów przeznaczonych do powtórzenia
     */
    void onReviewPrepared(int words);
}
