package services.observers;

import java.util.ArrayList;
import java.util.List;

import services.events.SessionEventBus;
import models.Word;

/**
 * Obserwator odpowiedzi odpowiedzialny za
 * identyfikację słów wymagających powtórki
 * i przygotowanie danych do sesji review.
 */
public class ReviewScheduler implements AnswerObserver {
    private final List<Word> reviewWords = new ArrayList<>();
    private boolean wasAlertShown = false;

    private SessionEventBus eventBus;

    /**
     * Ustawia magistralę zdarzeń sesji.
     */
    public void attachEventBus(SessionEventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Reaguje na odpowiedź użytkownika i aktualizuje
     * listę słów przeznaczonych do powtórki.
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