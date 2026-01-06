package controllers;

import app.AppContext;
import app.AppState;
import models.Connector;
import models.WordSet;
import views.MainFrame;
import views.DataInputPanel;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.List;

public class DataInputController implements Controller {

    private final MainFrame frame;

    public DataInputController(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public AppState run(AppContext context) {

        DataInputPanel panel = new DataInputPanel(readFileList());

        panel.onLoad(e -> handleLoad(context, panel));
        panel.onReview(e -> handleReview(context));

        frame.setView(panel, "DATA_INPUT");
        return null;
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
            frame.switchState(AppState.LearningSession);

        } catch (FileNotFoundException e) {
            panel.showError("Nie znaleziono pliku: " + selected);
        }
    }

    private void handleReview(AppContext context) {

        if (context.getReviewScheduler().getReviewWords().isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Brak słów do powtórzenia!",
                    "Review",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        WordSet review = new WordSet(
                "review",
                context.getReviewScheduler().getReviewWords(),
                "review"
        );

        context.setCurrentWordSet(review);
        frame.switchState(AppState.LearningSession);
    }

    private List<String> readFileList() {
        return List.of("default"); // później: skan katalogu
    }
}
