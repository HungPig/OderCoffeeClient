package Order.Modal.forms;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.OrderAPI;
import Order.Modal.Api.ProductAPI;
import Order.Modal.Api.TableAPI;
import Order.Modal.Entity.orders;
import Order.Modal.Entity.orders_items;
import Order.Modal.Entity.products;
import Order.Modal.Entity.tables;
import Order.Modal.Response.ApiResponse;
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

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SystemForm(name = "Order", description = "table is a user interface component", tags = {"list"})
public class FormOrder extends Form {
    private List<orders> allOrders = new ArrayList<>();
    private JTextField txtFieldOrderID;
    private JComboBox<String> comboOrderStatus;
    private JTextField txtTotalOrderAmount;
    private JComboBox<products> comboProducts;
    private JComboBox<tables> comboTableOrder;
    private JPanel panel;
    private int orderItemCount = 0;
    JTable tableOrder;
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public FormOrder() {
        txtFieldOrderID = new JTextField();
        txtFieldOrderID.setEditable(false);
        txtTotalOrderAmount = new JTextField();
        comboOrderStatus = new JComboBox<>(new String[]{"Đơn Hàng Mới", "Đã Xử Lý", "Đã hủy"});
        comboTableOrder = new JComboBox<tables>();
        comboProducts = new JComboBox<products>();
        loadDataOrder();
        init();
        loadTables();
        loadProducts();
    }

    @Override
    public void formRefresh() {
        loadDataOrder();
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
        return tabb;
    }

    private Component createBorder(Component component) {
        JPanel panel = new JPanel(new MigLayout("fill,insets 7 0 7 0", "[fill]", "[fill]"));
        panel.add(component);
        return panel;
    }


