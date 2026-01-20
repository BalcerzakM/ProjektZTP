package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.Statistics;
import models.User;
import views.StatisticsPanel;

/**
 * Kontroler odpowiedzialny za prezentację
 * globalnych statystyk użytkownika.
 *
 * Klasa pobiera dane użytkownika i jego statystyki
 * z kontekstu aplikacji oraz konfiguruje widok
 * statystyk ogólnych.
 */
public class StatisticsController implements Controller {
    private final AppRouter router;
    private final AppContext context;

    public StatisticsController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
    }

    /**
     * Inicjalizuje widok statystyk użytkownika
     * i obsługuje powrót do menu głównego.
     */
    @Override
    public void run() {
        User currentUser = context.getCurrentUser();
        Statistics stats = context.getCurrentUserStatistics();

        StatisticsPanel statisticsPanel = new StatisticsPanel();

        statisticsPanel.setUser(currentUser);
        statisticsPanel.setStatistics(stats);
        statisticsPanel.setProgressBar(stats.getLevelProgressPercent(currentUser.getLanguageLevel()));

        statisticsPanel.onBack(() ->
            router.switchState(AppState.MainMenu)
        );

        router.setPanel(statisticsPanel, "USER_STATS");
    }
}
