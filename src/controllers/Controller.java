package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;

public interface Controller {
    public void run(AppContext context);
}
