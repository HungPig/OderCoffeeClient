package Order.Modal.forms;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.CategoryAPI;
import Order.Modal.Api.ProductAPI;
import Order.Modal.Entity.categories;
import Order.Modal.Entity.products;
import Order.Modal.Response.ApiResponse;
import Order.Modal.Response.category.CreateCategoryResponse;
import Order.Modal.Response.category.DeleteCategoryResponse;
import Order.Modal.Response.products.CreatedProductReponse;
import Order.Modal.Response.products.DeleteProductResponse;
import Order.Modal.System.Form;
import Order.Modal.model.ModelProfile;
import Order.Modal.utils.ComboItem;
import Order.Modal.utils.DisplayUtils;
import Order.Modal.utils.SystemForm;
import Order.Modal.utils.table.CheckBoxTableHeaderRenderer;
import Order.Modal.utils.table.TableHeaderAlignment;
import Order.Modal.utils.table.TableProfileCellRenderer;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
import java.io.File;
import java.util.List;

import static Order.Modal.sample.SampleData.getProfileIcon;

@SystemForm(name = "Product", description = "Product table with advanced features", tags = {"list", "table"})
public class FormProduct extends Form {
    private JPanel panel;
    private JComboBox comboCategory;
    private JTextField txtFieldID;
    private JTextField txtFieldPrice;
    private JTextField txtFieldName;
    private JTextField txtFieldDescription;
    private JComboBox comboStatus;
    private JTable table;
    private JLabel lblImagePreview;
    private File selectedImageFile;

