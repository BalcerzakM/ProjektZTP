package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.Connector;
import models.WordSet;
import views.MainFrame;
import views.DataInputPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

public class DataInputController implements Controller {
    AppRouter router;

    public DataInputController(AppRouter router) {
        this.router = router;
    }

    @Override
    public void run(AppContext context) {

        DataInputPanel panel = new DataInputPanel(readFileList());

        panel.onLoad(e -> handleLoad(context, panel));
        panel.onReview(e -> handleReview(context, panel));

        router.setView(panel, "DATA_INPUT");
    }

    private void handleLoad(AppContext context, DataInputPanel panel) {
        String selected = panel.getSelectedFile();

        if (selected == null) {
            panel.showError("Wybierz plik z listy.");
            return;
        }

        String path = "resources/wordSets/" + selected + ".txt";

        try {
            WordSet ws = Connector.getInstance().readWordSetFromFile(path);
            context.setCurrentWordSet(ws);
            router.switchState(AppState.LearningSession);

        } catch (FileNotFoundException e) {
            panel.showError("Nie znaleziono pliku: " + selected);
        }
    }

    private void handleReview(AppContext context, Component parentComponent) {

        if (context.getReviewScheduler().getReviewWords().size() < 5) {
            JOptionPane.showMessageDialog(
                    parentComponent,
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

    private List<String> readFileList() {
        return List.of("default"); // później: skan katalogu
    }
}
