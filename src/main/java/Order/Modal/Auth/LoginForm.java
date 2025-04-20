package Order.Modal.Auth;

import Order.Modal.OrderMain;
import Order.Modal.System.Form;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm extends Form {
    // Biến toàn cục để dễ truy xuất trong init() và listener
    private JTextField     txtEmail;
    private JPasswordField txtPassword;
    private JButton        cmdSignIn;

    public LoginForm() {
        init();
    }

    private void init() {
        // 1) Root panel: BorderLayout để có thể căn giữa panel con
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // nền xám nhạt phía ngoài card

        // 2) Tạo "card" chứa form, dùng MigLayout để dễ căn chỉnh
        JPanel card = new JPanel(new MigLayout(
                "wrap, insets 20, align center center", // layout constraints
                "[300!]",                               // mỗi cột cố định 300px
                "[]10[]10[]10[]10[]"                    // gap 10px giữa các hàng nhóm
        ));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // 3) Nội dung bên trong card
        // Logo
        card.add(new JLabel(new FlatSVGIcon("Order/icons/logo.svg", 1.5f)),
                "wrap, gapbottom 15");

        // Tiêu đề
        JLabel lbTitle = new JLabel("Welcome back", JLabel.CENTER);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +15;");
        card.add(lbTitle, "wrap, gapbottom 5");

        // Subtitle
        card.add(new JLabel("Sign in to access to your dashboard,", JLabel.CENTER),
                "wrap");
        card.add(new JLabel("Setting and projects.", JLabel.CENTER),
                "wrap, gapbottom 15");

        // Separator với text
        JLabel lbSeparator = new JLabel("Sign in with Email", JLabel.CENTER);
        lbSeparator.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground;font:-1;");
        card.add(createJSeparator(), "growx, split 3");
        card.add(lbSeparator,            "growx");
        card.add(createJSeparator(),     "growx, wrap, gapbottom 20");

        // Email label + field
        card.add(new JLabel("Email"), "wrap, gapbottom 5");
        txtEmail = new JTextField();
        txtEmail.putClientProperty(FlatClientProperties.STYLE,
                "iconTextGap:10;");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,
                "Enter your email");
        txtEmail.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("Order/icons/email.svg", 0.35f));
        card.add(txtEmail, "growx, wrap");

        // Password label + field
        card.add(new JLabel("Password"), "wrap, gapbottom 5");
        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.STYLE,
                "iconTextGap:10;showRevealButton:true;");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,
                "Enter your password");
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("Order/icons/password.svg", 0.35f));
        // Custom reveal-icon
        JLabel eyeIcon = new JLabel(new FlatSVGIcon("Order/icons/eye.svg", 0.6f));
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON,
                eyeIcon);
        eyeIcon.addMouseListener(new MouseAdapter() {
            private boolean visible = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                visible = !visible;
                txtPassword.setEchoChar(visible ? (char)0 : '•');
            }
        });
        card.add(txtPassword, "growx, wrap");

        // Remember me
        card.add(new JCheckBox("Remember me"), "wrap, gapbottom 20");

        // Sign in button
        cmdSignIn = new JButton("Sign in", new FlatSVGIcon("Order/icons/next.svg")) {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdSignIn.putClientProperty(FlatClientProperties.STYLE,
                "foreground:#FFFFFF;iconTextGap:10;");
        cmdSignIn.setHorizontalTextPosition(SwingConstants.LEADING);
        cmdSignIn.setPreferredSize(new Dimension(0, 40)); // cao 40px, rộng fill
        card.add(cmdSignIn, "growx");

        // 4) Xử lý khi nhấn Sign in
        cmdSignIn.addActionListener(e -> {
            Window win = SwingUtilities.getWindowAncestor(LoginForm.this);
            win.dispose();             // đóng login
            new OrderMain().setVisible(true); // mở chính
        });

        // 5) Đưa card vào chính giữa LoginForm
        add(card, BorderLayout.CENTER);
    }

    private JSeparator createJSeparator() {
        JSeparator sep = new JSeparator();
        sep.putClientProperty(FlatClientProperties.STYLE, "stripeIndent:8;");
        return sep;
    }
}
