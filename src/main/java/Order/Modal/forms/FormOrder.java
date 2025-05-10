package Order.Modal.forms;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.OrderAPI;
import Order.Modal.Entity.orders;
import Order.Modal.Response.orders.CreatedOrderResponse;
import Order.Modal.Response.orders.DeleteOrderResponse;
import Order.Modal.Response.orders.OrderResponse;
import Order.Modal.System.Form;
import Order.Modal.forms.other.OrderItem;
import Order.Modal.model.ModelProduct;
import Order.Modal.simple.OrderDetailsForm;
import Order.Modal.utils.ComboItem;
import Order.Modal.utils.SystemForm;
import Order.Modal.utils.table.CheckBoxTableHeaderRenderer;
import Order.Modal.utils.table.TableHeaderAlignment;
import Order.Modal.utils.table.TableProfileCellRenderer;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import raven.modal.option.Location;
import raven.modal.option.Option;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@SystemForm(name = "Order", description = "Order form display some details")
public class FormOrder extends Form {
    private JPanel panel;
    private JTextField txtFieldID;
    private JTextField JTextAmount;
    private JTextField JTextFieldItems;
    private JComboBox comboTable_ID;
    private JComboBox comboStatus;
    private JTable table;

    public FormOrder() {
        txtFieldID = new JTextField();
        JTextFieldItems = new JTextField();
        JTextAmount = new JTextField();
        comboTable_ID = new JComboBox<>();
        comboStatus = new JComboBox<>();
        initComboTable(comboTable_ID);
        initComboStatus(comboStatus);
        init();
        loadData();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));
        // create table model
        Object columns[] = new Object[]{"SELECT", "#", "Table Id", "Items", "Amount", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // allow cell editable at column 0 for checkbox
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // use boolean type at column 0 for checkbox
                if (columnIndex == 0)
                    return Boolean.class;
                // use profile class
                return super.getColumnClass(columnIndex);
            }
        };


        // create table
        table = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // table option
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(250);

        // disable reordering table column
        table.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer
        table.setDefaultRenderer(ModelProduct.class, new TableProfileCellRenderer(table));

        table.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 0) {
                int changedRow = e.getFirstRow();
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (i != changedRow && (Boolean) table.getValueAt(i, 0)) {
                        table.setValueAt(false, i, 0);
                    }
                }
            }
        });

        // apply checkbox custom to table header
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

        // alignment table header
        table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table) {
            @Override
            protected int getAlignment(int column) {
                if (column == 1) {
                    return SwingConstants.CENTER;
                }
                return SwingConstants.LEADING;
            }
        });

        // style
        putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:$Table.background;");
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "height:30;" +
                "hoverBackground:null;" +
                "pressedBackground:null;" +
                "separatorColor:$TableHeader.background;");
        table.putClientProperty(FlatClientProperties.STYLE, "" +
                "rowHeight:70;" +
                "showHorizontalLines:true;" +
                "intercellSpacing:0,1;" +
                "cellFocusColor:$TableHeader.hoverBackground;" +
                "selectionBackground:$TableHeader.hoverBackground;" +
                "selectionInactiveBackground:$TableHeader.hoverBackground;" +
                "selectionForeground:$Table.foreground;");
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:$ScrollBar.thumbArc;" +
                "trackInsets:3,3,3,3;" +
                "thumbInsets:3,3,3,3;" +
                "background:$Table.background;");

        // create title
        JLabel title = new JLabel("Orders");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2");
        add(title, "gapx 20");

        // create header
        add(createHeaderAction());
        add(scrollPane);
    }

    private void loadData() {
        try {
            OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
            orderAPI.getAllOrder().enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    if (response.isSuccessful()) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setRowCount(0);
                        assert response.body() != null;
                        for (orders order : response.body().getData()) {
                            model.addRow(new Object[]{
                                    false,
                                    order.getId(),
                                    order.getTable_id(),
                                    order.getItems(),
                                    order.getTotal_amount(),
                                    order.getStatus(),

                            });
                        }
                        table.setModel(model);
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable throwable) {
                    JOptionPane.showMessageDialog(FormOrder.this,
                            "Lỗi kết nối API: " + throwable.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Order/icons/search.svg", 0.4f));
        JButton cmdDetails = new JButton("Details");
        OrderItem orderItem = new OrderItem();
        cmdDetails.addActionListener(actionEvent -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Lấy orderId từ dòng được chọn
                String orderIdStr = (String) table.getValueAt(selectedRow, 1);

                // Truyền orderId vào OrderDetailsForm
                OrderDetailsForm detailsForm = new OrderDetailsForm(orderIdStr);

                ModalDialog.showModal(this,
                        new SimpleModalBorder(detailsForm, null, SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                            // Optional: xử lý khi modal đóng nếu cần
                        })
                );
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng trước.");
            }
        });
        panel.add(txtSearch);
        panel.add(cmdDetails);

        panel.putClientProperty(FlatClientProperties.STYLE, "background:null;");
        return panel;
    }


    public Component inputOrder() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));


        txtFieldID.setEditable(false);
        txtFieldID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID will be generated automatically");

        JLabel lbTitle = new JLabel("Please complete the following Order to add data!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:+2");
        panel.add(lbTitle, "gapy 5 0");
        panel.add(new JSeparator(), "height 2!,gapy 0 5");


        panel.add(createInputField("ID", txtFieldID));
        panel.add(createInputField("Items", JTextFieldItems));
        panel.add(createInputField("Amount", JTextAmount));
        panel.add(createInputField("Table", comboTable_ID));
        panel.add(createInputField("Status", comboStatus));
        return panel;
    }

    public Component orderDetails()
    {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
        return panel;
    }

    private void initComboTable(JComboBox<ComboItem> combo) {
        combo.removeAllItems();
        combo.addItem(new ComboItem(1, "Bàn 1"));
        combo.addItem(new ComboItem(2, "Bàn 2"));
        combo.addItem(new ComboItem(3, "Bàn 3"));
        combo.addItem(new ComboItem(4, "Bàn 4"));
        combo.addItem(new ComboItem(5, "Bàn 5"));
    }

    private void initComboStatus(JComboBox<String> combo) {
        combo.removeAllItems();
        combo.addItem("Đơn hàng mới");
        combo.addItem("Đã xử lý");
        combo.addItem("Đã huỷ");
    }

    private JPanel createInputField(String label, JComponent field) {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lb = new JLabel(label);
        lb.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        panel.add(lb);
        panel.add(field);
        return panel;
    }
}
