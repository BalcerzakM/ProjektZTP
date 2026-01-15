package controllers;

import app.AppContext;
import app.AppRouter;
import app.AppState;
import models.LanguageCERFLevel;
import models.User;
import views.LoginPanel;
import views.RegisterPanel;

public class AuthenticationController implements Controller {
    private final AppRouter router;
    private final AppContext context;
    private final LoginPanel loginPanel;
    private final RegisterPanel registerPanel;

    public AuthenticationController(AppRouter router, AppContext context) {
        this.router = router;
        this.context = context;
        this.loginPanel = new LoginPanel();
        this.registerPanel = new RegisterPanel();
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
          User user = new User(username, password, LanguageCERFLevel.A1);
          context.setCurrentUser(user);
          router.switchState(AppState.MainMenu);
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
