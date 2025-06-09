package Order.Modal.simple;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.OrderAPI;
import Order.Modal.Response.orders.OrderResponse;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SimpleInputForms extends JPanel {
    private JPanel productListPanel;
    public SimpleInputForms() {
        init();
    }




    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));

        JTextArea txtNotes = new JTextArea();
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
        add(new JLabel("ID"));
        add(new JLabel("Trạng thái"));

        add(new JSeparator(), "grow");
        JLabel labelOrder = new JLabel("Đơn Hàng Của Bạn");
        add(labelOrder, "gapy15");
        labelOrder.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+2");
        JPanel productListPanel = new JPanel(new MigLayout("fillx, wrap", "[fill]", ""));
        productListPanel.setOpaque(false);

        addProductItem(productListPanel, "Cà phê sữa", 29000, 1, 49);
        addProductItem(productListPanel, "Trà đào", 35000, 2, 52);
        add(productListPanel, "growx");
        // Quantity
//hihi
        // Subtotal
        add(new JSeparator(), "grow");
        add(new JLabel("Mã sản phẩm:"));
        add(new JLabel("Số lượng:"));
        add(new JLabel("Tổng tiền:"));;
        add(new JSeparator(), "grow");
        // Notes
        add(new JLabel("Ghi chú:"));
        txtNotes = new JTextArea();
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        JScrollPane scrollNotes = new JScrollPane(txtNotes);
        add(scrollNotes, "height 100");
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
    }


    private void addProductItem(JPanel parent, String name, int price, int qty, int code) {
        int total = price * qty;
        JLabel nameLabel = new JLabel(name);
        JLabel codeLabel = new JLabel("Mã: " + code);
        JLabel priceQtyLabel = new JLabel(price + " × " + qty);
        JLabel totalLabel = new JLabel(total + " VND");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JPanel priceRow = new JPanel(new MigLayout("insets 0, fillx", "[left][right]", ""));
        priceRow.setOpaque(false);
        priceRow.add(priceQtyLabel);
        priceRow.add(totalLabel);
        parent.add(nameLabel, "wrap");
        parent.add(codeLabel, "wrap");
        parent.add(priceRow, "wrap, gapbottom 10");
    }

    private void createTitle(String title) {
        JLabel lb = new JLabel(title);
        lb.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:+2");
        add(lb, "gapy 5 0");
        add(new JSeparator(), "height 2!,gapy 0 0");
    }

}