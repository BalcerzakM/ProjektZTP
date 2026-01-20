package services.observers;

import models.Word;

/**
 * Interfejs obserwatora odpowiedzi użytkownika
 * w trakcie sesji nauki.
 *
 * Implementacje reagują na odpowiedzi udzielane
 * w różnych trybach nauki.
 */
public interface AnswerObserver {

    /**
     * Reaguje na odpowiedź użytkownika.
     *
     * @param w słowo, którego dotyczy odpowiedź; null w trybie fiszek
     * @param correct informacja, czy odpowiedź była poprawna
     */
    void onAnswer(Word w, boolean correct);
}
