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

/**
 * Router aplikacji odpowiedzialny za zarządzanie nawigacją
 * pomiędzy głównymi stanami aplikacji.
 *
 * Klasa realizuje centralny mechanizm przełączania widoków
 * poprzez delegowanie sterowania do odpowiednich kontrolerów.
 *
 * AppRouter pełni rolę pośrednika pomiędzy:
 * - stanami aplikacji ({@link AppState}),
 * - kontrolerami ({@link Controller}),
 * - głównym oknem aplikacji ({@link MainFrame}).
 *
 * Rozwiązanie upraszcza zarządzanie przepływem aplikacji
 * i izoluje logikę nawigacji od poszczególnych kontrolerów.
 */
public class AppRouter {
    private final MainFrame mainFrame;
    private final Map<AppState, Controller> controllers = new HashMap<>();

    /**
     * Tworzy router aplikacji oraz rejestruje kontrolery
     * obsługujące poszczególne stany aplikacji.
     *
     * @param mainFrame główne okno aplikacji
     * @param context   kontekst aplikacji współdzielony pomiędzy kontrolerami
     */
    public AppRouter(MainFrame mainFrame, AppContext context) {
        this.mainFrame = mainFrame;
        this.controllers.put(AppState.Authentication, new AuthenticationController(this, context));
        this.controllers.put(AppState.MainMenu, new MainMenuController(this, context));
        this.controllers.put(AppState.LearningSession, new LearningSessionController(this, context));
        this.controllers.put(AppState.Statistics, new StatisticsController(this, context));
    }

    /**
     * Uruchamia aplikację.
     *
     * Metoda ustawia widoczność głównego okna oraz inicjuje
     * pierwszy stan aplikacji – ekran uwierzytelniania.
     */
    public void start() {
        mainFrame.setVisible(true);
        switchState(AppState.Authentication);
    }

    /**
     * Przełącza aplikację do wskazanego stanu.
     *
     * Na podstawie przekazanego stanu wyszukiwany jest
     * odpowiedni kontroler, którego metoda {@code run()}
     * inicjalizuje logikę i widok danego etapu aplikacji.
     *
     * @param state nowy stan aplikacji
     */
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

    /**
     * Ustawia aktualny panel widoku w głównym oknie aplikacji.
     *
     * @param panel panel Swing do wyświetlenia
     * @param name  nazwa widoku (identyfikator logiczny)
     */
    public void setPanel(JPanel panel, String name) {
        mainFrame.showView(panel, name);
    }


    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
