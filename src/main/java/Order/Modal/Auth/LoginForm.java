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
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton cmdSignIn;
    private JCheckBox chRememberMe;

    public LoginForm() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel card = new JPanel(new MigLayout(
                "wrap, insets 35 35 25 35, align center center",
                "[fill, 300!]",
                "[]10[]10[]10[]10[]"
        )) {
            @Override
            public void updateUI() {
                super.updateUI();
                applyShadowBorder(this);
            }
        };
        card.setPreferredSize(new Dimension(500, 510));
        card.setOpaque(false);
        card.setBackground(null);
        applyShadowBorder(card);

        // Logo
        card.add(new JLabel(new FlatSVGIcon("Order/icons/logo.svg", 1.5f)), "wrap, gapbottom 15, align center");

        // Title
        JLabel lbTitle = new JLabel("Welcome back!", JLabel.CENTER);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +15;");
        card.add(lbTitle, "wrap, gapbottom 5");

        card.add(new JLabel("Please sign in to access your dashboard", JLabel.CENTER), "wrap");
        card.add(new JLabel("and projects.", JLabel.CENTER), "wrap, gapbottom 15");

        // Email
        card.add(new JLabel("Email"), "wrap, gapbottom 5");
        txtEmail = new JTextField();
        txtEmail.putClientProperty(FlatClientProperties.STYLE, "iconTextGap:10; margin:4,10,4,10; arc:12;");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your email or username");
        txtEmail.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("Order/icons/email.svg", 0.35f));
        card.add(txtEmail, "growx, wrap");

        // Password
        card.add(new JLabel("Password"), "wrap, gapbottom 5");
        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "iconTextGap:10; margin:4,10,4,10; arc:12; showRevealButton:true;");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                new FlatSVGIcon("Order/icons/password.svg", 0.35f));
        card.add(txtPassword, "growx, wrap");

        // Remember me
        chRememberMe = new JCheckBox("Remember me");
        card.add(chRememberMe, "wrap, gapbottom 20");

        // Sign in
        cmdSignIn = new JButton("Sign in", new FlatSVGIcon("Order/icons/next.svg")) {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdSignIn.putClientProperty(FlatClientProperties.STYLE, "margin:4,10,4,10; arc:12;");
        cmdSignIn.setHorizontalTextPosition(SwingConstants.LEADING);
        cmdSignIn.setPreferredSize(new Dimension(0, 40));
        card.add(cmdSignIn, "growx, wrap, gapbottom 20");

        // Info
        card.add(createInfoPanel(), "growx");

        cmdSignIn.addActionListener(e -> FormManager.login());

        // Wrap with center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(card);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createInfoPanel() {
        JPanel panelInfo = new JPanel(new MigLayout("wrap, al center", "[center]"));
        panelInfo.setOpaque(false);
        panelInfo.add(new JLabel("Don't remember your account details?"));
        panelInfo.add(new JLabel("Contact us at"), "split 2");

        LabelButton lbLink = new LabelButton("help@info.com");
        panelInfo.add(lbLink);

        lbLink.addOnClick(e -> {
            // handle link click
        });

        return panelInfo;
    }

    private void applyShadowBorder(JPanel panel) {
        if (panel != null) {
            panel.setBorder(new DropShadowBorder(new Insets(3, 5, 6, 5), 1, 20));
        }
    }
}