package Order.Modal.simple;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SimpleInputForms extends JPanel {

    public SimpleInputForms() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
        JTextField txtOrderId = new JTextField();
        JTextField txtProductId = new JTextField();
        JTextField txtQuantity = new JTextField();
        JTextField txtSubtotal = new JTextField();
        JComboBox comboStatus = new JComboBox<>();
        JTextArea txtNotes = new JTextArea();
        JTextField txtStatus = new JTextField();

        JTextArea txtAddress = new JTextArea();
        txtAddress.setWrapStyleWord(true);
        txtAddress.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(txtAddress);
        JLabel lbTitle = new JLabel("Chi tiết đơn hàng #1001");
        JLabel lbTime = new JLabel("04/05/2025 23:12:34");

        add(lbTitle, "split 2");
        add(lbTime, "gapleft push");

        add(new JSeparator(), "grow");

        // ID và Trạng thái
        add(new JLabel("ID"), "split 2");
        add(new JLabel("Trạng thái"), "gapleft push, wrap");

        txtOrderId = new JTextField();
        txtStatus = new JTextField();

        add(txtOrderId, "split 2");
        add(txtStatus, "gapleft push");

        add(new JSeparator(), "grow");

        // Product ID
        add(new JLabel("Mã sản phẩm:"));
        txtProductId = new JTextField();
        add(txtProductId);

        // Quantity
        add(new JLabel("Số lượng:"));
        txtQuantity = new JTextField();
        add(txtQuantity);

        // Subtotal
        add(new JLabel("Tổng tiền:"));
        txtSubtotal = new JTextField();
        add(txtSubtotal);

        add(new JSeparator(), "grow");

        // Notes
        add(new JLabel("Ghi chú:"));
        txtNotes = new JTextArea();
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        JScrollPane scrollNotes = new JScrollPane(txtNotes);
        add(scrollNotes, "height 100");

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Xoá"));
        buttonPanel.add(new JButton("Đóng"));
        buttonPanel.add(new JButton("Chỉnh sửa"));
        add(buttonPanel, "gapy 10");

        txtAddress.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isControlDown() && e.getKeyChar() == 10) {
                    ModalBorderAction modalBorderAction = ModalBorderAction.getModalBorderAction(SimpleInputForms.this);
                    if (modalBorderAction != null) {
                        modalBorderAction.doAction(SimpleModalBorder.YES_OPTION);
                    }
                }
            }
        });
        initComboItem(comboStatus);
    }

    private void createTitle(String title) {
        JLabel lb = new JLabel(title);
        lb.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+2");
        add(lb, "gapy 5 0");
        add(new JSeparator(), "height 2!,gapy 0 0");
    }

    private void initComboItem(JComboBox combo) {
        combo.addItem("United States");
        combo.addItem("Canada");
        combo.addItem("Brazil");
        combo.addItem("United Kingdom");
        combo.addItem("France");
        combo.addItem("Germany");
        combo.addItem("Australia");
        combo.addItem("Japan");
        combo.addItem("China");
        combo.addItem("India");
    }

    public void formOpen() {
        txtFirstName.grabFocus();
    }

    private JTextField txtFirstName;
}