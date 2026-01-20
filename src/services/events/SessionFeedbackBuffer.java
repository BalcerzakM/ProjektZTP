package services.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Bufor komunikatów informacji zwrotnej
 * generowanych w trakcie sesji nauki.
 */
public class SessionFeedbackBuffer implements SessionFeedbackListener {

    /**
     * Lista komunikatów oczekujących na odczyt.
     */
    private final List<String> messages = new ArrayList<>();

    @Override
    public void onStreak(int streak) {
        switch(streak) {
            case 5,6:
                messages.add("🔥 " + streak + " poprawnych odpowiedzi z rzędu!\nDobra robota!");
                break;
            case 7,8,9:
                messages.add("🔥 " + streak + " poprawnych odpowiedzi z rzędu!\nŚwietnie!");
                break;
            case 10:
                messages.add("🔥 " + streak + " poprawnych odpowiedzi z rzędu!\nDOSKONALE!");
                break;
            default:
                messages.add("🔥 " + streak + " poprawnych odpowiedzi z rzędu!\nNIEMOŻLIWE!!!");
                break;
        }
    }

    @Override
    public void onReviewPrepared(int words) {
        messages.add("🔁 Masz " + words + " słów do powtórki!\nSprawdź lekcję powtórzeniową.");
    }

    /**
     * Zwraca wszystkie zgromadzone komunikaty i czyści bufor.
     *
     * Metoda przeznaczona do jednokrotnego użycia
     * po obsłużeniu odpowiedzi użytkownika.
     *
     * @return lista komunikatów wygenerowanych od ostatniego odczytu
     */
    public List<String> consumeMessages() {
        List<String> msg = List.copyOf(messages);
        messages.clear();
        return msg;
    }
}
