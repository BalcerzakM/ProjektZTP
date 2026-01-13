import app.AppContext;
import app.AppState;
import controllers.Controller;
import views.MainFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
//import com.formdev.flatlaf.*;

public class Main {
    public static void main(String[] args) {
        //FlatDarculaLaf.setup();
        SwingUtilities.invokeLater(() -> {
            AppContext context = new AppContext();
            MainFrame frame = new MainFrame(context, "apka");
            frame.start();
        });
    }
}
