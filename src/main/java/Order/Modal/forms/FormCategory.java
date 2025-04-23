package Order.Modal.forms;

import Order.Modal.Api.APIClient;
import Order.Modal.Api.CategoryAPI;
import Order.Modal.Entity.categories;
import Order.Modal.Response.CategoryResponse;
import Order.Modal.System.Form;
import Order.Modal.model.ModelEmployee;
import Order.Modal.sample.SampleData;
import Order.Modal.simple.SimpleInputForms;
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
import java.util.List;

@SystemForm(name = "Category", description = "Category form display some details")
public class FormCategory extends Form {
    private JTable table;
    private DefaultTableModel model;

    public FormCategory() {
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

    @Override
    public void formRefresh()
    {
        loadData();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 20 0 10 0", "[fill]", "[]20[]20[fill,grow]"));


        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit() {
//                showModal();
            }

            @Override
            public void onDelete(int row) {
                System.out.println("Delete " + row);
            }
        };

        // create table model
        Object columns[] = new Object[]{"#", "NAME", "ACTION"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        // create table
        table = new JTable(model);
        //table header

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // table option
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(2).setCellEditor(new TableActionCellEditor(event));

        // alignment table header
        table.getTableHeader().setDefaultRenderer(new TableHeaderAlignment(table) {
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
        JLabel title = new JLabel("Category table");
        title.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2");
        add(title, "gapx 20, wrap");
        add(createHeaderAction(), "wrap");
        add(scrollPane);

        // sample data
//        for (ModelEmployee d : SampleData.getSampleBasicEmployeeData()) {
//            model.addRow(d.toTableRowBasic(table.getRowCount() + 1));
//        }
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
                new SimpleInputForms(), "Create", SimpleModalBorder.YES_NO_OPTION,
                (controller, action) -> {

                }), option);
    }
}
