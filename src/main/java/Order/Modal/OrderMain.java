package Order.Modal;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import raven.modal.Drawer;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class OrderMain extends javax.swing.JFrame {
    public static final String DEMO_VERSION = "2.4.1-SNAPSHOT";
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
        EventQueue.invokeLater(() -> new Demo().setVisible(true));
    }

}