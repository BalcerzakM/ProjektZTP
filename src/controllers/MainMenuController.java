package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import views.MainMenuPanel;

/**
 * Kontroler menu głównego aplikacji.
 *
 * Odpowiada za prezentację menu startowego oraz
 * obsługę przejść pomiędzy głównymi obszarami aplikacji:
 * sesją nauki, statystykami oraz procesem wylogowania.
 *
 * Klasa pełni rolę Controller we wzorcu MVC
 * i stanowi punkt wejścia do głównych funkcjonalności systemu.
 */
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

    /**
     * Uruchamia widok menu głównego.
     *
     * Metoda ustawia komunikat powitalny dla zalogowanego użytkownika
     * oraz wyświetla menu w głównym oknie aplikacji.
     */
    @Override
    public void run() {
        String userName = context.getCurrentUser().getUsername();
        mainMenuPanel.setWelcomeMessage(userName);
        router.setPanel(mainMenuPanel, "MENU");
    }

    /**
     * Obsługuje proces wylogowania użytkownika.
     *
     * Metoda zapisuje aktualny stan aplikacji,
     * czyści kontekst użytkownika i przekierowuje
     * do ekranu uwierzytelniania.
     */
    private void handleLogOut() {
        context.saveToDbAndExit();
        context.setCurrentUser(null);
        context.setUserStatistics(null);
        mainMenuPanel.showLogOutMessage();
        router.switchState(AppState.Authentication);
    }
}
