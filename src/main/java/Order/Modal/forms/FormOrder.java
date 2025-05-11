package Order.Modal.forms;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.OrderAPI;
import Order.Modal.Api.TableAPI;
import Order.Modal.Entity.orders;
import Order.Modal.Entity.tables;
import Order.Modal.Response.orders.CreatedOrderResponse;
import Order.Modal.Response.orders.DeleteOrderResponse;
import Order.Modal.Response.orders.OrderResponse;
import Order.Modal.Response.tables.TableResponse;
import Order.Modal.System.Form;
import Order.Modal.utils.SystemForm;
import Order.Modal.utils.table.CheckBoxTableHeaderRenderer;
import Order.Modal.utils.table.TableHeaderAlignment;
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
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

@SystemForm(name = "Order", description = "table is a user interface component", tags = {"list"})
public class FormOrder extends Form {
    private List<orders> allOrders = new ArrayList<>();
    private JTextField txtFieldOrderID;
    private JComboBox<String> comboOrderStatus;
    private JTextField txtTotalOrderAmount;
    private JComboBox<tables> comboTableOrder;
    JTable tableOrder;
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public FormOrder() {
        txtFieldOrderID = new JTextField();
        txtFieldOrderID.setEditable(false);
        txtTotalOrderAmount = new JTextField();
        comboOrderStatus = new JComboBox<>(new String[]{"Đơn Hàng Mới", "Đã Xử Lý", "Đã hủy"});
        comboTableOrder = new JComboBox<tables>();
        loadDataOrder();
        init();
        loadTables();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap", "[fill]", "[][fill,grow]"));
        add(createInfo("Manager Order", "A table is a user interface component that displays a collection of records in a structured, tabular format. It allows users to view, sort, and manage data or other resources.", 1));
        add(createTab(), "gapx 7 7");
    }

    private JPanel createInfo(String title, String description, int level) {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap", "[fill]"));
        JLabel lbTitle = new JLabel(title);
        JTextPane text = new JTextPane();
        text.setText(description);
        text.setEditable(false);
        text.setBorder(BorderFactory.createEmptyBorder());
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +" + (4 - level));
        panel.add(lbTitle);
        panel.add(text, "width 500");
        return panel;
    }

    private Component createTab() {
        JTabbedPane tabb = new JTabbedPane();
        tabb.putClientProperty(FlatClientProperties.STYLE, "" +
                "tabType:card");
        tabb.addTab("Order", createBorder(createOrderTable()));
        tabb.addTab("OrderDetails", createBorder(createOrderDetails()));
        return tabb;
    }

    private Component createBorder(Component component) {
        JPanel panel = new JPanel(new MigLayout("fill,insets 7 0 7 0", "[fill]", "[fill]"));
        panel.add(component);
        return panel;
    }


    private Component createOrderTable() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));

        // create table model
        Object columns[] = new Object[]{"SELECT", "#", "TABLE", "STATUS", "AMOUNT", "CREATE", "UPDATE"};
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
        tableOrder = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(tableOrder);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // table option
        tableOrder.getColumnModel().getColumn(0).setMaxWidth(50);
        tableOrder.getColumnModel().getColumn(1).setMaxWidth(50);
        tableOrder.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableOrder.getColumnModel().getColumn(5).setPreferredWidth(100);
        tableOrder.getColumnModel().getColumn(6).setPreferredWidth(250);

        // disable reordering table column
        tableOrder.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer

        // apply checkbox custom to table header
        tableOrder.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(tableOrder, 0));

        // alignment table header
        tableOrder.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(tableOrder) {
            @Override
            protected int getAlignment(int column) {
                if (column == 1) {
                    return SwingConstants.CENTER;
                }
                return SwingConstants.LEADING;
            }
        });

        // style
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:$Table.background;");
        tableOrder.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "" +
                "height:30;" +
                "hoverBackground:null;" +
                "pressedBackground:null;" +
                "separatorColor:$TableHeader.background;");
        tableOrder.putClientProperty(FlatClientProperties.STYLE, "" +
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
        JLabel title = new JLabel("Order table");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2");
        panel.add(title, "gapx 20");

        // create header
        panel.add(createHeaderOrderAction());
        panel.add(scrollPane);

        // sample data

        return panel;
    }

    private Component createOrderDetails() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));

        // create table model
        Object columns[] = new Object[]{"SELECT", "#", "NAME", "DATE", "SALARY", "POSITION", "DESCRIPTION"};
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
        JTable table = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // table option
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(250);

        // disable reordering table column
        table.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer

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
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
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
        JLabel title = new JLabel("OrderDetails table");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2");
        panel.add(title, "gapx 20");

        // create header
