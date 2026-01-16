package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.WordSet;
import views.DatabaseSelectionPanel;
import views.MainMenuPanel;

import javax.swing.*;

public class MainMenuController implements Controller{
    private final AppRouter router;
    private final AppContext context;
    private final MainMenuPanel mainMenuPanel;
    private final DatabaseSelectionPanel dbSelectionPanel;

    public MainMenuController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
        this.mainMenuPanel = new MainMenuPanel();
        this.dbSelectionPanel = new DatabaseSelectionPanel(this.context.getDatabaseNamesList());
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

    }

    @Override
    public void run() {
        String userName = context.getCurrentUser().getUsername();
        mainMenuPanel.setWelcomeMessage(userName);
        if (context.isDatabaseSelected()) {
            router.setPanel(mainMenuPanel, "MENU");
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

            WordSet ws = context.getNewWordSetFromDb(selected);
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
