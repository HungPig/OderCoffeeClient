package Order.Modal.forms;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.CategoryAPI;
import Order.Modal.Entity.categories;
import Order.Modal.Response.CategoryResponse;
import Order.Modal.Response.CreateCategoryResponse;
import Order.Modal.Response.DeleteCategoryResponse;
import Order.Modal.System.Form;
import Order.Modal.model.ModelEmployee;
import Order.Modal.model.ModelProfile;
import Order.Modal.sample.SampleData;
import Order.Modal.simple.SimpleInputForms;
import Order.Modal.utils.SystemForm;
import Order.Modal.utils.table.*;
import Order.Modal.utils.table.Action.TableActionEvent;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.NotNull;
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

@SystemForm(name = "Category", description = "Category form display some details")
public class FormCategory extends Form {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtFieldID;
    private JTextField txtFieldCategoryName;
    public FormCategory() {
        txtFieldID = new JTextField();
        txtFieldCategoryName = new JTextField();
        init();
        loadData();
    }
    public void loadData() {
        try {
            CategoryAPI categoryAPI = APIClient.getClient().create(CategoryAPI.class);
            categoryAPI.getAllCategories().enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                    if (response.isSuccessful()) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setRowCount(0);
                        assert response.body() != null;
                        for (categories category : response.body().getData()) {
                            model.addRow(new Object[]{
                                    false,
                                    category.getId(),
                                    category.getName()
                            });
                        }
                        table.setModel(model);
                    }
                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                    JOptionPane.showMessageDialog(FormCategory.this,
                            "Lỗi kết nối API: " + throwable.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Component inputCate()
    {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
        if (txtFieldID == null) txtFieldID = new JTextField();
        if (txtFieldCategoryName == null) txtFieldCategoryName = new JTextField();
        txtFieldID.setEditable(false);
        // style
        txtFieldCategoryName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g.Coffee");
        txtFieldID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID will be generated automatically");
        // Create title
        JLabel lb = new JLabel("Please complete the following categories to add data !");
        lb.putClientProperty(FlatClientProperties.STYLE, "" + "font:+2");
        panel.add(lb, "gapy 5 0");
        panel.add(new JSeparator(), "height 2!,gapy 0 0");
        // Add ID field
        panel.add(new JLabel("ID"), "gapy 5 0");
        panel.add(txtFieldID);
        // Add Category name field
        panel.add(new JLabel("Category name"), "gapy 5 0");
        panel.add(txtFieldCategoryName);
        return panel;
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));
        DefaultTableModel model = getDefaultTableModel();
        table = new JTable(model);
        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // table option
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        // disable reordering table column
        table.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer
//        table.setDefaultRenderer(ModelProfile.class, new TableProfileCellRenderer(table));
        table.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 0) {
                int changedRow = e.getFirstRow();
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (i != changedRow && (Boolean) table.getValueAt(i, 0)) {
                        table.setValueAt(false, i, 0); // bỏ chọn các hàng khác
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
        add(createHeaderAction());
        add(scrollPane);
    }

    @NotNull
    private static DefaultTableModel getDefaultTableModel() {
        Object columns[] = new Object[]{"SELECT", "ID", "NAME",};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // use boolean type at column 0 for checkbox
                if (columnIndex == 0)
                    return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
        };
        return model;
    }

    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Order/icons/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Create");
        JButton cmdEdit = new JButton("Edit");
        JButton cmdDelete = new JButton("Delete");

