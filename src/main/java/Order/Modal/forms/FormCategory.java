package Order.Modal.forms;

import Order.Modal.System.Form;
import Order.Modal.model.ModelEmployee;
import Order.Modal.sample.SampleData;
import Order.Modal.utils.SystemForm;
import Order.Modal.utils.table.Action.TableActionEvent;
import Order.Modal.utils.table.TableActionCellEditor;
import Order.Modal.utils.table.TableActionCellRender;
import Order.Modal.utils.table.TableHeaderAlignment;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SystemForm(name = "Category", description = "Category form display some details")
public class FormCategory extends Form {
    private JTable table;
    private DefaultTableModel model;

    public FormCategory() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap,insets 10 0 10 0", "[fill]", "[]0[fill,grow]"));
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

        // table scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // table option
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
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
        putClientProperty(FlatClientProperties.STYLE, "arc:20;" +
                "background:$Table.background;");
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "height:65;" +
                "hoverBackground:null;" +
                "pressedBackground:null;" +
                "separatorColor:$TableHeader.background;");
        table.putClientProperty(FlatClientProperties.STYLE, "rowHeight:65;" +
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
        add(title, "gapx 20");
        add(scrollPane);

        // sample data
        for (ModelEmployee d : SampleData.getSampleBasicEmployeeData()) {
            model.addRow(d.toTableRowBasic(table.getRowCount() + 1));
        }
    }
}
