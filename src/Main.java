import app.AppContext;
import app.AppState;
import controllers.Controller;
import controllers.DataInputController;
import controllers.LearningSessionController;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<AppState, Controller> controllers = new HashMap<>();
        controllers.put(AppState.ChoosingDatabase, new DataInputController());
        controllers.put(AppState.LearningSession, new LearningSessionController());

        AppState currentState = AppState.ChoosingDatabase;
        AppContext context = new AppContext();

        while (currentState != AppState.Quit) {
            if (controllers.containsKey(currentState)) {
                Controller controller = controllers.get(currentState);
                currentState = controller.run(context);
            } else {
                System.out.println("Błąd: NIeznany stan aplikacji.");
                break;
            }
        }
        System.out.println("Zamknięto aplikację.");
    }
}