package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import views.MainMenuPanel;

public class MainMenuController implements Controller{
    private final AppRouter router;
    private final AppContext context;
    private final MainMenuPanel mainMenuPanel;

    public MainMenuController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
        this.mainMenuPanel = new MainMenuPanel();
        initMainMenuLogic();

    }


    private void initMainMenuLogic() {
        mainMenuPanel.onLearningSessionBtn(() -> router.switchState(AppState.LearningSession));
        mainMenuPanel.onLogOutBtn(this::handleLogOut);
        mainMenuPanel.onStatisticsBtn(() -> router.switchState(AppState.Statistics));

    }

    @Override
    public void run() {
        String userName = context.getCurrentUser().getUsername();
        mainMenuPanel.setWelcomeMessage(userName);
        router.setPanel(mainMenuPanel, "MENU");
    }

    private void handleLogOut() {
        context.setCurrentUser(null);
        context.setUserStatistics(null);
        mainMenuPanel.showLogOutMessage();
        router.switchState(AppState.Authentication);
    }
}
