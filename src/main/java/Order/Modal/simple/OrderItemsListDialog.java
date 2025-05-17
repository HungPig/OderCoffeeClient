
package Order.Modal.simple;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.OrderAPI;
import Order.Modal.Api.OrderItemAPI;
import Order.Modal.Entity.orders;
import Order.Modal.Entity.orders_items;
import Order.Modal.Entity.products;
import Order.Modal.Response.ApiResponse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import Order.Modal.Response.order_items.OrderItemResponse;
import Order.Modal.Response.orders.CreatedOrderResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderItemsListDialog extends JDialog {
    private JTable tableOrderItems;
    private DefaultTableModel tableModel;
    private List<orders_items> orderItems;
    private orders parentOrder;// Add new item button
    private JButton btnEdit;
    private JButton btnDelete; // Delete item button
    private JButton btnClose;
    private JLabel lblTotalAmount; // Display total amount

    public OrderItemsListDialog(Frame parent, orders parentOrder) {
        super(parent, "Edit Order Items", true);
        this.parentOrder = parentOrder;
        this.orderItems = parentOrder.getItems();

        initComponents();
        loadOrderItems();
        calculateTotal();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setSize(700, 500);
        setLocationRelativeTo(getOwner());

        // Create table model with column names
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Product ID", "Product Name", "Quantity", "Unit Price", "Subtotal", "Notes"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };

        tableOrderItems = new JTable(tableModel);
        tableOrderItems.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tableOrderItems.getColumnModel().getColumn(1).setPreferredWidth(80); // Product ID
        tableOrderItems.getColumnModel().getColumn(2).setPreferredWidth(200); // Product Name
        tableOrderItems.getColumnModel().getColumn(3).setPreferredWidth(80); // Quantity
        tableOrderItems.getColumnModel().getColumn(4).setPreferredWidth(100); // Unit Price
        tableOrderItems.getColumnModel().getColumn(5).setPreferredWidth(100); // Subtotal
        tableOrderItems.getColumnModel().getColumn(6).setPreferredWidth(150); // Notes
        tableOrderItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tableOrderItems);
        add(scrollPane, BorderLayout.CENTER);

        // Create top panel with info
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Order ID: " + parentOrder.getId()));

        // Total amount label
        lblTotalAmount = new JLabel("Total: 0");
        topPanel.add(lblTotalAmount);

        add(topPanel, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));


        btnEdit = new JButton("Edit Item");
        btnDelete = new JButton("Delete Item");
        btnClose = new JButton("Close");

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        btnEdit.addActionListener(e -> editSelectedItem());
        btnDelete.addActionListener(e -> deleteSelectedItem());
        btnClose.addActionListener(e -> dispose());

        // Double click to edit
        tableOrderItems.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editSelectedItem();
                }
            }
        });
    }

    private void loadOrderItems() {
        tableModel.setRowCount(0); // Clear table

        if (orderItems != null && !orderItems.isEmpty()) {
            for (orders_items item : orderItems) {
                // Fetch product name (in a real app, you would probably have a cache of products)
                String productName = getProductName(item.getProduct_id());
                int unitPrice = item.getSubtotal() / item.getQuantity();

                tableModel.addRow(new Object[]{
                        item.getId(),
                        item.getProduct_id(),
                        productName,
                        item.getQuantity(),
                        unitPrice,
                        item.getSubtotal(),
                        item.getNotes()
                });
            }
        }

        calculateTotal();
    }

    private String getProductName(int productId) {
        // In a real application, you would get this from your product cache or database
        // For this example, we'll just return a placeholder
        return "Product #" + productId;
    }



    private void editSelectedItem() {
        int selectedRow = tableOrderItems.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to edit.");
            return;
        }

        // Get the selected item
        orders_items selectedItem = orderItems.get(selectedRow);

        // Open edit dialog
        OrderItemsEditDialog editDialog = new OrderItemsEditDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                selectedItem,
                parentOrder);
        editDialog.setVisible(true);

        // After editing, reload the list
        loadOrderItems();
    }

    private void deleteSelectedItem() {
        int selectedRow = tableOrderItems.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this item?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Get the selected item
            orders_items selectedItem = orderItems.get(selectedRow);

            // Remove from list
            orderItems.remove(selectedRow);

            // Remove from table
            tableModel.removeRow(selectedRow);

            // Recalculate total
            calculateTotal();

            // In a real app, you would also call the API to delete the item
            // For example:

            OrderItemAPI orderItemAPI = APIClient.getClient().create(OrderItemAPI.class);
            orderItemAPI.deleteOrderItems(String.valueOf(selectedItem.getId())).enqueue(new Callback<OrderItemResponse>() {
                @Override
                public void onResponse(Call<OrderItemResponse> call, Response<OrderItemResponse> response) {
                    if (response.isSuccessful()) {
                        JOptionPane.showMessageDialog(null, "Category created successfully");
                    } else {
                        // Handle error
                    }
                }

                @Override
                public void onFailure(Call<OrderItemResponse> call, Throwable t) {
                    // Handle connection error
                }
            });

        }
    }

    private void calculateTotal() {
        int total = 0;

        if (orderItems != null) {
            for (orders_items item : orderItems) {
                total += item.getSubtotal();
            }
        }

        lblTotalAmount.setText("Total: " + total);

        // Update parent order total
        parentOrder.setTotal_amount(total);
    }

    private void saveOrderItems() {
        // Update the items list in the parent order
        parentOrder.setItems(orderItems);

        // Call API to update the entire order
        OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
        orderAPI.updateOrder(parentOrder.getId(), parentOrder).enqueue(new Callback<CreatedOrderResponse>() {
            @Override
            public void onResponse(Call<CreatedOrderResponse> call, Response<CreatedOrderResponse> response) {
                if (response.isSuccessful()) {
                    JOptionPane.showMessageDialog(OrderItemsListDialog.this,
                            "Order items saved successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(OrderItemsListDialog.this,
                            "Failed to save order items. Error: " +
                                    (response.errorBody() != null ? response.errorBody().toString() : "Unknown error"));
                }
            }

            @Override
            public void onFailure(Call<CreatedOrderResponse> call, Throwable t) {
                JOptionPane.showMessageDialog(OrderItemsListDialog.this,
                        "API Error: Failed to connect. " + t.getMessage());
            }
        });
    }
}