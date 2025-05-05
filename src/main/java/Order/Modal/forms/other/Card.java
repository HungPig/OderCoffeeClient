package Order.Modal.forms.other;

import Order.Modal.model.ModelEmployee;
import Order.Modal.model.ModelOrder;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.extras.AvatarIcon;

import javax.swing.*;
import java.util.function.Consumer;

public class Card extends JPanel {

    private final ModelOrder order;

    public Card(ModelOrder order) {
        this.order = order;
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:30;" +
                "[light]background:darken($Panel.background,3%);" +
                "[dark]background:lighten($Panel.background,3%);");

        setLayout(new MigLayout("", "", "fill"));
        // create panel header
        panelHeader = createHeader();

        // create panel body
        panelBody = createBody();

        add(panelHeader);
        add(panelBody);
    }

    private void loadData()
    {

    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new MigLayout("fill,insets 0", "[fill]", "[top]"));
        header.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        JLabel label = new JLabel(new AvatarIcon(order.getTableImage(), 130, 130, 20));
        header.add(label);
        return header;
    }

    private JPanel createBody() {
        // Create body panel
        JPanel body = new JPanel(new MigLayout("wrap", "[grow,fill]", "[][]push[]"));
        body.putClientProperty(FlatClientProperties.STYLE, "background:null");

        // Add order details
        JLabel orderIdLabel = new JLabel("Mã đơn hàng: " + order.getId());
        orderIdLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +1;");
        body.add(orderIdLabel);

        JLabel orderStatusLabel = new JLabel("Trạng thái: " + order.getStatus());
        orderStatusLabel.putClientProperty(FlatClientProperties.STYLE, "font:plain;");
        body.add(orderStatusLabel);

        JLabel tableInfoLabel = new JLabel("Số bàn: " + order.getTableId() + " - Trạng thái Bàn: " + order.getTableStatus());
        tableInfoLabel.putClientProperty(FlatClientProperties.STYLE, "font:plain;");
        body.add(tableInfoLabel);
        JLabel totalAmountLabel = new JLabel("Tổng tiền: " + order.getTotal() + " đồng");
        totalAmountLabel.putClientProperty(FlatClientProperties.STYLE, "font:plain;");
        body.add(totalAmountLabel);

        // 🆕 Add time label here
        JLabel timeLabel = new JLabel("Thời gian: " + order.getCreatedAt());
        timeLabel.putClientProperty(FlatClientProperties.STYLE, "font:italic;");
        body.add(timeLabel);

        return body;
    }

    private JPanel panelHeader;
    private JPanel panelBody;
}
