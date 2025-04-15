/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Order.Login.icon;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.AnimatedIcon;
import java.awt.Component;
import java.awt.Graphics;
import java.lang.annotation.Annotation;
import javax.swing.Icon;

/**
 *
 * @author admin
 */
public class PasswordRevealIcon implements AnimatedIcon {

    private final Icon icon;

    public PasswordRevealIcon() {
        this(new FlatSVGIcon("Order/Login/icon/eye.svg"));
    }

    public PasswordRevealIcon(Icon icon) {
        this.icon = icon;
    }

    @Override
    public void paintIconAnimated(Component cmpnt, Graphics grphcs, int i, int i1, float f) {
        icon.paintIcon(cmpnt, grphcs, i, i);
    }

    @Override
    public float getValue(Component cmpnt) {
        return 0;
    }

    @Override
    public int getIconWidth() {
        return icon.getIconWidth();

    }

    @Override
    public int getIconHeight() {
        return icon.getIconHeight();
    }

}
