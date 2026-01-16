package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.Authentication;
import models.LanguageCERFLevel;
import models.Statistics;
import models.User;
import views.LoginPanel;
import views.RegisterPanel;

import javax.swing.*;

public class AuthenticationController implements Controller {
    private final AppRouter router;
    private final AppContext context;
    private final Authentication model;
    private final LoginPanel loginPanel;
    private final RegisterPanel registerPanel;

    public AuthenticationController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
        this.loginPanel = new LoginPanel();
        this.registerPanel = new RegisterPanel();
        this.model = new Authentication();
        initLoginLogic();
        initRegisterLogic();

    }

    private void initLoginLogic() {
        loginPanel.onLoginBtn(this::handleLogin);
        loginPanel.onGoToRegisterPanelBtn(() -> router.setPanel(registerPanel, "REGISTER"));
    }

    private void initRegisterLogic() {
        registerPanel.onRegisterBtn(this::handleRegister);
        registerPanel.onGoToLoginPanelBtn(() -> router.setPanel(loginPanel, "LOGIN"));
    }

    @Override
    public void run() {
        router.setPanel(loginPanel, "LOGIN");
    }

    private void handleLogin() {
        String username = loginPanel.getUsername();
        String password = loginPanel.getPassword();
        if (username.isEmpty() || password.isEmpty()) {
            loginPanel.showEmptyFieldsError();
        } else {
            if (model.isUserAlreadyRegistered(username, context.getUserNamesList())) {
                if (model.isPasswordCorrect(context.getUserPasswordFromDb(username), password)) {
                    User user = context.getNewUserFromDb(username);
                    Statistics statistics = context.getNewStatisticsFromDb(username);
                    context.setCurrentUser(user);
                    context.setUserStatistics(statistics);
                    router.switchState(AppState.MainMenu);
                } else {
                    loginPanel.showWrongPasswordError();
                }
            } else {
                loginPanel.showNoUserExistError();
            }
        }
    }

    private void handleRegister() {
        String username = registerPanel.getUsername();
        String password = registerPanel.getPassword();
        String repeatedPassword = registerPanel.getRepeatedPassword();
        User user = new User(username, password, LanguageCERFLevel.A1);
        context.setCurrentUser(user);
        router.switchState(AppState.MainMenu);
    }
}