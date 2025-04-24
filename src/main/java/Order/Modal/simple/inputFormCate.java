package Order.Modal.simple;

import Order.Modal.forms.FormCategory;
import com.formdev.flatlaf.FlatClientProperties;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class inputFormCate extends JPanel {
    FormCategory category = new FormCategory();
    public inputFormCate() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));

        // Initialize text fields
        txtFieldID = new JTextField();
        txtCategoryName = new JTextField();

        // Make ID field non-editable
        txtFieldID.setEditable(false);

        // style
        txtCategoryName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g.Coffee");
        txtFieldID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID will be generated automatically");

        // add to panel
        createTitle("Please complete the following categories to add data !");

        // Add ID field
        add(new JLabel("ID"), "gapy 5 0");
        add(txtFieldID);

        // Add Category name field
        add(new JLabel("Category name"), "gapy 5 0");
        add(txtCategoryName);

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

    public String getCategoryName(String name) {
        return txtCategoryName.getText().trim();
    }

    public void setCategoryName(String name) {
        txtCategoryName.setText(name);
    }

    // New methods for ID field
    public JTextField getTextID() {
        return txtFieldID;
    }

    public void setID(String id) {
        txtFieldID.setText(id);
    }

    private JTextField txtFieldID;
    private JTextField txtCategoryName;
}