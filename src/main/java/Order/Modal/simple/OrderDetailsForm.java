package Order.Modal.simple;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.OrderItemsAPI;
import Order.Modal.Entity.orders_items;
import Order.Modal.Response.order_items.CreatedOrderItemsReponse;
import Order.Modal.Response.order_items.OrderItemResponse;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderDetailsForm extends JPanel {

    // Data fields
    private int id;
    private String orderId;
    private int productId;
    private int quantity;
    private int subtotal;
    private int status;
    private String notes;
    private Date createdAt = new Date();

    private boolean isEditing = false;
    private JPanel contentPanel;
    private JPanel editPanel;
    private JPanel viewPanel;
    private JPanel headerPanel;

    // Edit form components
    private JTextField txtOrderId;
    private JTextField txtProductId;
    private JTextField txtQuantity;
    private JTextField txtSubtotal;
    private JComboBox<String> comboStatus;
    private JTextArea txtNotes;

    // Default constructor for testing
    public OrderDetailsForm() {
       // Default to invalid orderId
    }

    // Constructor with orderId parameter
    public OrderDetailsForm(String orderId) {
        this.orderId = orderId;

        // Initialize date
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            createdAt = dateFormat.parse("2025-05-04T23:12:34");
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();
        loadOrderItemsFromApi(orderId);
        // Load data if we have a valid orderId

    }

    private void loadOrderItemsFromApi(String orderId) {
        try {
            OrderItemsAPI orderItemsAPI = APIClient.getClient().create(OrderItemsAPI.class);
            orderItemsAPI.getOrderItemsId(orderId).enqueue(new Callback<OrderItemResponse>() {
                @Override
                public void onResponse(Call<OrderItemResponse> call, Response<OrderItemResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        orders_items items = response.body().getData();
                        if (items != null) {
                            // Hiển thị order item đầu tiên hoặc có thể hiển thị danh sách
                            updateUIWithOrderDetails(items);
                        } else {
                            JOptionPane.showMessageDialog(OrderDetailsForm.this,
                                    "Không tìm thấy chi tiết đơn hàng",
                                    "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        String errorMessage = "Không thể tải thông tin đơn hàng";
                        if (response.body() != null) {
                            errorMessage = response.body().getMessage();
                        }
                        JOptionPane.showMessageDialog(OrderDetailsForm.this,
                                errorMessage,
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                @Override
                public void onFailure(Call<OrderItemResponse> call, Throwable throwable) {
                    JOptionPane.showMessageDialog(OrderDetailsForm.this,
                            "Lỗi kết nối API: " + throwable.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(OrderDetailsForm.this,
                    "Lỗi: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUIWithOrderDetails(orders_items order) {
        if (order == null) {
            JOptionPane.showMessageDialog(this,
                    "Đơn hàng không hợp lệ hoặc không tồn tại.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gán giá trị cho các thuộc tính
        this.id = order.getId();
        this.orderId = order.getOrder_id();
        this.productId = order.getProduct_id();
        this.quantity = order.getQuantity();
        this.subtotal = order.getSubtotal();
        this.status = order.getStatus();
        this.notes = order.getNotes();

        // Xử lý ngày tạo (createdAt) với kiểm tra null và định dạng
        try {
            if (order.getCreatedAt() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                this.createdAt = dateFormat.parse(String.valueOf(order.getCreatedAt()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Định dạng ngày không hợp lệ: " + order.getCreatedAt(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Cập nhật giao diện
        try {
            // Update header
            if (headerPanel != null) {
                remove(headerPanel);
            }
            headerPanel = createHeaderPanel();
            add(headerPanel, BorderLayout.NORTH, 0);

            // Refresh content panels
            if (contentPanel != null) {
                if (viewPanel != null) {
                    contentPanel.remove(viewPanel);
                }
                viewPanel = createViewPanel();
                contentPanel.add(viewPanel, "VIEW");

                if (editPanel != null) {
                    contentPanel.remove(editPanel);
                }
                editPanel = createEditPanel();
                contentPanel.add(editPanel, "EDIT");
            }

            // Show view mode
            showViewMode();

            // Refresh UI
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi khi cập nhật giao diện: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void init() {
        // Set up the main panel with drawer-like layout
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(450, 600));

        // Create header panel
        headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Create content panel that will switch between view and edit modes
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder());

        // Create view panel
        viewPanel = createViewPanel();
        contentPanel.add(viewPanel, "VIEW");

        // Create edit panel
        editPanel = createEditPanel();
        contentPanel.add(editPanel, "EDIT");

        // Add content panel to main panel
        add(contentPanel, BorderLayout.CENTER);

        // Create footer panel with action buttons
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

        // Start in view mode
        showViewMode();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Title with order ID
        JLabel titleLabel = new JLabel("Chi tiết đơn hàng #" + orderId);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.WEST);

        // Close button
        JButton closeButton = new JButton();
        closeButton.setIcon(new FlatSVGIcon("Order/icons/close.svg", 16, 16));
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setVisible(false);
        closeButton.addActionListener(e -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
        });
        panel.add(closeButton, BorderLayout.EAST);

        // Date label
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        JLabel dateLabel = new JLabel(dateFormat.format(createdAt));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(100, 100, 100));
        panel.add(dateLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createViewPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ID and Status section
        JPanel idStatusPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        idStatusPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        idStatusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ID section
        JPanel idPanel = new JPanel(new BorderLayout());
        JLabel idTitleLabel = new JLabel("ID");
        idTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel idValueLabel = new JLabel(String.valueOf(id));
        idValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        idPanel.add(idTitleLabel, BorderLayout.NORTH);
        idPanel.add(idValueLabel, BorderLayout.CENTER);

        // Status section
        JPanel statusPanel = new JPanel(new BorderLayout());
        JLabel statusTitleLabel = new JLabel("Trạng thái");
        statusTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel statusValueLabel = new JLabel(getStatusLabel(status));
        statusValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusValueLabel.setForeground(getStatusColor(status));
        statusPanel.add(statusTitleLabel, BorderLayout.NORTH);
        statusPanel.add(statusValueLabel, BorderLayout.CENTER);

        idStatusPanel.add(idPanel);
        idStatusPanel.add(statusPanel);
        panel.add(idStatusPanel);

        // Separator
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Order details section
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Product ID
        JPanel productIdPanel = createDetailRow("Mã sản phẩm:", String.valueOf(productId));
        detailsPanel.add(productIdPanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Quantity
        JPanel quantityPanel = createDetailRow("Số lượng:", String.valueOf(quantity));
        detailsPanel.add(quantityPanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(-1, 10)));

        // Subtotal
        JPanel subtotalPanel = createDetailRow("Tổng tiền:", formatCurrency(subtotal));
        detailsPanel.add(subtotalPanel);
        panel.add(detailsPanel);

        // Separator
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Notes section
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS));
        notesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(createBoldText("Ghi chú:"));
        txtNotes = new JTextArea(notes);
        txtNotes.setRows(5);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        txtNotes.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Note");

        JScrollPane scrollPane = new JScrollPane(txtNotes);
        panel.add(scrollPane, "growx, h 100:100:");

        panel.add(notesPanel);

        return panel;
    }

    private JPanel createEditPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 20", "[fill]"));
        // Order ID
        panel.add(createBoldText("Mã đơn hàng:"));
        txtOrderId = new JTextField(String.valueOf(orderId));
        txtOrderId.setEditable(false); // Order ID không nên sửa
        panel.add(txtOrderId, "growx, gapbottom 15");

        // Product ID
        panel.add(createBoldText("Mã sản phẩm:"));
        txtProductId = new JTextField(String.valueOf(productId));
        panel.add(txtProductId, "growx, gapbottom 15");

        // Quantity
        panel.add(createBoldText("Số lượng:"));
        txtQuantity = new JTextField(String.valueOf(quantity));
        panel.add(txtQuantity, "growx, gapbottom 15");

        // Subtotal
        panel.add(createBoldText("Tổng tiền:"));
        txtSubtotal = new JTextField(String.valueOf(subtotal));
        panel.add(txtSubtotal, "growx, gapbottom 15");

        // Status
        panel.add(createBoldText("Trạng thái:"));
        String[] statuses = {"Đang chờ", "Đã xác nhận", "Đang chuẩn bị", "Đã hoàn thành", "Đã hủy"};
        comboStatus = new JComboBox<>(statuses);
        comboStatus.setSelectedIndex(status);
        panel.add(comboStatus, "growx, gapbottom 15");

        // Notes
        panel.add(createBoldText("Ghi chú:"));
        txtNotes = new JTextArea(notes);
        txtNotes.setRows(5);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(txtNotes);
        panel.add(scrollPane, "growx, h 100:100:");

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Delete button
        JButton deleteButton = new JButton("Xóa");
        deleteButton.setIcon(new FlatSVGIcon("Order/icons/delete.svg", 16, 16));
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa chi tiết đơn hàng này?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
//                deleteOrderItem();
            }
        });
        panel.add(deleteButton, BorderLayout.WEST);

        // Right buttons panel
        JPanel rightButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        // Close/Cancel button
        JButton closeButton = new JButton(isEditing ? "Hủy" : "Đóng");
        closeButton.addActionListener(e -> {
            if (isEditing) {
                showViewMode();
            } else {
                ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
            }
        });

        // Edit/Save button
        JButton editButton = new JButton(isEditing ? "Lưu thay đổi" : "Chỉnh sửa");
        editButton.setIcon(new FlatSVGIcon(isEditing ? "Order/icons/save.svg" : "Order/icons/edit.svg", 16, 16));
        editButton.putClientProperty(FlatClientProperties.STYLE, "background:@accentColor; foreground:#F0F0F0;");
        editButton.addActionListener(e -> {
            if (isEditing) {
//                saveChanges();
                showViewMode();
            } else {
                showEditMode();
            }
        });

        rightButtonsPanel.add(closeButton);
        rightButtonsPanel.add(editButton);
        panel.add(rightButtonsPanel, BorderLayout.EAST);

        return panel;
    }



    private JPanel createDetailRow(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setHorizontalAlignment(JLabel.RIGHT);

        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(valueComponent, BorderLayout.EAST);

        return panel;
    }

    private JLabel createBoldText(String text) {
        JLabel label = new JLabel(text);
        label.putClientProperty(FlatClientProperties.STYLE, "font:bold;");
        return label;
    }

    private String formatCurrency(int amount) {
        DecimalFormat formatter = new DecimalFormat("#,### VNĐ");
        return formatter.format(amount);
    }

    private String getStatusLabel(int status) {
        switch (status) {
            case 0: return "Đang chờ";
            case 1: return "Đã xác nhận";
            case 2: return "Đang chuẩn bị";
            case 3: return "Đã hoàn thành";
            case 4: return "Đã hủy";
            default: return "Không xác định";
        }
    }

    private Color getStatusColor(int status) {
        switch (status) {
            case 0: return new Color(108, 117, 125); // Gray
            case 1: return new Color(25, 135, 84);   // Green
            case 2: return new Color(255, 193, 7);   // Yellow
            case 3: return new Color(13, 110, 253);  // Blue
            case 4: return new Color(220, 53, 69);   // Red
            default: return Color.BLACK;
        }
    }

    private void showViewMode() {
        isEditing = false;
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "VIEW");

        // Update footer buttons
        remove(getComponent(2));
        add(createFooterPanel(), BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void showEditMode() {
        isEditing = true;
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "EDIT");

        // Update footer buttons
        remove(getComponent(2));
        add(createFooterPanel(), BorderLayout.SOUTH);
        revalidate();
        repaint();
    }


}