package Order.Modal.simple;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class inputFormCate extends JPanel {
    public inputFormCate() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
        txtCategoryName = new JTextField();
        // style
        txtCategoryName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g.Coffee");
        // add to panel
        createTitle("Please complete the following categories to add data !");

        add(new JLabel("Category name"), "gapy 5 0");
        add(txtCategoryName, "split 2");
    }

    private void createTitle(String title) {
        JLabel lb = new JLabel(title);
        lb.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+2");
        add(lb, "gapy 5 0");
        add(new JSeparator(), "height 2!,gapy 0 0");
    }



    public void formOpen() {
        txtCategoryName.grabFocus();
    }

    public String getCategoryName() {
        return txtCategoryName.getText().trim();
    }

    private JTextField txtCategoryName;
}
