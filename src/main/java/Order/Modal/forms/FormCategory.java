package Order.Modal.forms;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.CategoryAPI;
import Order.Modal.Entity.categories;
import Order.Modal.Response.CategoryResponse;
import Order.Modal.Response.CreateCategoryResponse;
import Order.Modal.Response.DeleteCategoryResponse;
import Order.Modal.System.Form;
import Order.Modal.model.ModelEmployee;
import Order.Modal.sample.SampleData;
import Order.Modal.simple.SimpleInputForms;
import Order.Modal.simple.inputFormCate;
import Order.Modal.utils.SystemForm;
import Order.Modal.utils.table.Action.TableActionEvent;
import Order.Modal.utils.table.TableActionCellEditor;
import Order.Modal.utils.table.TableActionCellRender;
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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SystemForm(name = "Category", description = "Category form display some details")
public class FormCategory extends Form {
    private JTable table;
    private DefaultTableModel model;
    JTextField txtFieldID = new JTextField();
    JTextField txtCategoryName = new JTextField();

    public FormCategory() {
        init();
        loadData();
    }

    private Component inputForm() {
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 5 30 5 30,width 400", "[fill]", ""));
        // Initialize text fields

        // Make ID field non-editable
        txtFieldID.setEditable(false);

        // style
        txtCategoryName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g.Coffee");
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
        panel.add(txtCategoryName);
        return panel;
    }


    public void loadData() {
        try {
            CategoryAPI categoryAPI = APIClient.getClient().create(CategoryAPI.class);
            categoryAPI.getAllCategories().enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                    if (response.isSuccessful()) {
                        model.setRowCount(0);
                        List<categories> categories = response.body().getData();
                        for (categories category : categories) {
                            model.addRow(new Object[]{
                                    category.getId(),
                                    category.getName()
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(FormCategory.this,
                                "Lỗi kết nối API: " + throwable.getMessage(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    });
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(FormCategory.this,
                    "Lỗi khởi tạo API: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void formRefresh() {
        loadData();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 20 0 10 0", "[fill]", "[]20[]20[fill,grow]"));
        //Hi
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit() {

            }


            @Override
            public void onDelete() {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int result = JOptionPane.showConfirmDialog(FormCategory.this,
                            "Bạn có chắc chắn muốn xóa danh mục này?",
                            "Xác nhận xóa",
                            JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            // Lấy ID từ cột đầu tiên (cột 0)
                            int id = (int) table.getValueAt(selectedRow, 0);

                            CategoryAPI categoryAPI = APIClient.getClient().create(CategoryAPI.class);
                            categoryAPI.deleteCategory(id).enqueue(new Callback<DeleteCategoryResponse>() {
                                @Override
                                public void onResponse(Call<DeleteCategoryResponse> call, Response<DeleteCategoryResponse> response) {
                                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                                        SwingUtilities.invokeLater(() -> {
                                            loadData();
                                            JOptionPane.showMessageDialog(FormCategory.this,
                                                    "Xóa danh mục thành công!");
                                        });
                                    } else {
                                        SwingUtilities.invokeLater(() -> {
                                            JOptionPane.showMessageDialog(FormCategory.this,
                                                    "Xóa thất bại: " + (response.body() != null ?
                                                            response.body().getMessage() : response.message()),
                                                    "Lỗi",
                                                    JOptionPane.ERROR_MESSAGE);
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<DeleteCategoryResponse> call, Throwable throwable) {
                                    SwingUtilities.invokeLater(() -> {
                                        JOptionPane.showMessageDialog(FormCategory.this,
                                                "Lỗi kết nối: " + throwable.getMessage(),
                                                "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                    });
                                }
                            });
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(FormCategory.this,
                                    "Có lỗi xảy ra: " + e.getMessage(),
                                    "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(FormCategory.this,
                            "Vui lòng chọn một danh mục để xóa",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

        // create table model
        Object columns[] = new Object[]{"#", "NAME", "ACTION"};
        model = new

                DefaultTableModel(columns, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 2;
                    }
                }

        ;

        // create table
        table = new JTable(model);
        //table header
        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // table option
        table.getColumnModel().
                getColumn(0).
                setMaxWidth(50);
        table.getColumnModel().
                getColumn(1).
                setPreferredWidth(100);
        table.getColumnModel().
                getColumn(2).
                setMaxWidth(200);
        table.getColumnModel().
                getColumn(2).
                setCellRenderer(new TableActionCellRender());
        table.getColumnModel().
                getColumn(2).
                setCellEditor(new TableActionCellEditor(event));

        // alignment table header
        table.getTableHeader().

                setDefaultRenderer(new TableHeaderAlignment(table) {
                    @Override
                    protected int getAlignment(int column) {
                        if (column == 0) {
                            return SwingConstants.CENTER;
                        }
                        return SwingConstants.LEADING;
                    }
                });

        // style
        putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "background:$Table.background;");
        table.getTableHeader().

                putClientProperty(FlatClientProperties.STYLE, "" +
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
        scrollPane.getVerticalScrollBar().

                putClientProperty(FlatClientProperties.STYLE, "" +
                        "trackArc:$ScrollBar.thumbArc;" +
                        "trackInsets:3,3,3,3;" +
                        "thumbInsets:3,3,3,3;" +
                        "background:$Table.background;");

        // create title
        JLabel title = new JLabel("Category table");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2");

        add(title, "gapx 20, wrap");

        add(createHeaderAction(), "wrap");

        add(scrollPane);
    }

    private Component createHeaderAction() {
        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("Order/icons/search.svg", 0.4f));
        JButton cmdCreate = new JButton("Create");

        cmdCreate.addActionListener(e -> showModal());
        panel.add(txtSearch);
        panel.add(cmdCreate);

        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null;");
        return panel;
    }

    private void showModal() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-1, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);


        ModalDialog.showModal(this, new SimpleModalBorder(
                inputForm(), "Add New Category", SimpleModalBorder.YES_NO_OPTION,
                (controller, action) ->  {
                    if (action == SimpleModalBorder.YES_OPTION) {
                        categories category = new categories();
//                        category.setName(input.getTxtCategoryName().getText());
                        CategoryAPI categoryAPI = APIClient.getClient().create(CategoryAPI.class);
                        categoryAPI.addCategory(category).enqueue(new Callback<CreateCategoryResponse>() {
                            @Override
                            public void onResponse(Call<CreateCategoryResponse> call, Response<CreateCategoryResponse> response) {
                                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                                    JOptionPane.showMessageDialog(FormCategory.this, "Tạo danh mục thành công!");
                                    loadData();
                                } else {
                                    JOptionPane.showMessageDialog(FormCategory.this, "Tạo thất bại: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<CreateCategoryResponse> call, Throwable throwable) {
                                JOptionPane.showMessageDialog(FormCategory.this, "Lỗi kết nối: " + throwable.getMessage());
                            }
                        });

                    }
                }), option);
    }

}
