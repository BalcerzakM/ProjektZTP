package controllers;

import LearningModes.ConnectMode;
import LearningModes.FlashCardMode;
import LearningModes.MillionaireMode;
import LearningModes.TypingMode;
import app.AppContext;
import app.AppState;
import observers.ReviewScheduler;
import observers.Statistics;
import views.LearningSessionView;
import models.LearningSession;

import java.util.Scanner;

public class LearningSessionController implements Controller {
    public LearningSession model = new LearningSession();
    public LearningSessionView view;
    @Override
    public AppState run(AppContext context) {
        Statistics statistics = Statistics.getInstance();
        model.registerObserver(statistics);
        model.registerObserver(new ReviewScheduler());
        view = new LearningSessionView(context.getCurrentWordSet().getName());
        while(true) {
            view.showMainPage();
            String line = view.showChooseModePrompt();
            int command = Integer.parseInt(line);
            switch (command) {
                case 1:
                    model.setMode(new FlashCardMode());
                    break;
                case 2:
                    model.setMode(new ConnectMode());
                    break;
                case 3:
                    model.setMode(new MillionaireMode());
                    break;
                case 4:
                    model.setMode(new TypingMode());
                    break;
                default:
                    view.showError();
                    continue;
            }
            model.getMode().start(context.getCurrentWordSet(), model);

            System.out.println(statistics.showStatistics());
            statistics.addToOverallStats();
            statistics.resetStatistics();
            System.out.println(statistics.showOverallStatistics()); //TO TYLKO DO TESTOW, BARDZIEJ JAKO OPCJA DLA USERA BEDZIE
        }
    }
}
