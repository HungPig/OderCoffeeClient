package Order.Modal.Auth;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class TestLoginForm extends JFrame {
    public TestLoginForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setLayout(new MigLayout("al center center"));
        add(new LoginForm());

    }
    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("Login.themes");
        FlatMacDarkLaf.setup();
        UIManager.put("defautlFont", new Font(FlatRobotoFont.FAMILY,Font.PLAIN,13));
        EventQueue.invokeLater(()->new TestLoginForm().setVisible(true));

    }

}
