package events;

public interface SessionFeedbackListener {
    void onStreak(int streak);
    void onReviewPrepared(int words);
}
