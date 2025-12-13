public interface SessionState {
    void start(LearningSession session);
    void end(LearningSession session);
    void pause(LearningSession session);
    void resume(LearningSession session);

}
