package controllers;

import app.AppContext;
import app.AppState;
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
            String line= view.prompt("Wybierz zestaw słówek, którego chcesz się nauczyć: ");
            try {
                context.connector.readWordSetFromFile(line);
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
