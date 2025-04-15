package Order.Modal.Auth;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm extends JPanel {
    public LoginForm() {
        init();
    }
    private void init()
    {
        setLayout(new MigLayout("wrap,gapy 3","[fill,300]"));

        add(new JLabel(new FlatSVGIcon("Order/Login/icon/logo.svg",1.5f)));
        JLabel lbTitle= new JLabel("Welcome back", JLabel.CENTER);
        lbTitle.putClientProperty(FlatClientProperties.STYLE,""+"font:bold +15;");
        add(lbTitle, "gapy 8 8");

        add(new JLabel("Sign in to  access  to your dashboard,",JLabel.CENTER ));
        add(new JLabel("Setting and projects.",JLabel.CENTER ));

        JLabel lbSeparator= new JLabel("Sign in with Email");
        lbSeparator.putClientProperty(FlatClientProperties.STYLE,""
                +"foreground:$Label.disabledForeground;"
                +"font: -1;");
        add(createJSeparator()," split 3, sizegroup g1");
        add(lbSeparator,"sizegroup g1");
        add(createJSeparator(),"sizegroup g1");

        JLabel lbEmail= new JLabel("Email");
        lbEmail.putClientProperty(FlatClientProperties.STYLE,""+
                "font: bold;");
        add(lbEmail, "gapy 10 5");

        JTextField txtEmail= new JTextField();
        txtEmail.putClientProperty(FlatClientProperties.STYLE, ""
                +"iconTextGap:10;");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Enter your email");
        txtEmail.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Order/Login/icon/email.svg",0.35f));
        add(txtEmail);

        JLabel lbPassword= new JLabel("Password");
        lbPassword.putClientProperty(FlatClientProperties.STYLE,""+
                "font: bold;");
        add(lbPassword, "grow 0, gapy 10 5");

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.STYLE, ""
                +"iconTextGap:10;"+
                "showRevealButton:true;");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Enter your password");
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Order/Login/icon/password.svg",0.35f));
        //txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("Order/Login/icon/eye.svg",0.6f));
        JLabel eyeIcon = new JLabel(new FlatSVGIcon("Order/Login/icon/eye.svg", 0.6f));
        txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, eyeIcon);

        eyeIcon.addMouseListener(new MouseAdapter() {
            private boolean passwordVisible = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                passwordVisible = !passwordVisible;
                if (passwordVisible) {
                    txtPassword.setEchoChar((char) 0); // Hiện mật khẩu
                } else {
                    txtPassword.setEchoChar('•'); // Ẩn mật khẩu
                }
            }
        });
        add(txtPassword);

        add(new JCheckBox("Remember me"), "gapy 10 10");

        JButton cmdSignIn= new JButton("Sign in", new FlatSVGIcon("Order/Login/icon/next.svg")){
            @Override
            public boolean isDefaultButton(){
                return true;
            }
        };
        cmdSignIn.putClientProperty((FlatClientProperties.STYLE), ""+
                "foreground:#FFFFFF;"+
                "iconTextGap:10;");
        cmdSignIn.setHorizontalTextPosition(JButton.LEADING);
        add(cmdSignIn,"gapy n 10");

    }
    private JSeparator createJSeparator()
    {
        JSeparator separator= new JSeparator();
        separator.putClientProperty(FlatClientProperties.STYLE, ""+
                "stripeIndent:8;");
        return separator;
    }
    private JButton createNoBorderButton(String text)
    {
        JButton button= new JButton(text);
        button.putClientProperty(FlatClientProperties.STYLE, ""+
                "foreground:$Compoment.accentColor;"+
                "margin:1,5,1,5"+
                "borderWidth:0;"+
                "focusWidth:0;"+
                "innerFocusWidth:0;"+
                "background:null");
        return button;
    }
}
