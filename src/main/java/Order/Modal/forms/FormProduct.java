package Order.Modal.forms;

import Order.Modal.System.Form;
import Order.Modal.model.ModelEmployee;
import Order.Modal.model.ModelProfile;
import Order.Modal.sample.SampleData;
import Order.Modal.simple.SimpleInputForms;
import Order.Modal.utils.SystemForm;
import Order.Modal.utils.table.CheckBoxTableHeaderRenderer;
import Order.Modal.utils.table.TableHeaderAlignment;
import Order.Modal.utils.table.TableProfileCellRenderer;
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
    private JPanel panel;
    private JComboBox comboCategory;
    private JTextField txtFieldID;
    private JTextField txtFieldPrice;
    private JTextField txtFieldDescription;
    private JComboBox comboStatus;
    public FormProduct() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[][]0[fill,grow]"));
        // create table model
        Object columns[] = new Object[]{"SELECT", "#", "NAME", "PRICE", "STATUS","DESCRIPTION"};
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
        JTable table = new JTable(model);

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
        JButton cmdEdit = new JButton("Edit");
        JButton cmdDelete = new JButton("Delete");
        cmdCreate.addActionListener(e -> showModal());
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
        // Khởi tạo các trường
        if (txtFieldID == null) {
            txtFieldID = new JTextField();
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
        txtFieldID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID will be generated automatically");
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

        JPanel pCate = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbCategory = new JLabel("Category");
        lbCategory.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pCate.add(lbCategory);
        pCate.add(comboCategory);
        panel.add(pCate);

        JPanel pPrice = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbPrice = new JLabel("Price");
        lbPrice.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pPrice.add(lbPrice);
        pPrice.add(txtFieldPrice);
        panel.add(pPrice);

        // --- Status Field ---
        JPanel pStatus = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbStatus = new JLabel("Status");
        lbStatus.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pStatus.add(lbStatus);
        pStatus.add(comboStatus);
        panel.add(pStatus);

        // --- Description Field ---
        JPanel pDescription = new JPanel(new MigLayout("fillx,wrap", "[fill]", ""));
        JLabel lbDescription = new JLabel("Description");
        lbDescription.putClientProperty(FlatClientProperties.STYLE, "font:+1");
        pDescription.add(lbDescription);
        pDescription.add(txtFieldDescription);
        panel.add(pDescription);

        return panel;
    }

    private void showModal() {
        Option option = ModalDialog.createOption();
        option.getLayoutOption().setSize(-1, 1f)
                .setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        ModalDialog.showModal(this, new SimpleModalBorder(
                inputProduct(), "Create", SimpleModalBorder.YES_NO_OPTION,
                (controller, action) -> {

                }), option);
    }
}