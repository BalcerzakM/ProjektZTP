package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.WordSet;
import views.DatabaseSelectionPanel;
import views.MainMenuPanel;
import views.StatisticsPanel;

import javax.swing.*;

public class StartMenuController implements Controller{
    private final AppRouter router;
    private final AppContext context;
    private final MainMenuPanel mainMenuPanel;
    private final DatabaseSelectionPanel dbSelectionPanel;

    public StartMenuController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
        this.mainMenuPanel = new MainMenuPanel();
        this.dbSelectionPanel = new DatabaseSelectionPanel(this.context.getDatabaseList());
        initDbSelectionLogic();
        initMainMenuLogic();

    }

    private void initDbSelectionLogic() {
        dbSelectionPanel.onLoadBtn(e -> handleLoad());
        dbSelectionPanel.onReviewBtn(e -> handleReview());
    }

    private void initMainMenuLogic() {
        mainMenuPanel.onLearningSessionBtn(() -> router.switchState(AppState.LearningSession));
        mainMenuPanel.onChangeWordSetBtn(() -> router.setPanel(dbSelectionPanel, "DB_SELECTION"));
        mainMenuPanel.onStatisticsBtn(() -> router.switchState(AppState.Statistics));
    }

    @Override
    public void run() {
        if (context.isUserLoggedIn() && context.isDatabaseSelected()) {
            router.setPanel(mainMenuPanel, "MENU");
        } else if (context.isUserLoggedIn()) {
            router.setPanel(dbSelectionPanel, "DB_SELECTION");
        } else {
            router.setPanel(dbSelectionPanel, "DB_SELECTION");
        }
    }

    private void handleLoad() {
        String selected = dbSelectionPanel.getSelectedFile();

        if (selected == null) {
            dbSelectionPanel.showError("Wybierz plik z listy.");
            return;
        }

            WordSet ws = context.getNewWordSet(selected);
            context.setCurrentWordSet(ws);
            router.setPanel(mainMenuPanel, "MENU");
    }

    private void handleReview() {

        if (context.getReviewScheduler().getReviewWords().size() < 5) {
            JOptionPane.showMessageDialog(
                    dbSelectionPanel,
                    "Za mało słów do powtórzenia!",
                    "Review",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        WordSet review = new WordSet(
                "review",
                context.getReviewScheduler().loadReviewWords(),
                "review"
        );
        System.out.println(review.getWords().size());

        context.setCurrentWordSet(review);
        router.switchState(AppState.LearningSession);
    }
}
