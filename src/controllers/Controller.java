package controllers;

import app.AppContext;
import app.AppState;

public interface Controller {
    public AppState run(AppContext context);
}
