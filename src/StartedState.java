public class StartedState implements SessionState {

    @Override
    public void start(LearningSession session) {
        System.out.println("Lekcja jest już zaczęta");
    }

    @Override
    public void end(LearningSession session) {
        //fajnie byłoby tu dać statystyki
        session.setState(new EndedState());
    }

    @Override
    public void pause(LearningSession session) {
        session.setState(new PausedState());
    }

    @Override
    public void resume(LearningSession session) {
    }
}
