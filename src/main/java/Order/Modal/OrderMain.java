package Order.Modal;


import Order.Modal.Auth.LoginForm;
import Order.Modal.System.FormManager;
import Order.Modal.untils.DemoPreferences;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.util.FontUtils;
import Order.Modal.Menu.MyDrawerBuilder;
//import Order.Modal.Ultis.DemoPreference
import raven.modal.Drawer;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class OrderMain extends javax.swing.JFrame {
    public static final String DEMO_VERSION = "1.1.1";
    public OrderMain() {
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        Drawer.installDrawer(this, new MyDrawerBuilder());
        FormManager.install(this);
        setSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        DemoPreferences.init();
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.modal.demo.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        DemoPreferences.setupLaf();

        EventQueue.invokeLater(() -> {
            // Tạo một JFrame để chứa LoginForm
            JFrame loginFrame = new JFrame("Đăng nhập");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(400, 500); // hoặc điều chỉnh theo kích thước bạn muốn
            loginFrame.setLocationRelativeTo(null); // căn giữa

            LoginForm loginForm = new LoginForm();
            loginFrame.setContentPane(loginForm);
            loginFrame.setVisible(true);
        });
    }
}