import app.AppContext;
import views.MainFrame;

import javax.swing.*;
import com.formdev.flatlaf.*;

public class Main {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        SwingUtilities.invokeLater(() -> {
            AppContext context = new AppContext();
            MainFrame frame = new MainFrame(context, "apka");
            frame.start();
        });
    }
}