    public FormProduct() {
        loadData();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));
        // create table model
        Object columns[] = new Object[]{"SELECT", "#", "NAME", "PRICE", "STATUS", "DESCRIPTION"};
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
                if (columnIndex == 2) {
                    return ModelProfile.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };


        // create table
        table = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // table option
        table.getColumnModel().getColumn(0).setMaxWidth(50);  // SELECT
        table.getColumnModel().getColumn(1).setMaxWidth(50);  // #
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // NAME
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // PRICE
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // STATUS
        table.getColumnModel().getColumn(5).setPreferredWidth(250); // DESCRIPTION

        // disable reordering table column
        table.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer
        table.setDefaultRenderer(ModelProfile.class, new TableProfileCellRenderer(table));

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
        JLabel title = new JLabel("Product table");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2");
        add(title, "gapx 20");

        // create header
        add(createHeaderAction());
        add(scrollPane);

    }

    public void loadData() {
        try {
            ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
            productAPI.getAllProducts().enqueue(new Callback<ApiResponse<List<products>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<products>>> call, Response<ApiResponse<List<products>>> response) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    assert response.body() != null;
                    boolean defaultIcon = false;
                    for (products product : response.body().getData()) {
                        model.addRow(new Object[]{
                                false,
                                product.getId(),
                                new ModelProfile(
                                        getProfileIcon(product.getImage(), defaultIcon),
                                        product.getName(),
                                        DisplayUtils.getCategoryName(product.getCategory_id())
                                ),
                                product.getPrice(),
                                DisplayUtils.getStatusText(product.getStatus()),
                                product.getDescription()
                        });
                        table.setModel(model);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<List<products>>> call, Throwable throwable) {
                    JOptionPane.showMessageDialog(FormProduct.this,
                            "Lỗi kết nối API: " + throwable.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void formRefresh() {
        loadData();
    }

    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Order/icons/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Create");
        JButton cmdEdit = new JButton("Edit");
        JButton cmdDelete = new JButton("Delete");
        cmdCreate.addActionListener(e -> showModal());
        cmdEdit.addActionListener(e -> edit());
        cmdDelete.addActionListener(e -> Delete());
        txtSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if (txtSearch.getText().isEmpty()) {
                loadData();
            } else {
                searchProduct(keyword);
            }
        });
        panel.add(txtSearch);
        panel.add(cmdCreate);
        panel.add(cmdEdit);
        panel.add(cmdDelete);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }

    public Component inputProduct() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
        if (txtFieldID == null) {
            txtFieldID = new JTextField();
        }
        if (txtFieldName == null) {
            txtFieldName = new JTextField();
        }
        if (comboCategory == null) {
            comboCategory = new JComboBox();
        }
        if (txtFieldPrice == null) {
            txtFieldPrice = new JTextField();
        }
        if (comboStatus == null) {
            comboStatus = new JComboBox();
        }
        if (txtFieldDescription == null) {
            txtFieldDescription = new JTextField();
        }
        txtFieldID.setEditable(false);
        txtFieldID.setEditable(false);
        txtFieldID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID will be generated automatically");
        txtFieldName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. Cappuccino");
        comboCategory.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. Coffee");
        txtFieldPrice.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. 10.99");
        comboStatus.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. Active");
        txtFieldDescription.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Short description about category");
        JLabel lbTitle = new JLabel("Please complete the following Category to add data!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:+2");
        panel.add(lbTitle, "gapy 5 0");
        panel.add(new JSeparator(), "height 2!,gapy 0 5");
        JPanel pID = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbID = new JLabel("ID");
        lbID.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pID.add(lbID);
        pID.add(txtFieldID);
        panel.add(pID);

        JPanel pName = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbName = new JLabel("Name");
        lbName.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pName.add(lbName);
        pName.add(txtFieldName);
        panel.add(pName);

        JPanel pPrice = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbPrice = new JLabel("Price");
        lbPrice.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pPrice.add(lbPrice);
        pPrice.add(txtFieldPrice);
        panel.add(pPrice);
        JPanel pCate = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbCategory = new JLabel("Category");
        lbCategory.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pCate.add(lbCategory);
        pCate.add(comboCategory);
        panel.add(pCate);
        JPanel pStatus = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbStatus = new JLabel("Status");
        lbStatus.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pStatus.add(lbStatus);
        pStatus.add(comboStatus);
        panel.add(pStatus);
        JPanel pDescription = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbDescription = new JLabel("Description");
        lbDescription.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pDescription.add(lbDescription);
        pDescription.add(txtFieldDescription);
        panel.add(pDescription);
        JPanel pImage = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbImage = new JLabel("Product Image");
        lbImage.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        JButton btnChooseImage = new JButton("Choose Image");
        lblImagePreview = new JLabel();
        btnChooseImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Image");
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedImageFile.getAbsolutePath());
                Image scaledImg = icon.getImage().getScaledInstance(50, 100, Image.SCALE_SMOOTH);
                lblImagePreview.setIcon(new ImageIcon(scaledImg));
            }
        });
        pImage.add(lbImage);
        pImage.add(btnChooseImage);
        pImage.add(lblImagePreview, "h 50!, w 100!");
        panel.add(pImage);
        initComboStatus(comboStatus);
        initComboCate(comboCategory);
        return panel;
    }

    public void postProduct() {
        String name = txtFieldName.getText();
        String description = txtFieldDescription.getText();
        int priceStr = Integer.parseInt(txtFieldPrice.getText());
        ComboItem selectedStatus = (ComboItem) comboStatus.getSelectedItem();
        ComboItem selectedCategory = (ComboItem) comboCategory.getSelectedItem();
        int status = selectedStatus.getId();
        int categoryId = selectedCategory.getId();
        RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody descPart = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody pricePart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(priceStr));
        RequestBody statusPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(status));
        RequestBody categoryIdPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(categoryId));
        RequestBody filePart = RequestBody.create(selectedImageFile, MediaType.parse("image/*"));
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", selectedImageFile.getName(), filePart);
        ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
        productAPI.createProduct(namePart, descPart, pricePart, statusPart, categoryIdPart, image).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JOptionPane.showMessageDialog(null, "Product created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create product!");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                JOptionPane.showMessageDialog(null, "Error: " + throwable.getMessage());
            }
        });
    }

    private void searchProduct(String keyword) {
        ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
        productAPI.getProductBySearch(keyword).enqueue(new Callback<ApiResponse<List<products>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<products>>> call, Response<ApiResponse<List<products>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<products>> apiResponse = response.body();
                    List<products> categoriesList = apiResponse != null ? apiResponse.getData() : null;
                    SwingUtilities.invokeLater(() -> {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setRowCount(0);
                        boolean defaultIcon = false;
                        if (categoriesList != null) {
                            for (products product : categoriesList) {
                                model.addRow(new Object[]{
                                        false,
                                        product.getId(),
                                        new ModelProfile(
                                                getProfileIcon(product.getImage(), defaultIcon),
                                                product.getName(),
                                                DisplayUtils.getCategoryName(product.getCategory_id())
                                        ),
                                        product.getPrice(),
                                        DisplayUtils.getStatusText(product.getStatus()),
                                        product.getDescription()
                                });
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<products>>> call, Throwable throwable) {
                JOptionPane.showMessageDialog(FormProduct.this,
                        "Lỗi khi tìm kiếm: " + throwable.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void edit() {
        int selectedRow = -1;
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isChecked = (Boolean) table.getValueAt(i, 0);
            if (isChecked != null && isChecked) {
                selectedRow = i;
                break;
            }
        }
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to edit.");
            return;
        }
        Component form = inputProduct();

        String productId = table.getValueAt(selectedRow, 1).toString();
        ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
        productAPI.getProductId(productId).enqueue(new Callback<CreatedProductReponse>() {
            @Override
            public void onResponse(Call<CreatedProductReponse> call, Response<CreatedProductReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products product = response.body().getData();
                    txtFieldID.setText(product.getId());
                    txtFieldName.setText(product.getName());
                    txtFieldDescription.setText(product.getDescription());
                    txtFieldPrice.setText(String.valueOf(product.getPrice()));
                    selectComboItemByValue(comboStatus, String.valueOf(product.getStatus()));
                    selectComboItemByValue(comboCategory, String.valueOf(product.getCategory_id()));

                    Option option = ModalDialog.createOption();
                    option.getLayoutOption().setSize(-1, 1f)
                            .setLocation(Location.TRAILING, Location.TOP)
                            .setAnimateDistance(0.7f, 0);
                    ModalDialog.showModal(FormProduct.this, new SimpleModalBorder(
                            inputProduct(), "Edit Product", SimpleModalBorder.YES_NO_OPTION,
                            (controller, action) -> {
                                if (action == SimpleModalBorder.YES_OPTION) {
                                    String name = txtFieldName.getText();
                                    String description = txtFieldDescription.getText();
                                    int price = Integer.parseInt(txtFieldPrice.getText());
                                    int status = ((ComboItem) comboStatus.getSelectedItem()).getId();
                                    int categoryId = ((ComboItem) comboCategory.getSelectedItem()).getId();

                                    RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);
                                    RequestBody descPart = RequestBody.create(MediaType.parse("text/plain"), description);
                                    RequestBody pricePart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(price));
                                    RequestBody statusPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(status));
                                    RequestBody categoryIdPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(categoryId));

                                    MultipartBody.Part imagePart = null;
                                    if (selectedImageFile != null) {
                                        RequestBody filePart = RequestBody.create(selectedImageFile, MediaType.parse("image/*"));
                                        imagePart = MultipartBody.Part.createFormData("image", selectedImageFile.getName(), filePart);
                                    }

                                    productAPI.updateProduct(productId, namePart, descPart, pricePart, statusPart, categoryIdPart, imagePart)
                                            .enqueue(new Callback<CreatedProductReponse>() {
                                                @Override
                                                public void onResponse(Call<CreatedProductReponse> call, Response<CreatedProductReponse> response) {
                                                    if (response.isSuccessful()) {
                                                        JOptionPane.showMessageDialog(null, "Product updated successfully!");
                                                        loadData();
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Failed to update product.");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<CreatedProductReponse> call, Throwable t) {
                                                    JOptionPane.showMessageDialog(null, "Error: " + t.getMessage());
                                                }
                                            });
                                }
                            }
                    ), option);
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found.");
                }
            }

            @Override
            public void onFailure(Call<CreatedProductReponse> call, Throwable t) {
                JOptionPane.showMessageDialog(null, "Error: " + t.getMessage());
            }
        });

    }

    private void Delete() {
        int selectedRow = -1;
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isChecked = (Boolean) table.getValueAt(i, 0);
            if (isChecked != null && isChecked) {
                selectedRow = i;
                break;
            }
        }
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to edit.");
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Confirm", "Are you sure?", JOptionPane.YES_NO_OPTION);
        String productID = table.getValueAt(selectedRow, 1).toString();
        if (result == JOptionPane.YES_OPTION) {
            ProductAPI productAPI = APIClient.getClient().create(ProductAPI.class);
            productAPI.deleteProduct(productID).enqueue(new Callback<DeleteProductResponse>() {
                @Override
                public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                    if (response.isSuccessful()) {
                        loadData();
                    }
                }

                @Override
                public void onFailure(Call<DeleteProductResponse> call, Throwable throwable) {
                    JOptionPane.showMessageDialog(null, throwable.getMessage());
                }
            });
        }
    }

    private void selectComboItemByValue(JComboBox comboBox, String value) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            ComboItem item = (ComboItem) comboBox.getItemAt(i);
            if (String.valueOf(item.getId()).equals(value)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }



    private void initComboStatus(JComboBox<ComboItem> combo) {
        combo.removeAllItems();
        combo.addItem(new ComboItem(1, "Còn Hàng"));
        combo.addItem(new ComboItem(0, "Hết Hàng"));
    }

    private void initComboCate(JComboBox<ComboItem> combo) {
        combo.removeAllItems();
        combo.addItem(new ComboItem(1, "Cà Phê"));
        combo.addItem(new ComboItem(2, "Trà Sữa"));
    }

    private void showModal() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-1, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        ModalDialog.showModal(this, new SimpleModalBorder(
                inputProduct(), "Create", SimpleModalBorder.YES_NO_OPTION,
                (controller, action) -> {
                    if (action == SimpleModalBorder.YES_OPTION) {
                        postProduct();
                    }
                }), option);
    }
}