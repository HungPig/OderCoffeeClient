package Order.Modal.simple;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.OrderItemAPI;
import Order.Modal.Api.ProductAPI;
import Order.Modal.Entity.orders;
import Order.Modal.Entity.orders_items;
import Order.Modal.Entity.products;
import Order.Modal.Response.ApiResponse;
import Order.Modal.Response.order_items.CreatedOrderItemsReponse;
import Order.Modal.Response.order_items.OrderItemResponse;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrderItemsEditDialog extends JDialog {
    private orders_items orderItem;
    private orders parentOrder; // Added parent order reference
    private JComboBox<products> comboProducts;
    private JSpinner spinnerQuantity;
    private JTextField txtSubtotal;
    private JTextField txtNotes;
    // Getter to check if the item was saved
    @Getter
    private boolean itemSaved = false; // Track if changes were saved

    public OrderItemsEditDialog(Frame parent, orders_items orderItem) {
        super(parent, "Edit Order Item", true);
        this.orderItem = orderItem;
        initUI();
        loadProducts();
    }

    // New constructor with parent order
    public OrderItemsEditDialog(Frame parent, orders_items orderItem, orders parentOrder) {
        super(parent, "Edit Order Item", true);
        this.orderItem = orderItem;
        this.parentOrder = parentOrder;
        initUI();
        loadProducts();
    }

    private void initUI() {
        setLayout(new MigLayout("fillx,wrap", "[fill][fill]", "[][][][][]"));
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Product
        add(new JLabel("Product:"));
        comboProducts = new JComboBox<>();
        comboProducts.addActionListener(e -> updateSubtotal());
        add(comboProducts, "wrap");

        // Quantity
        add(new JLabel("Quantity:"));
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(orderItem.getQuantity(), 1, 1000, 1));
        spinnerQuantity.addChangeListener(e -> updateSubtotal());
        add(spinnerQuantity, "wrap");

        // Subtotal
        add(new JLabel("Subtotal:"));
        txtSubtotal = new JTextField(String.valueOf(orderItem.getSubtotal()));
        txtSubtotal.setEditable(false);
        add(txtSubtotal, "wrap");

        // Notes
        add(new JLabel("Notes:"));
        txtNotes = new JTextField(orderItem.getNotes() != null ? orderItem.getNotes() : "");
        add(txtNotes, "wrap");

        // Buttons
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> saveOrderItem());
        btnCancel.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, "span");
    }

    private void loadProducts() {
        ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
        productAPI.getAllProducts().enqueue(new Callback<ApiResponse<List<products>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<products>>> call, Response<ApiResponse<List<products>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (products product : response.body().getData()) {
                        comboProducts.addItem(product);
                        if (product.getId().equals(String.valueOf(orderItem.getProduct_id()))) {
                            comboProducts.setSelectedItem(product);
                        }
                    }
                    // Update subtotal after products are loaded
                    updateSubtotal();
                } else {
                    // Handle API response error
                    String errorMessage = response.errorBody() != null ? response.errorBody().toString() : "Unknown error";
                    JOptionPane.showMessageDialog(OrderItemsEditDialog.this,
                            "Failed to load products. Error: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<products>>> call, Throwable t) {
                // Show API connection error
                JOptionPane.showMessageDialog(OrderItemsEditDialog.this,
                        "API Error: Failed to connect. " + t.getMessage());
            }
        });
    }

    private void updateSubtotal() {
        products selectedProduct = (products) comboProducts.getSelectedItem();
        int quantity = (int) spinnerQuantity.getValue();
        if (selectedProduct != null) {
            int subtotal = selectedProduct.getPrice() * quantity;
            txtSubtotal.setText(String.valueOf(subtotal));
        }
    }

    private void saveOrderItem() {
        products selectedProduct = (products) comboProducts.getSelectedItem();
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }

        int quantity = (int) spinnerQuantity.getValue();
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.");
            return;
        }

        try {
            int subtotal = Integer.parseInt(txtSubtotal.getText());
            if (subtotal <= 0) {
                JOptionPane.showMessageDialog(this, "Subtotal must be greater than zero.");
                return;
            }

            // Cập nhật orderItem
            orderItem.setProduct_id(Integer.valueOf(selectedProduct.getId()));
            orderItem.setQuantity(quantity);
            orderItem.setSubtotal(subtotal);
            orderItem.setNotes(txtNotes.getText());

            // Log dữ liệu
            System.out.println("Updating Order Item: " + orderItem);

            // Gọi API
            OrderItemAPI orderItemAPI = APIClient.getClient().create(OrderItemAPI.class);
            orderItemAPI.updateOrderItems(String.valueOf(orderItem.getId()), orderItem).enqueue(new Callback<OrderItemResponse>() {
                @Override
                public void onResponse(Call<OrderItemResponse> call, Response<OrderItemResponse> response) {
                    if (response.isSuccessful()) {
                        JOptionPane.showMessageDialog(OrderItemsEditDialog.this, "Order item updated successfully.");
                        itemSaved = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(OrderItemsEditDialog.this, "Failed to update order item. Status Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<OrderItemResponse> call, Throwable t) {
                    JOptionPane.showMessageDialog(OrderItemsEditDialog.this, "API Error: " + t.getMessage());
                }
            });
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Subtotal is invalid. Please check the values.");
        }
    }

    // New method to save the parent order
    public void save() {
        if (parentOrder != null) {
            // Implement the logic to save the parent order here
            // This could involve calling an API to update the entire order
            JOptionPane.showMessageDialog(this, "Saving parent order...");
            // Example implementation would go here
        }
    }
}