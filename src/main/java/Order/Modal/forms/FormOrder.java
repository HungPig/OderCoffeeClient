package Order.Modal.forms;

import Order.Modal.forms.other.Card;
import Order.Modal.layout.ResponsiveLayout;
import Order.Modal.model.ModelOrder;
import Order.Modal.sample.SampleData;
import Order.Modal.System.Form;
import Order.Modal.utils.SystemForm;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SystemForm(name = "Order", description = "Order form display some details")
public class FormOrder extends Form {
    private JPanel panelCard;
    private JPanel detailPanel;
    private JTextField searchField;
    private List<ModelOrder> filteredOrders;
    private ModelOrder selectedOrder;
    private NumberFormat currencyFormatter;

    public FormOrder() {
        currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        filteredOrders = new ArrayList<>(SampleData.getSampleOrderData());
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));
        add(createHeader());
        add(createSeparator(), "growx");
        add(createMainContent(), "grow");
    }

    private Component createHeader() {
        JPanel panel = new JPanel(new MigLayout("fillx", "[][push][]", "[]"));

        // Tiêu đề
        JLabel title = new JLabel("Quản lý đơn hàng");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");

        // Mô tả
        JLabel description = new JLabel("Xem và quản lý các đơn hàng trong hệ thống");
        description.putClientProperty(FlatClientProperties.STYLE, "font: -1; foreground: lighten(@foreground, 30%)");


        JPanel titlePanel = new JPanel(new MigLayout("wrap", "[]", "[]0[]"));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(description);

        panel.add(titlePanel);

        return panel;
    }



    private Component createSeparator() {
        JSeparator separator = new JSeparator();
        separator.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@foreground, 70%)");
        return separator;
    }

    private Component createMainContent() {
        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.6);
        splitPane.setContinuousLayout(true);

        // Left component - Order cards
        JPanel leftPanel = new JPanel(new BorderLayout());
        ResponsiveLayout responsiveLayout = new ResponsiveLayout(ResponsiveLayout.JustifyContent.FIT_CONTENT, new Dimension(-1, -1), 15, 15);
        panelCard = new JPanel(responsiveLayout);
        panelCard.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background, 2%); border:10,10,10,10;");

        JScrollPane scrollPane = new JScrollPane(panelCard);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" +
                "thumbInsets:0,0,0,0;" +
                "width:5;");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" +
                "thumbInsets:0,0,0,0;" +
                "width:5;");

        leftPanel.add(scrollPane, BorderLayout.CENTER);

        // Right component - Order details
        detailPanel = createDetailPanel();

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(detailPanel);

        return splitPane;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 20", "[fill]", "[]10[]10[]10[]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background, 1%)");

        JLabel titleLabel = new JLabel("Chi tiết đơn hàng");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");

        JLabel noSelectionLabel = new JLabel("Chọn một đơn hàng để xem chi tiết");
        noSelectionLabel.putClientProperty(FlatClientProperties.STYLE, "foreground: lighten(@foreground, 30%)");
        noSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titleLabel);
        panel.add(new JSeparator(), "growx");
        panel.add(noSelectionLabel, "alignx center, gaptop 30");

        return panel;
    }

    private JPanel createOrderDetailPanel(ModelOrder order) {
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 20", "[fill]", "[]10[]10[]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "background: lighten(@background, 1%)");

        // Header
        JPanel headerPanel = new JPanel(new MigLayout("fillx", "[]push[]", "[]"));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Chi tiết đơn hàng #" + order.getId());
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");


        headerPanel.add(titleLabel);


        // Order info
        JPanel infoPanel = new JPanel(new MigLayout("wrap 2, fillx, insets 15", "[30%][70%]", "[]10[]10[]10[]"));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        infoPanel.setOpaque(false);

        addInfoRow(infoPanel, "Mã đơn hàng:", + order.getId());
        addInfoRow(infoPanel, "Bàn:", order.getTableId());
        addInfoRow(infoPanel, "Trạng thái:", getStatusLabel(order.getStatus()));
        addInfoRow(infoPanel, "Thời gian:", order.getUpdatedAt().toString());
        addInfoRow(infoPanel, "Tổng tiền:", formatCurrency(order.getTotal()));

        // Order items
        JPanel itemsPanel = new JPanel(new MigLayout("wrap, fillx, insets 15", "[fill]", "[]10[]"));
        itemsPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        itemsPanel.setOpaque(false);

        JLabel itemsTitle = new JLabel("Các món trong đơn");
        itemsTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +1");

        // Create table for items
        String[] columns = {"Món", "Số Lượng", "Đơn giá", "Thành tiền"};
        Object[][] data = {
                {"Cà Phê", 2, formatCurrency(35000), formatCurrency(170000)},
        };

        JTable itemsTable = new JTable(data, columns);
        itemsTable.setShowGrid(false);
        itemsTable.setIntercellSpacing(new Dimension(0, 8));
        itemsTable.setRowHeight(30);
        itemsTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "font:bold; background:lighten(@background, 5%)");

        JScrollPane tableScrollPane = new JScrollPane(itemsTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        itemsPanel.add(itemsTitle);
        itemsPanel.add(tableScrollPane);

        // Action buttons
        JPanel actionPanel = new JPanel(new MigLayout("fillx", "[grow][grow][grow]", "[]"));
        actionPanel.setOpaque(false);

        JButton editButton = new JButton("Chỉnh sửa");
        editButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, "roundRect");

        JButton completeButton = new JButton("Hoàn thành");
        completeButton.putClientProperty(FlatClientProperties.STYLE, "background: #4CAF50; foreground: #FFFFFF; borderWidth: 0");

        JButton cancelButton = new JButton("Hủy đơn");
        cancelButton.putClientProperty(FlatClientProperties.STYLE, "background: #F44336; foreground: #FFFFFF; borderWidth: 0");

        actionPanel.add(editButton, "growx");
        actionPanel.add(completeButton, "growx");
        actionPanel.add(cancelButton, "growx");

        // Add all components to main panel
        panel.add(headerPanel);
        panel.add(new JSeparator(), "growx");
        panel.add(infoPanel, "growx");
        panel.add(itemsPanel, "growx");
        panel.add(actionPanel, "growx");

        return panel;
    }

    private void addInfoRow(JPanel panel, String label, Object value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.putClientProperty(FlatClientProperties.STYLE, "foreground: lighten(@foreground, 30%)");

        JComponent valueComponent;
        if (value instanceof JComponent) {
            valueComponent = (JComponent) value;
        } else {
            valueComponent = new JLabel(value.toString());
            valueComponent.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        }

        panel.add(labelComponent);
        panel.add(valueComponent);
    }

    private JLabel getStatusLabel(String status) {
        JLabel label = new JLabel(getStatusText(status));
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(3, 8, 3, 8));
        label.putClientProperty(FlatClientProperties.STYLE, getStatusStyle(status));
        return label;
    }

    private String getStatusText(String status) {
        switch (status.toLowerCase()) {
            case "processing": return "Đang xử lý";
            case "completed": return "Hoàn thành";
            case "cancelled": return "Đã hủy";
            default: return status;
        }
    }

    private String getStatusStyle(String status) {
        switch (status.toLowerCase()) {
            case "processing": return "background: #2196F3; foreground: #FFFFFF; arc: 10";
            case "completed": return "background: #4CAF50; foreground: #FFFFFF; arc: 10";
            case "cancelled": return "background: #F44336; foreground: #FFFFFF; arc: 10";
            default: return "background: #9E9E9E; foreground: #FFFFFF; arc: 10";
        }
    }

    private String formatCurrency(double amount) {
        return currencyFormatter.format(amount).replace("₫", "") + " ₫";
    }

    @Override
    public void formInit() {
        refreshOrderCards();
    }

    private void refreshOrderCards() {
        panelCard.removeAll();

        for (ModelOrder order : filteredOrders) {
            Card card = new Card(order);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedOrder = order;
                    updateDetailPanel();
                }
            });
            panelCard.add(card);
        }

        panelCard.repaint();
        panelCard.revalidate();
    }

    private void updateDetailPanel() {
        if (selectedOrder != null) {
            Container parent = detailPanel.getParent();
            parent.remove(detailPanel);
            detailPanel = createOrderDetailPanel(selectedOrder);
            parent.add(detailPanel);
            parent.revalidate();
            parent.repaint();
        }
    }

}
