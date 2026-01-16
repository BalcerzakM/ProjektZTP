package events;

public interface SessionFeedbackView {
    void onStreak(int streak);
    void onReviewPrepared(int words);
}
