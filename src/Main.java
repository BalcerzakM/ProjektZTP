import app.AppContext;
import app.AppRouter;
import views.MainFrame;

import javax.swing.*;
//import com.formdev.flatlaf.*;

public class Main {
    public static void main(String[] args) {
        final String appName = "ZTP Learning App";
        final String appVersion = "v0.5";
        //FlatDarculaLaf.setup();
        SwingUtilities.invokeLater(() -> {
            AppContext context = new AppContext();
            MainFrame frame = new MainFrame(appName + " " + appVersion, context);
            AppRouter router = new AppRouter(frame, context);
            router.start();
        });
    }
}
