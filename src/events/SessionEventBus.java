package events;

import java.util.ArrayList;
import java.util.List;

public class SessionEventBus {
    private final List<SessionFeedbackView> feedbacks = new ArrayList<>();

    public void register(SessionFeedbackView feedback) {
        feedbacks.add(feedback);
    }

    public void unregister(SessionFeedbackView feedback) {
        feedbacks.remove(feedback);
    }

    public void streakFeedback(int streak) {
        for (SessionFeedbackView feedback : feedbacks) {
            feedback.onStreak(streak);
        }
    }

    public void reviewFeedback(int words) {
        for (SessionFeedbackView feedback : feedbacks) {
            feedback.onReviewPrepared(words);
        }
    }
}
