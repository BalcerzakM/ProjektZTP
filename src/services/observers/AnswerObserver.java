package services.observers;

import models.Word;

/**
 * Interfejs obserwatora odpowiedzi użytkownika w trakcie sesji nauki.
 *
 * Definiuje kontrakt dla obiektów reagujących na odpowiedzi
 * udzielane w różnych trybach nauki.
 *
 * Implementacje tego interfejsu mogą gromadzić statystyki,
 * planować powtórki lub generować informacje zwrotne.
 */
public interface AnswerObserver {

    /**
     * Reaguje na odpowiedź użytkownika.
     *
     * Parametr w może przyjmować wartość null w przypadku trybów,
     * które nie operują na pojedynczych słowach, takich jak tryb fiszek.
     *
     * @param w słowo, którego dotyczy odpowiedź, lub null
     * @param correct informacja, czy odpowiedź została uznana za poprawną
     */
    void onAnswer(Word w, boolean correct);
}