    private Component createOrderTable() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));
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
        tableOrder.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 0) {
                int changedRow = e.getFirstRow();
                for (int i = 0; i < tableOrder.getRowCount(); i++) {
                    if (i != changedRow && (Boolean) tableOrder.getValueAt(i, 0)) {
                        tableOrder.setValueAt(false, i, 0);
                    }
                }
            }
        });
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

    private Component createHeaderOrderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Order/icons/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Create");
        JButton cmdEdit = new JButton("Edit");
        JButton cmdDelete = new JButton("Delete");
        JButton cmdProceed = new JButton("Details");

        cmdCreate.addActionListener(e -> createOrder());
        cmdEdit.addActionListener(e -> editOrder());
        cmdDelete.addActionListener(e -> deleteOrder());
        cmdProceed.addActionListener(e -> showModal());
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
            int totalAmount = order.calculateTotalAmount();
            model.addRow(new Object[]{
                    false,
                    order.getId(),
                    "Bàn " + order.getTable_id(),
                    order.getStatus(),
                    currencyFormatter.format(totalAmount),
                    order.getCreated_at(),
                    order.getUpdated_at(),
            });
        }

    }

    public Component inputOrder() {
        JPanel mainPanel = new JPanel(new MigLayout("fillx,wrap,insets 10 20 10 20", "[fill]", ""));

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
        mainPanel.add(title, "gapy 5 0");
        mainPanel.add(new JSeparator(), "height 2!,gapy 0 10");

        // Thêm các trường nhập

        mainPanel.add(new JLabel("Table ID"));
        mainPanel.add(comboTableOrder);
        //button
        if (panel == null) {
            panel = new JPanel(new MigLayout("fillx,wrap")); // Tạo lại panel nếu chưa có
        }
        JButton btnAddOrderItem = new JButton("Add Order Item");
        btnAddOrderItem.addActionListener(e -> addOrderItem());
        mainPanel.add(btnAddOrderItem, "wrap");
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Không cuộn ngang
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Cuộn dọc khi cần
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Tăng tốc độ cuộn

        // Thêm JScrollPane vào giao diện chính
        mainPanel.add(scrollPane, "growx, h 300!");
        return mainPanel;
    }

    private void addOrderItem() {
        orderItemCount++; // Tăng số lượng Order Items

        // Tạo một JPanel để chứa các thành phần của Order Item
        JPanel orderItemPanel = new JPanel(new GridBagLayout());
        orderItemPanel.setBorder(BorderFactory.createTitledBorder("Order Item " + orderItemCount));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tạo nhãn và JComboBox cho sản phẩm
        JLabel lblProduct = new JLabel("Product:");
        JComboBox<products> comboProducts = new JComboBox<>();
        loadProductsToComboBox(comboProducts); // Tải dữ liệu sản phẩm vào JComboBox

        // Các thành phần khác: Số lượng, Subtotal, Ghi chú
        JLabel lblQuantity = new JLabel("Quantity:");
        JSpinner spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

        JLabel lblSubtotal = new JLabel("Subtotal:");
        JTextField txtSubtotal = new JTextField("0 VND");
        txtSubtotal.setEditable(false);

        JLabel lblNotes = new JLabel("Notes:");
        JTextField txtNote = new JTextField();
        txtNote.putClientProperty("JTextField.placeholderText", "Ghi chú (Notes)");

        // Nút xóa Order Item
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> {
            panel.remove(orderItemPanel); // Xóa Order Item Panel
            panel.revalidate();
            panel.repaint();
            orderItemCount--;
            updateOrderItemLabels(); // Cập nhật lại số thứ tự
        });

        // Cập nhật giá trị subtotal khi thay đổi sản phẩm hoặc số lượng
        comboProducts.addActionListener(e -> updateSubtotal(comboProducts, spinnerQuantity, txtSubtotal));
        spinnerQuantity.addChangeListener(e -> updateSubtotal(comboProducts, spinnerQuantity, txtSubtotal));

        // Thêm các thành phần vào panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        orderItemPanel.add(lblProduct, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        orderItemPanel.add(comboProducts, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        orderItemPanel.add(lblQuantity, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        orderItemPanel.add(spinnerQuantity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        orderItemPanel.add(lblSubtotal, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        orderItemPanel.add(txtSubtotal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        orderItemPanel.add(lblNotes, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        orderItemPanel.add(txtNote, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        orderItemPanel.add(btnDelete, gbc);

        // Thêm Order Item Panel vào panel chính
        panel.add(orderItemPanel);
        panel.revalidate();
        panel.repaint();
    }

    private void loadProductsToComboBox(JComboBox<products> comboBox) {
        ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
        productAPI.getAllProducts().enqueue(new Callback<ApiResponse<List<products>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<products>>> call, Response<ApiResponse<List<products>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (products product : response.body().getData()) {
                        comboBox.addItem(product); // Thêm sản phẩm vào JComboBox
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi dữ liệu product!");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<products>>> call, Throwable throwable) {
                JOptionPane.showMessageDialog(null, throwable.getMessage());
            }
        });
    }

    // Hàm cập nhật lại số thứ tự các Order Items sau khi xóa
    private void updateOrderItemLabels() {
        int currentOrderItem = 1;
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel orderItemPanel) {
                TitledBorder border = (TitledBorder) orderItemPanel.getBorder();
                border.setTitle("Order Item " + currentOrderItem);
                currentOrderItem++;
            }
        }

        // Cập nhật giao diện
        panel.revalidate();
        panel.repaint();
    }

    private void updateSubtotal(JComboBox<products> comboProducts, JSpinner spinnerQuantity, JTextField txtSubtotal) {
        try {
            // Lấy sản phẩm được chọn
            products selectedProduct = (products) comboProducts.getSelectedItem();
            if (selectedProduct != null) {
                int price = selectedProduct.getPrice(); // Giá sản phẩm
                int quantity = (int) spinnerQuantity.getValue(); // Số lượng
                int subtotal = price * quantity; // Tính tổng tiền
                txtSubtotal.setText(String.format("%,d VND", subtotal)); // Hiển thị tổng tiền
            } else {
                txtSubtotal.setText("0 VND"); // Nếu không có sản phẩm nào được chọn
            }
        } catch (Exception e) {
            txtSubtotal.setText("0 VND");
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật Subtotal: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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

    private void loadProducts() {
        comboProducts = new JComboBox<>();
        ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);

        productAPI.getAllProducts().enqueue(new Callback<ApiResponse<List<products>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<products>>> call, Response<ApiResponse<List<products>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (products product : response.body().getData()) {
                        comboProducts.addItem(product);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi dữ liệu product!");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<products>>> call, Throwable throwable) {
                JOptionPane.showMessageDialog(null, throwable.getMessage());
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
                    } else {
                        System.out.println("API không trả về dữ liệu hoặc trả về lỗi: " + response.message());
                        JOptionPane.showMessageDialog(FormOrder.this, "Không thể tải dữ liệu đơn hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                        orders order = new orders();
                        orders_items items = new orders_items();
                        order.setTable_id(selectedTable.getId());
                        order.setStatus("Đơn Hàng Mới");
                        List<orders_items> itemsList = collectOrderItems();
                        if (itemsList.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please add at least one order item.");
                            return;
                        }
                        order.setItems(itemsList);
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

    private List<orders_items> collectOrderItems() {
        List<orders_items> itemsList = new ArrayList<>();
        for (Component component : panel.getComponents()) { // panelItems chứa tất cả các Order Item
            if (component instanceof JPanel itemPanel) {

                // Lấy dữ liệu từ các trường trong giao diện
                JComboBox<products> comboProduct = null;
                JSpinner spinnerQuantity = null;
                JTextField txtSubtotal = null;
                JTextField txtNote = null;

                // Duyệt qua các thành phần của itemPanel để tìm đúng loại
                for (Component child : itemPanel.getComponents()) {
                    if (child instanceof JComboBox) {
                        comboProduct = (JComboBox<products>) child;
                    } else if (child instanceof JSpinner) {
                        spinnerQuantity = (JSpinner) child;
                    } else if (child instanceof JTextField) {
                        if (txtSubtotal == null) {
                            txtSubtotal = (JTextField) child; // Giả định TextField đầu tiên là Subtotal
                        } else {
                            txtNote = (JTextField) child; // TextField thứ hai là Note
                        }
                    }
                }

                // Kiểm tra nếu tất cả các thành phần cần thiết đã được tìm thấy
                if (comboProduct == null || spinnerQuantity == null || txtSubtotal == null || txtNote == null) {
                    JOptionPane.showMessageDialog(null, "Không thể thu thập dữ liệu từ một số thành phần.");
                    continue;
                }

                // Lấy sản phẩm từ combo box
                products selectedProduct = (products) comboProduct.getSelectedItem();
                if (selectedProduct == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm.");
                    continue;
                }

                // Parse subtotal
                String subtotalText = txtSubtotal.getText().replace(" VND", "").replace(".", "").replace(",", ""); // Remove formatting
                int subtotal;
                try {
                    subtotal = Integer.parseInt(subtotalText); // Parse as int
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid subtotal value: " + subtotalText);
                    continue;
                }

                // Tạo đối tượng orders_items
                orders_items item = new orders_items();
                item.setProduct_id(Integer.valueOf(selectedProduct.getId())); // Map product ID
                item.setQuantity((int) spinnerQuantity.getValue()); // Get quantity
                item.setSubtotal(subtotal); // Set subtotal
                item.setNotes(txtNote.getText()); // Add notes

                itemsList.add(item);
            }
        }
        return itemsList;
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
    //------------------//
    //Order_Items Drawer
    private void showModal() {
        // Lấy dòng được chọn từ bảng
        int selectedRow = tableOrder.getSelectedRow();
        if (selectedRow == -1) {
            // Nếu không có dòng nào được chọn, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng để xem chi tiết.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy orderId từ dòng được chọn (cột 1 chứa ID)
        String orderId = tableOrder.getValueAt(selectedRow, 1).toString();

        // Tạo các tùy chọn nút
        SimpleModalBorder.Option[] options = {
                new SimpleModalBorder.Option("Process", 0), // Nút "Process" với action ID = 0
                new SimpleModalBorder.Option("Cancel", 1)  // Nút "Cancel" với action ID = 1
        };

        // Tạo dialog với các nút tùy chỉnh
        SimpleModalBorder modal = new SimpleModalBorder(
                orderDetail(orderId), // Nội dung của dialog
                "Chi tiết đơn hàng",  // Tiêu đề của dialog
                options,              // Các nút tùy chỉnh
                (controller, action) -> {
                    if (action == 0) { // Nếu nhấn nút "Process"
                        updateOrderStatus(Integer.parseInt(orderId),"Đã Hoàn Thành");
                    } else if (action == 1) { // Nếu nhấn nút "Cancel"
                        System.out.println("Action canceled");
                    }
                }
        );

        // Hiển thị dialog
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-1, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        ModalDialog.showModal(this, modal, option);
    }
    private void updateOrderStatus(int orderId, String newStatus) {
        OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
        orders order = new orders();
        order.setId(String.valueOf(orderId));
        order.setStatus(newStatus);

        orderAPI.updateOrder(order.getId(), order).enqueue(new Callback<CreatedOrderResponse>() {
            @Override
            public void onResponse(Call<CreatedOrderResponse> call, Response<CreatedOrderResponse> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(null, "Trạng thái đơn hàng đã được cập nhật thành '" + newStatus + "'!");
                    loadDataOrder();
                } else {
                    try {
                        String errorMessage = response.errorBody().string();
                        JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi không xác định.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CreatedOrderResponse> call, Throwable throwable) {
                JOptionPane.showMessageDialog(null, "Lỗi kết nối: " + throwable.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private Component orderDetail(String orderId) {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
        JLabel lblTableId = new JLabel();
        JLabel lblStatus = new JLabel();
        JLabel lbTitle = new JLabel();
        JLabel lbTime = new JLabel();
        JLabel lblTotalQuantity = new JLabel();
        JLabel lblTotalAmount = new JLabel();
        panel.add(lbTitle, "split 2");
        panel.add(lbTime, "gapleft push");

        panel.add(new JSeparator(), "grow");
        // ID và Trạng thái
        panel.add(lblTableId);
        panel.add(lblStatus);

        panel.add(new JSeparator(), "grow");
        JLabel labelOrder = new JLabel("Đơn Hàng Của Bàn");
        panel.add(labelOrder, "gapy15");
        labelOrder.putClientProperty(FlatClientProperties.STYLE, "font:+2");
        JPanel productListPanel = new JPanel(new MigLayout("fillx, wrap", "[fill]", ""));
        productListPanel.setOpaque(false);
        panel.add(productListPanel, "growx");
        panel.add(new JSeparator(), "grow");
        panel.add(lblTotalQuantity);
        panel.add(lblTotalAmount);
        panel.add(new JSeparator(), "grow");
        OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
        orderAPI.getOrderId(String.valueOf(orderId)).enqueue(new Callback<CreatedOrderResponse>() {
            @Override
            public void onResponse(Call<CreatedOrderResponse> call, Response<CreatedOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders order = response.body().getData();
                    lbTitle.setText("Chi tiết đơn hàng #" + order.getId());
                    lbTime.setText(order.getCreated_at());
                    lblTableId.setText("Table Id: " + order.getTable_id());
                    lblStatus.setText("Trạng Thái: " + order.getStatus());
                    // Populate product list
                    productListPanel.removeAll();
                    List<orders_items> items = order.getItems();
                    int totalQuantity = 0;
                    int totalAmount = 0;

                    for (orders_items item : items) {
                        addProductItem(productListPanel, item.getProduct_id(), item.getQuantity(), item.getSubtotal(), item.getNotes());
                        totalQuantity += item.getQuantity();
                        totalAmount += item.getSubtotal();
                    }
                    lblTotalQuantity.setText("Số Lượng: " + totalQuantity);
                    lblTotalAmount.setText("Tổng Tiền: " + String.format("%,d VND", totalAmount));
                    panel.revalidate();
                    panel.repaint();
                } else {
                    JOptionPane.showMessageDialog(panel, "Không thể tải thông tin đơn hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<CreatedOrderResponse> call, Throwable t) {
                JOptionPane.showMessageDialog(panel, "Lỗi kết nối: " + t.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private void addProductItem(JPanel parent, int productId, int quantity, int subtotal, String notes) {
        JLabel productLabel = new JLabel("Mã sản phẩm: " + productId);
        JLabel quantityLabel = new JLabel("Số lượng: " + quantity);
        JLabel subtotalLabel = new JLabel("Thành tiền: " + String.format("%,d VND", subtotal));
        JLabel notesLabel = new JLabel("Ghi chú: " + (notes != null ? notes : "Không có"));
        JSeparator separator = new JSeparator();
        parent.add(separator);
        parent.add(productLabel, "wrap");
        parent.add(quantityLabel, "wrap");
        parent.add(subtotalLabel, "wrap");
        parent.add(notesLabel, "wrap, gapbottom 10");
    }
}
