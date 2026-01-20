package services.observers;

import java.util.ArrayList;
import java.util.List;

import services.events.SessionEventBus;
import models.Word;

/**
 * Obserwator odpowiedzi odpowiedzialny za planowanie sesji powtórzeniowej.
 *
 * Klasa implementuje wzorzec Observer i analizuje odpowiedzi użytkownika
 * w trakcie sesji nauki. Jej zadaniem jest identyfikacja słów, które sprawiają
 * trudność i powinny zostać powtórzone w osobnej sesji.
 *
 * ReviewScheduler:
 * - gromadzi słowa, przy których użytkownik popełnił błąd,
 * - zapobiega wielokrotnemu dodawaniu tych samych słów,
 * - generuje informację zwrotną po osiągnięciu odpowiedniej liczby słów do powtórki,
 * - przygotowuje dane wejściowe do trybu powtórzeniowego.
 *
 * Klasa nie odpowiada za prezentację powtórek ani ich uruchamianie,
 * a jedynie za decyzję, które słowa wymagają ponownego utrwalenia.
 */
public class ReviewScheduler implements AnswerObserver {

    /**
     * Lista słów przeznaczonych do powtórzenia.
     *
     * Zawiera wyłącznie słowa, przy których użytkownik udzielił
     * niepoprawnej odpowiedzi co najmniej raz w bieżącej sesji.
     */
    private final List<Word> reviewWords = new ArrayList<>();

    /**
     * Flaga zabezpieczająca przed wielokrotnym wyświetlaniem
     * komunikatu o gotowości sesji powtórkowej.
     */
    private boolean wasAlertShown = false;

    /**
     * Magistrala zdarzeń sesji wykorzystywana do przekazywania
     * informacji zwrotnej o liczbie słów do powtórzenia.
     */
    private SessionEventBus eventBus;

    /**
     * Dołącza magistralę zdarzeń do obserwatora.
     *
     * Metoda wywoływana podczas inicjalizacji sesji nauki.
     *
     * @param eventBus magistrala zdarzeń sesji
     */
    public void attachEventBus(SessionEventBus eventBus) {
        this.eventBus = eventBus;
    }


    /**
     * Reaguje na odpowiedź użytkownika w trakcie sesji.
     *
     * W przypadku niepoprawnej odpowiedzi:
     * - słowo zostaje dodane do listy powtórkowej (jeśli jeszcze jej nie zawiera),
     * - sprawdzany jest próg liczby słów wymaganych do uruchomienia powtórki.
     *
     * Po osiągnięciu odpowiedniej liczby słów generowane jest zdarzenie
     * informujące użytkownika o możliwości rozpoczęcia sesji powtórkowej.
     *
     * @param w słowo, którego dotyczy odpowiedź
     * @param correct informacja, czy odpowiedź była poprawna
     */
    @Override
    public void onAnswer(Word w, boolean correct) {

        if (!correct && !reviewWords.contains(w)) {
            reviewWords.add(w);
        }

        if (reviewWords.size() % 5 == 0 && reviewWords.size() > 9 && !wasAlertShown) {
            if (eventBus != null) {
                eventBus.reviewFeedback(reviewWords.size());
            }
            wasAlertShown = true;
        }

        if (reviewWords.size() % 5 != 0) {
            wasAlertShown = false;
        }
    }

    /**
     * Zwraca aktualną listę słów przeznaczonych do powtórki.
     *
     * @return lista słów do powtórzenia
     */
    public List<Word> getReviewWords() {
        return reviewWords;
    }

    /**
     * Przygotowuje dane do sesji powtórkowej.
     *
     * Metoda zwraca kopię listy słów do powtórzenia,
     * a następnie czyści stan obserwatora, umożliwiając
     * gromadzenie danych dla kolejnej sesji.
     *
     * @return lista słów używana w sesji powtórkowej
     */
    public List<Word> loadReviewWords() {
        List<Word> reviewWordsCopy = new ArrayList<>(reviewWords);
        reviewWords.clear();
        wasAlertShown = false;
        return reviewWordsCopy;
    }
}