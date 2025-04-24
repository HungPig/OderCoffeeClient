package Order.Modal.forms;

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
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import raven.modal.option.Location;
import raven.modal.option.Option;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@SystemForm(name = "Product", description = "Product table with advanced features", tags = {"list", "table"})
public class FormProduct extends Form {
    private JTable table;
    private DefaultTableModel model;
    public FormProduct() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));
        // create table model
        Object columns[] = new Object[]{
                "Hung", // 0
                "#", // 1
                "NAME",
                "STATUS",
                "PRICE",
                "DESCRIPTION",
                "ACTION"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // only action column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // use boolean type at column 0 for checkbox
                if (columnIndex == 2) {
                    return ModelProfile.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };



        // create table
        JTable table = new JTable(model);

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit() {
                showModal();
            }

            @Override
            public void onDelete(int row) {
                System.out.println("Delete " + row);
            }
        };
        // table option
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setMinWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(250);
        table.getColumnModel().getColumn(6).setMaxWidth(200);
        table.getColumnModel().getColumn(6).setResizable(false);
        table.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        // disable reordering table column
        table.getTableHeader().setReorderingAllowed(false);

        // apply profile cell renderer
        table.setDefaultRenderer(ModelProfile.class, new TableProfileCellRenderer(table));

        // apply checkbox custom to table header
        //table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));

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

        // sample data
        for (ModelEmployee d : SampleData.getSampleEmployeeData(false)) {
            model.addRow(d.toTableRowCustom(table.getRowCount() + 1));
        }
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