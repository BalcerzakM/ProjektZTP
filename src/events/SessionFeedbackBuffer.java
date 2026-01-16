package events;

import java.util.ArrayList;
import java.util.List;

public class SessionFeedbackBuffer implements SessionFeedbackView {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void onStreak(int streak) {
        messages.add("🔥 " + streak + " poprawnych odpowiedzi z rzędu!\nDobra robota!");
    }

    @Override
    public void onReviewPrepared(int words) {
        messages.add("🔁 Masz " + words + " słów do powtórki!\nSprawdź lekcję powtórzeniową.");
    }

    public List<String> consumeMessages() {
        List<String> msg = List.copyOf(messages);
        messages.clear();
        return msg;
    }
}
