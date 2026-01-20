package services.events;

import java.util.ArrayList;
import java.util.List;
/**
 * Magistrala zdarzeń sesji nauki.
 *
 * Umożliwia przekazywanie informacji zwrotnej
 * pomiędzy obserwatorami odpowiedzi a komponentami
 * reagującymi na feedback w trakcie sesji.
 */
public class SessionEventBus {

    /**
     * Zarejestrowane listenery zdarzeń sesji.
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
