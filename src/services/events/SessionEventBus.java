package services.events;

import java.util.ArrayList;
import java.util.List;
/**
 * Magistrala zdarzeń odpowiedzialna za przekazywanie informacji
 * zwrotnej w obrębie jednej sesji nauki.
 *
 * Klasa pośredniczy pomiędzy obserwatorami odpowiedzi
 * (np. statystykami lub planowaniem powtórek)
 * a komponentami odpowiedzialnymi za prezentację feedbacku.
 *
 * EventBus jest tworzony na początku sesji i współdzielony
 * przez tryby nauki uruchamiane w jej trakcie.
 */
public class SessionEventBus {
    /**
     * Lista listenerów zdarzeń sesji.
     *
     * Listenery są rejestrowani dynamicznie przez kontrolery
     * i usuwani przy opuszczaniu trybu nauki.
     */
    private final List<SessionFeedbackListener> feedbacks = new ArrayList<>();

    /**
     * Rejestruje listenera zdarzeń sesji.
     *
     * @param feedback obiekt nasłuchujący informacji zwrotnej
     */
    public void register(SessionFeedbackListener feedback) {
        feedbacks.add(feedback);
    }

    /**
     * Usuwa wcześniej zarejestrowanego listenera.
     *
     * @param feedback listener do usunięcia
     */
    public void unregister(SessionFeedbackListener feedback) {
        feedbacks.remove(feedback);
    }

    /**
     * Przekazuje informację o aktualnej serii poprawnych odpowiedzi.
     *
     * @param streak liczba poprawnych odpowiedzi z rzędu
     */
    public void streakFeedback(int streak) {
        for (SessionFeedbackListener feedback : feedbacks) {
            feedback.onStreak(streak);
        }
    }

    /**
     * Przekazuje informację o przygotowaniu sesji powtórkowej.
     *
     * @param words liczba słów przeznaczonych do powtórki
     */
    public void reviewFeedback(int words) {
        for (SessionFeedbackListener feedback : feedbacks) {
            feedback.onReviewPrepared(words);
        }
    }
}
