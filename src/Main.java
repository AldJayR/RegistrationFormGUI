
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import controller.RegistrationController;
import model.MySQLUserDAO;
import model.UserDao;
import view.RegistrationForm;
import javax.swing.*;
import java.awt.Color;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/user_registration";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {

        setupLookAndFeel();

        java.awt.EventQueue.invokeLater(() -> {
            RegistrationForm view = new RegistrationForm();
            UserDao userDao = new MySQLUserDAO(DB_URL, DB_USER, DB_PASSWORD);
            RegistrationController controller = new RegistrationController(userDao, view);
            view.setVisible(true);
        });
    }

    private static void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Use FlatLightLaf

            UIManager.put("Button.arc", 10); // Rounded buttons
            UIManager.put("Component.focusWidth", 2);
            UIManager.put("ScrollBar.thumbArc", 5);
            UIManager.put("TextComponent.arc", 10); // Rounded text fields
            UIManager.put("PasswordField.showRevealButton", true);
            UIManager.put("Button.focusedBackground", Color.BLUE);
            UIManager.put("TextField.background", new Color(248, 250, 252));
            UIManager.put("TextField.foreground", new Color(17, 24, 39));
            UIManager.put("TextField.selectionBackground", new Color(34, 211, 238));
            UIManager.put("TextField.selectionForeground", Color.BLACK);

            FlatLaf.updateUI(); // Apply changes immediately

        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
    }
}
