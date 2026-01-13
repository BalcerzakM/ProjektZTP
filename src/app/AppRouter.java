package app;

import controllers.Controller;
import controllers.DataInputController;
import controllers.LearningSessionController;
import controllers.MainMenuController;
import views.MainFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class AppRouter {
    private final MainFrame mainFrame;
    private final AppContext context;
    private final Map<AppState, Controller> controllers = new HashMap<>();

    public AppRouter(MainFrame mainFrame, AppContext context) {
        this.mainFrame = mainFrame;
        this.context = context;
        this.controllers.put(AppState.MainMenu, new MainMenuController(this));
        this.controllers.put(AppState.LoadWordSet, new DataInputController(this));
        this.controllers.put(AppState.LearningSession, new LearningSessionController(this));
        //this.controllers.put(AppState.WordSetCreator, new WordSetCreatorController(this));
        //this.controllers.put(AppState.User, new UserController(this));
        //this.controllers.put(AppState.Statistics, new StatisticsController(this));
    }

    public void start() {
        mainFrame.setVisible(true);
        switchState(AppState.MainMenu);
    }

    public void switchState(AppState state) {
        Controller controller = null;
        if (controllers.containsKey(state)) {
            controller = controllers.get(state);
        } else {
            System.out.println("No controller found for state " + state);
        }

        if (controller != null) {
            controller.run(context);
        }
    }

    public void setView(JPanel panel, String name) {
        mainFrame.showView(panel, name);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
