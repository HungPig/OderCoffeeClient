package Order.Modal.Auth;

import Order.Modal.System.Form;
import Order.Modal.System.FormManager;
import Order.Modal.component.LabelButton;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.DropShadowBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm extends Form {
    // Global variables for easy access in init() and listeners
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton cmdSignIn;
    private JCheckBox chRememberMe;

    public LoginForm() {
        init();
    }

    private void init() {
        // Root panel with BorderLayout for better centering
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Create card panel with shadow border
        JPanel card = new JPanel(new MigLayout(
                "wrap, insets 35 35 25 35, align center center", // layout constraints
                "[fill, 300!]",                                 // fixed column width 300px
                "[]10[]10[]10[]10[]"                            // 10px gap between row groups
        )) {

            @Override
            public void updateUI() {
                super.updateUI();
//                applyShadowBorder(this);
            }
        };
        card.setBackground(Color.white);
        applyShadowBorder(card);

        // Card content
        // Logo
        card.add(new JLabel(new FlatSVGIcon("Order/icons/logo.svg", 1.5f)),
                "wrap, gapbottom 15, align center");

        // Title
        JLabel lbTitle = new JLabel("Welcome back!", JLabel.CENTER);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +15;");
        card.add(lbTitle, "wrap, gapbottom 5");

        // Subtitle
        card.add(new JLabel("Please sign in to access your dashboard", JLabel.CENTER),
                "wrap");
        card.add(new JLabel("and projects.", JLabel.CENTER),
                "wrap, gapbottom 15");

        // Email label + field
        card.add(new JLabel("Email"), "wrap, gapbottom 5");
        txtEmail = new JTextField();
        txtEmail.putClientProperty(FlatClientProperties.STYLE,
                "iconTextGap:10; margin:4,10,4,10; arc:12;");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,
                "Enter your email or username");
        txtEmail.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("Order/icons/email.svg", 0.35f));
        card.add(txtEmail, "growx, wrap");

        // Password label + field
        card.add(new JLabel("Password"), "wrap, gapbottom 5");
        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.STYLE,
                "iconTextGap:10; margin:4,10,4,10; arc:12; showRevealButton:true;");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,
                "Enter your password");
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("Order/icons/password.svg", 0.35f));
        card.add(txtPassword, "growx, wrap");

        // Remember me
        chRememberMe = new JCheckBox("Remember me");
        card.add(chRememberMe, "wrap, gapbottom 20");

        // Sign in button
        cmdSignIn = new JButton("Sign in", new FlatSVGIcon("Order/icons/next.svg")) {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdSignIn.putClientProperty(FlatClientProperties.STYLE, "" +
                "margin:4,10,4,10;" +
                "arc:12;");
        cmdSignIn.setHorizontalTextPosition(SwingConstants.LEADING);
        cmdSignIn.setPreferredSize(new Dimension(0, 40)); // Height 40px, width fill
        card.add(cmdSignIn, "growx, wrap, gapbottom 20");

        // Add help info panel
        card.add(createInfoPanel(), "growx");

        // Handle sign in button click
        cmdSignIn.addActionListener(e -> {
            FormManager.login();
        });

        // Add card to center of LoginForm
        add(card, BorderLayout.CENTER);

        // Optional: card style for dark mode support
        card.putClientProperty(FlatClientProperties.STYLE, "" +
                "[dark]background:tint($Panel.background,1%);");
    }

    private JPanel createInfoPanel() {
        JPanel panelInfo = new JPanel(new MigLayout("wrap, al center", "[center]"));
        panelInfo.putClientProperty(FlatClientProperties.STYLE, "background:null;");

        panelInfo.add(new JLabel("Don't remember your account details?"));
        panelInfo.add(new JLabel("Contact us at"), "split 2");

        LabelButton lbLink = new LabelButton("help@info.com");
        panelInfo.add(lbLink);

        // Event
        lbLink.addOnClick(e -> {
            // Handle help link click
        });

        return panelInfo;
    }

    private void applyShadowBorder(JPanel panel) {
        if (panel != null) {
            panel.setBorder(new DropShadowBorder(new Insets(5, 8, 12, 8), 1, 25));
        }
    }
}