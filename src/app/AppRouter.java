package app;

import controllers.AuthenticationController;
import controllers.Controller;
import controllers.LearningSessionController;
import controllers.StatisticsController;
import controllers.MainMenuController;
import views.MainFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class AppRouter {
    private final MainFrame mainFrame;
    private final Map<AppState, Controller> controllers = new HashMap<>();

    public AppRouter(MainFrame mainFrame, AppContext context) {
        this.mainFrame = mainFrame;
        this.controllers.put(AppState.Authentication, new AuthenticationController(this, context));
        this.controllers.put(AppState.MainMenu, new MainMenuController(this, context));
        this.controllers.put(AppState.LearningSession, new LearningSessionController(this, context));
        //this.controllers.put(AppState.WordSetCreator, new WordSetCreatorController(this));
        //this.controllers.put(AppState.User, new UserController(this));
        this.controllers.put(AppState.Statistics, new StatisticsController(this, context));
    }

    public void start() {
        mainFrame.setVisible(true);
        switchState(AppState.Authentication);
    }

    public void switchState(AppState state) {
        Controller controller = null;
        if (controllers.containsKey(state)) {
            controller = controllers.get(state);
        } else {
            System.out.println("No controller found for state " + state);
        }

        if (controller != null) {
            controller.run();
        }
    }

    public void setPanel(JPanel panel, String name) {
        mainFrame.showView(panel, name);
    }


    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
