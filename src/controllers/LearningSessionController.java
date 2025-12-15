package controllers;

import LearningModes.ConnectMode;
import LearningModes.FlashCardMode;
import LearningModes.MillionaireMode;
import LearningModes.TypingMode;
import app.AppContext;
import app.AppState;
import models.OverallStatistics;
import observers.ReviewScheduler;
import observers.SessionStatistics;
import views.LearningSessionView;
import models.LearningSession;

public class LearningSessionController implements Controller {
    public LearningSession model = new LearningSession();
    public LearningSessionView view;
    @Override
    public AppState run(AppContext context) {
        model.registerObserver(new ReviewScheduler());

        view = new LearningSessionView(context.getCurrentWordSet().getName());
        while(true) {
            SessionStatistics sessionStatistics = new SessionStatistics();
            model.registerObserver(sessionStatistics);

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

            System.out.println(sessionStatistics.showStatistics());
            OverallStatistics.getInstance().addToOverallStats(sessionStatistics);
            model.unregisterObserver(sessionStatistics);

            System.out.println(OverallStatistics.getInstance().showOverallStatistics()); //TO TYLKO DO TESTOW, BARDZIEJ JAKO OPCJA DLA USERA BEDZIE
        }
    }
}
