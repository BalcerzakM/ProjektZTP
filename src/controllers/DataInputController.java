package controllers;

import app.AppContext;
import app.AppState;
import models.Connector;
import models.WordSet;
import views.DataInputView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DataInputController implements Controller {
    public DataInputView view = new DataInputView(readFileList());

    @Override
    public AppState run(AppContext context) {
        while(true) {
            view.show();
            String line = view.showChooseFileNamePrompt();
            if (line.equals("review")) {
                if (!context.getReviewScheduler().getReviewWords().isEmpty()) {
                    WordSet reviewWordSet = new WordSet("review", context.getReviewScheduler().getReviewWords(), "review");
                    context.setCurrentWordSet(reviewWordSet);
                    return AppState.LearningSession;
                }
                else {
                    System.out.println("Brak słów do powtórzenia!"); //ten komuniakt bedzie pewnie w DataInputView
                    continue;
                }
            }
            String path = "resources/wordSets/" + line + ".txt";
            try {
                WordSet importedWordSet = Connector.getInstance().readWordSetFromFile(path);
                context.setCurrentWordSet(importedWordSet);
            } catch (FileNotFoundException e) {
                view.showError();
                continue;
            }
            return AppState.LearningSession;
        }
    }

    private List<String> readFileList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("default");
        return list;
    }

}