        cmdCreate.addActionListener(e -> Create());
        cmdEdit.addActionListener(e -> Edit());
        cmdDelete.addActionListener(e -> Delete());
        panel.add(txtSearch);
        panel.add(cmdCreate);
        panel.add(cmdEdit);
        panel.add(cmdDelete);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }

    private void Delete()
    {
        int selectedRow = -1;
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isChecked = (Boolean) table.getValueAt(i, 0);
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
        String categoryId = table.getValueAt(selectedRow, 1).toString();
        if (result == JOptionPane.YES_OPTION) {
            CategoryAPI categoryAPI = APIClient.getClient().create(CategoryAPI.class);
            categoryAPI.deleteCategory(categoryId).enqueue(new Callback<DeleteCategoryResponse>() {
                @Override
                public void onResponse(Call<DeleteCategoryResponse> call, Response<DeleteCategoryResponse> response) {
                    if(response.isSuccessful())
                    {
                        loadData();
                    }
                }

                @Override
                public void onFailure(Call<DeleteCategoryResponse> call, Throwable throwable) {
                    JOptionPane.showMessageDialog(null, throwable.getMessage());
                }
            });
        }
    }

    private void Edit() {
        int selectedRow = -1;
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isChecked = (Boolean) table.getValueAt(i, 0);
            if (isChecked != null && isChecked) {
                selectedRow = i;
                break;
            }
        }
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a category to edit.");
            return;
        }
        String categoryId = table.getValueAt(selectedRow, 1).toString();
        CategoryAPI categoryAPI = APIClient.getClient().create(CategoryAPI.class);
        categoryAPI.getCategoryId(categoryId).enqueue(new Callback<CreateCategoryResponse>() {
            @Override
            public void onResponse(Call<CreateCategoryResponse> call, Response<CreateCategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories category = response.body().getData();
                    txtFieldID.setText(category.getId());
                    txtFieldCategoryName.setText(category.getName());
                    Option option = ModalDialog.createOption();
                    option.getLayoutOption().setSize(-1, 1f)
                            .setLocation(Location.TRAILING, Location.TOP)
                            .setAnimateDistance(0.7f, 0);
                    ModalDialog.showModal(FormCategory.this, new SimpleModalBorder(
                            inputCate(), "Edit Category", SimpleModalBorder.YES_NO_OPTION,
                            (controller, action) -> {
                                if (action == SimpleModalBorder.YES_OPTION) {
                                    category.setName(txtFieldCategoryName.getText());
                                    categoryAPI.updateCategory(category.getId(), category).enqueue(new Callback<CreateCategoryResponse>() {
                                        @Override
                                        public void onResponse(Call<CreateCategoryResponse> call, Response<CreateCategoryResponse> response) {
                                            if (response.isSuccessful()) {
                                                JOptionPane.showMessageDialog(null, "Category updated successfully.");
                                                loadData();
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Update failed.");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CreateCategoryResponse> call, Throwable t) {
                                            JOptionPane.showMessageDialog(null, "Error: " + t.getMessage());
                                        }
                                    });
                                }
                            }
                    ), option);
                } else {
                    JOptionPane.showMessageDialog(null, "Category not found.");
                }
            }

            @Override
            public void onFailure(Call<CreateCategoryResponse> call, Throwable t) {
                JOptionPane.showMessageDialog(null, "Error: " + t.getMessage());
            }
        });
    }

    private void Create() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-1, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        ModalDialog.showModal(this, new SimpleModalBorder(
                inputCate(), "Create Category", SimpleModalBorder.YES_NO_OPTION,

                (controller, action) -> {
                    if(action == SimpleModalBorder.YES_OPTION) {
                        categories category = new categories();
                        category.setId(txtFieldID.getText());
                        category.setName(txtFieldCategoryName.getText());
                        CategoryAPI categoryAPI = APIClient.getClient().create(CategoryAPI.class);
                        categoryAPI.addCategory(category).enqueue(new Callback<CreateCategoryResponse>() {
                            @Override
                            public void onResponse(Call<CreateCategoryResponse> call, Response<CreateCategoryResponse> response) {
                                if(response.isSuccessful()) {
                                    JOptionPane.showMessageDialog(null, "Category created successfully");
                                    txtFieldID.setText("");
                                    txtFieldCategoryName.setText("");
                                    loadData();
                                }
                            }
                            @Override
                            public void onFailure(Call<CreateCategoryResponse> call, Throwable throwable) {
                                JOptionPane.showMessageDialog(null, "Error: " + throwable.getMessage());
                            }
                        });
                    }
                }), option);
    }
}
