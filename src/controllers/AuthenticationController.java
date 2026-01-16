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
            return;
        }
        if (!model.isUserAlreadyRegistered(username, context.getUserNamesList())) {
            loginPanel.showNoUserExistError();
            return;
        }
        if (!model.isPasswordCorrect(context.getUserPasswordFromDb(username), password)) {
            loginPanel.showWrongPasswordError();
            return;
        }
        User user = context.getNewUserFromDb(username);
        Statistics statistics = context.getNewStatisticsFromDb(username);
        context.setCurrentUser(user);
        //context.setUserStatistics(statistics);
        loginPanel.showSuccessMessage();
        router.switchState(AppState.MainMenu);
    }

    private void handleRegister() {
        String username = registerPanel.getUsername();
        String password = registerPanel.getPassword();
        String repeatedPassword = registerPanel.getRepeatedPassword();
        if (username.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
            registerPanel.showEmptyFieldsError();
            return;
        }
        if (model.isUserAlreadyRegistered(username, context.getUserNamesList())) {
            registerPanel.showUserExistsError();
            return;
        }
        if (model.isPasswordCorrect(repeatedPassword, password)) {
            registerPanel.showRepeatedPasswordNotEqualError();
            return;
        }
        if (!model.isPasswordValidate(password, username)) {
            registerPanel.showTooEasyPasswordError();
            return;
        }
        User newUser = new User(username, password, LanguageCERFLevel.A1);
        context.saveNewUserToDb(newUser);
        registerPanel.showSuccessMessage();
        router.setPanel(loginPanel, "LOGIN");
    }
}