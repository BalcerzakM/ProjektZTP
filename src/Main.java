import app.AppContext;
import views.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppContext context = new AppContext();
            MainFrame frame = new MainFrame(context, "apka");
            frame.start();
        });
    }
}
