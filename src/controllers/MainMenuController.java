package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import views.MainMenuPanel;

public class MainMenuController implements Controller{
    private final AppRouter router;
    private final MainMenuPanel panel;

    public MainMenuController(AppRouter router) {
        this.router = router;
        this.panel = new MainMenuPanel();
        initListeners();
    }

    private void initListeners() {
        panel.onLearningSessionBtn(() -> {
            router.switchState(AppState.LearningSession);
        });

    }

    @Override
    public void run(AppContext context) {
        router.setView(panel, "MENU");
    }
}
