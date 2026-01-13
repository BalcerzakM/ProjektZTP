package app;

import controllers.Controller;
import controllers.DataInputController;
import controllers.LearningSessionController;
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
        //this.controllers.put(AppState.MainMenu, new MainMenuController());
        this.controllers.put(AppState.LoadWordSet, new DataInputController());
        this.controllers.put(AppState.LearningSession, new LearningSessionController());
        //this.controllers.put(AppState.WordSetCreator, new WordSetCreatorController());
        //this.controllers.put(AppState.User, new UserController());
        //this.controllers.put(AppState.Statistics, new StatisticsController());
    }

    public void start() {
        mainFrame.setVisible(true);
        switchState(AppState.LoadWordSet);
    }

    public void switchState(AppState state) {
        Controller controller = null;
        if (controllers.containsKey(state)) {
            controller = controllers.get(state);
        } else {
            System.out.println("No controller found for state " + state);
        }

        if (controller != null) {
            controller.run(context, this);
        }
    }

    public void setView(JPanel panel) {
        mainFrame.showView(view);
    }
}
