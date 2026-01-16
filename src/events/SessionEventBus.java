package events;

import java.util.ArrayList;
import java.util.List;

public class SessionEventBus {
    private final List<SessionFeedbackListener> feedbacks = new ArrayList<>();

    public void register(SessionFeedbackListener feedback) {
        feedbacks.add(feedback);
    }

    public void unregister(SessionFeedbackListener feedback) {
        feedbacks.remove(feedback);
    }

    public void streakFeedback(int streak) {
        for (SessionFeedbackListener feedback : feedbacks) {
            feedback.onStreak(streak);
        }
    }

    public void reviewFeedback(int words) {
        for (SessionFeedbackListener feedback : feedbacks) {
            feedback.onReviewPrepared(words);
        }
    }
}
