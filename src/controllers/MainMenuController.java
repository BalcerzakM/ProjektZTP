package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.Connector;
import models.WordSet;
import views.DatabaseSelectionPanel;
import views.MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainMenuController implements Controller{
    private final AppRouter router;
    private final AppContext context;
    private final MainMenuPanel mainMenuPanel;
    private final DatabaseSelectionPanel dbSelectionPanel;

    public MainMenuController(AppRouter router, AppContext context) {
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
        mainMenuPanel.onChangeWordSetBtn(() -> router.setView(dbSelectionPanel, "DB_SELECTION"));

    }

    @Override
    public void run() {
        if (context.isUserLoggedIn() && context.isDatabaseSelected()) {
            router.setView(mainMenuPanel, "MENU");
        } else if (context.isUserLoggedIn()) {
            router.setView(dbSelectionPanel, "DB_SELECTION");
        } else {
            router.setView(dbSelectionPanel, "DB_SELECTION");
        }
    }

    private void handleLoad() {
        String selected = dbSelectionPanel.getSelectedFile();

        if (selected == null) {
            dbSelectionPanel.showError("Wybierz plik z listy.");
            return;
        }

            WordSet ws = context.getWordSet(selected);
            context.setCurrentWordSet(ws);
            router.switchState(AppState.LearningSession);
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