//        panel.add(createHeaderAction());
        panel.add(scrollPane);

        // sample data

        return panel;
    }


    private Component createHeaderOrderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Order/icons/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Create");
        JButton cmdEdit = new JButton("Edit");
        JButton cmdDelete = new JButton("Delete");
        JButton cmdProceed = new JButton("Proceed");

        cmdCreate.addActionListener(e -> createOrder());
        cmdEdit.addActionListener(e -> editOrder());
        cmdDelete.addActionListener(e -> deleteOrder());
        cmdProceed.addActionListener(e -> processOrder());
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                filterOrders();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterOrders();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterOrders();
            }

            private void filterOrders() {
                String keyword = txtSearch.getText().trim().toLowerCase();
                List<orders> filtered = new ArrayList<>();
                for (orders order : allOrders) {
                    if (String.valueOf(order.getId()).contains(keyword)
                            || String.valueOf(order.getTable_id()).contains(keyword)
                            || order.getStatus().toLowerCase().contains(keyword)) {
                        filtered.add(order);
                    }
                }
                displayOrders(filtered);
            }

        });
        panel.add(txtSearch);
        panel.add(cmdCreate);
        panel.add(cmdEdit);
        panel.add(cmdDelete);
        panel.add(cmdProceed);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }

    //Order
    private void displayOrders(List<orders> list) {
        DefaultTableModel model = (DefaultTableModel) tableOrder.getModel();
        model.setRowCount(0);
        for (orders order : list) {
            model.addRow(new Object[]{
                    false,
                    order.getId(),
                    "Bàn " + order.getTable_id(),
                    order.getStatus(),
                    currencyFormatter.format(order.getTotal_amount()),
                    order.getCreated_at(),
                    order.getUpdated_at(),
            });
        }
    }

    public Component inputOrder() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 20 10 20", "[fill]", ""));

        // Khởi tạo các field nếu chưa có
        if (txtFieldOrderID == null) txtFieldOrderID = new JTextField();
        if (comboOrderStatus == null)
            comboOrderStatus = new JComboBox<>(new String[]{"Đơn Hàng Mới", "Đang xử lý", "Đã hủy"});
        if (txtTotalOrderAmount == null) txtTotalOrderAmount = new JTextField();

        // Đặt placeholder và cấu hình
        txtFieldOrderID.setEditable(false);
        txtFieldOrderID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID will be generated automatically");
        txtTotalOrderAmount.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. 150000");

        // Tiêu đề
        JLabel title = new JLabel("Please complete the order information");
        title.putClientProperty(FlatClientProperties.STYLE, "font:+2");
        panel.add(title, "gapy 5 0");
        panel.add(new JSeparator(), "height 2!,gapy 0 10");

        // Thêm các trường nhập
        panel.add(new JLabel("Order ID"));
        panel.add(txtFieldOrderID);

        panel.add(new JLabel("Table ID"));
        panel.add(comboTableOrder);

        panel.add(new JLabel("Status"));
        panel.add(comboOrderStatus);

        panel.add(new JLabel("Total Amount (VNĐ)"));
        panel.add(txtTotalOrderAmount);

        return panel;
    }

    private void loadTables() {
        comboTableOrder = new JComboBox<>();
        TableAPI tableAPI = APIClient.getClient().create(TableAPI.class);

        tableAPI.getAllTabel().enqueue(new Callback<TableResponse>() {
            @Override
            public void onResponse(Call<TableResponse> call, Response<TableResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (tables table : response.body().getData()) {
                        comboTableOrder.addItem(table);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi dữ liệu bàn!");
                }
            }

            @Override
            public void onFailure(Call<TableResponse> call, Throwable t) {
                JOptionPane.showMessageDialog(null, "Lỗi kết nối API bàn: " + t.getMessage());
            }
        });
    }

    public void loadDataOrder() {
        try {
            OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
            orderAPI.getAllOrder().enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        allOrders = response.body().getData();
                        displayOrders(allOrders);
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

    public void createOrder() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-1, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        ModalDialog.showModal(this, new SimpleModalBorder(
                inputOrder(), "Create Order", SimpleModalBorder.YES_NO_OPTION,
                (controller, action) -> {
                    if (action == SimpleModalBorder.YES_NO_OPTION) {
                        tables selectedTable = (tables) comboTableOrder.getSelectedItem();
                        String orderStatus = (String) comboOrderStatus.getSelectedItem();
                        orders order = new orders();
                        order.setTable_id(selectedTable.getId());
                        order.setStatus(orderStatus);
                        order.setTotal_amount(Integer.valueOf(txtTotalOrderAmount.getText()));
                        OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
                        orderAPI.addOrder(order).enqueue(new Callback<CreatedOrderResponse>() {
                            @Override
                            public void onResponse(Call<CreatedOrderResponse> call, Response<CreatedOrderResponse> response) {
                                if (response.isSuccessful()) {
                                    JOptionPane.showMessageDialog(null, "Order created successfully");
                                    loadDataOrder();
                                }
                            }

                            @Override
                            public void onFailure(Call<CreatedOrderResponse> call, Throwable throwable) {
                                JOptionPane.showMessageDialog(null, "Error: " + throwable.getMessage());
                            }
                        });
                    }
                }), option);
    }

    public void editOrder() {
        int selectedRow = -1;
        for (int i = 0; i < tableOrder.getRowCount(); i++) {
            Boolean isChecked = (Boolean) tableOrder.getValueAt(i, 0);
            if (isChecked != null && isChecked) {
                selectedRow = i;
                break;
            }
        }
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a category to edit.");
            return;
        }
        String orderId = tableOrder.getValueAt(selectedRow, 1).toString();
        OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
        orderAPI.getOrderId(orderId).enqueue(new Callback<CreatedOrderResponse>() {
            @Override
            public void onResponse(Call<CreatedOrderResponse> call, Response<CreatedOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders order = response.body().getData();
                    //
                    txtFieldOrderID.setText(orderId);
                    txtTotalOrderAmount.setText(String.valueOf(order.getTotal_amount()));
                    comboOrderStatus.setSelectedItem(order.getStatus());
                    for (int i = 0; i < comboTableOrder.getItemCount(); i++) {
                        tables t = comboTableOrder.getItemAt(i);
                        if (t.getId() == order.getTable_id()) {
                            comboTableOrder.setSelectedIndex(i);
                            break;
                        }
                    }
                    String currentStatus = order.getStatus();
                    for (int i = 0; i < comboOrderStatus.getItemCount(); i++) {
                        if (comboOrderStatus.getItemAt(i).equals(currentStatus)) {
                            comboOrderStatus.setSelectedIndex(i);
                            break;
                        }
                    }
                    Option option = ModalDialog.createOption();
                    option.getLayoutOption().setSize(-1, 1f)
                            .setLocation(Location.TRAILING, Location.TOP)
                            .setAnimateDistance(0.7f, 0);
                    ModalDialog.showModal(FormOrder.this, new SimpleModalBorder(
                            inputOrder(), "Edit Category", SimpleModalBorder.YES_NO_OPTION,
                            (controller, action) -> {
                                if (action == SimpleModalBorder.YES_OPTION) {
                                    orderAPI.updateOrder(orderId, order).enqueue(new Callback<CreatedOrderResponse>() {
                                        @Override
                                        public void onResponse(Call<CreatedOrderResponse> call, Response<CreatedOrderResponse> response) {
                                            if (response.isSuccessful()) {
                                                JOptionPane.showMessageDialog(null, "Order updated successfully.");
                                                loadDataOrder();
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Update failed.");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CreatedOrderResponse> call, Throwable t) {
                                            JOptionPane.showMessageDialog(null, "Error: " + t.getMessage());
                                        }
                                    });
                                }
                            }
                    ), option);
                } else {
                    JOptionPane.showMessageDialog(null, "Order not found.");
                }
            }

            @Override
            public void onFailure(Call<CreatedOrderResponse> call, Throwable t) {
                JOptionPane.showMessageDialog(null, "Error: " + t.getMessage());
            }
        });
    }

    private void processOrder() {
        int selectedRow = -1;
        for (int i = 0; i < tableOrder.getRowCount(); i++) {
            Boolean isChecked = (Boolean) tableOrder.getValueAt(i, 0);
            if (isChecked != null && isChecked) {
                selectedRow = i;
                break;
            }
        }

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng để xử lý.");
            return;
        }
        String orderId = tableOrder.getValueAt(selectedRow, 1).toString();
        OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
        orderAPI.getOrderId(orderId).enqueue(new Callback<CreatedOrderResponse>() {
            @Override
            public void onResponse(Call<CreatedOrderResponse> call, Response<CreatedOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders order = response.body().getData();
                    order.setStatus("Đã Xử Lý");
                    orderAPI.updateOrder(orderId, order).enqueue(new Callback<CreatedOrderResponse>() {
                        @Override
                        public void onResponse(Call<CreatedOrderResponse> call, Response<CreatedOrderResponse> response) {
                            if (response.isSuccessful()) {
                                JOptionPane.showMessageDialog(FormOrder.this, "Đơn hàng đã được xử lý.");
                                loadDataOrder(); // Refresh bảng
                            } else {
                                JOptionPane.showMessageDialog(FormOrder.this, "Xử lý thất bại.");
                            }
                        }

                        @Override
                        public void onFailure(Call<CreatedOrderResponse> call, Throwable t) {
                            JOptionPane.showMessageDialog(FormOrder.this, "Lỗi: " + t.getMessage());
                        }
                    });

                } else {
                    JOptionPane.showMessageDialog(FormOrder.this, "Không tìm thấy đơn hàng.");
                }
            }

            @Override
            public void onFailure(Call<CreatedOrderResponse> call, Throwable t) {
                JOptionPane.showMessageDialog(FormOrder.this, "Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void deleteOrder() {
        int selectedRow = -1;
        for (int i = 0; i < tableOrder.getRowCount(); i++) {
            Boolean isChecked = (Boolean) tableOrder.getValueAt(i, 0);
            if (isChecked != null && isChecked) {
                selectedRow = i;
                break;
            }
        }

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục để xoá.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Confirm", "Are you sure?", JOptionPane.YES_NO_OPTION);
        String categoryId = tableOrder.getValueAt(selectedRow, 1).toString();
        if (result == JOptionPane.YES_OPTION) {
            OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
            orderAPI.deleteOrder(categoryId).enqueue(new Callback<DeleteOrderResponse>() {
                @Override
                public void onResponse(Call<DeleteOrderResponse> call, Response<DeleteOrderResponse> response) {
                    if (response.isSuccessful()) {
                        JOptionPane.showMessageDialog(null, "Order deleted successfully.");
                        loadDataOrder();
                    }
                }

                @Override
                public void onFailure(Call<DeleteOrderResponse> call, Throwable throwable) {
                    JOptionPane.showMessageDialog(null, throwable.getMessage());
                }
            });
        }
    }
    //------------------


}
