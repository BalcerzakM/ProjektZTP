package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import views.StatisticsPanel;

public class StatisticsController implements Controller {
    private AppRouter router;
    private AppContext context;

    public StatisticsController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
    }

    @Override
    public void run() {
        StatisticsPanel statisticsPanel = new StatisticsPanel();
        statisticsPanel.setUser(context.getCurrentUser());
        statisticsPanel.setStatistics(context.getCurrentUserStatistics());

        statisticsPanel.onBack(() ->
            router.switchState(AppState.StartMenu)
        );

        router.setPanel(statisticsPanel, "USER_STATS");
    }
}
