package Order.Modal.utils.table;

import Order.Modal.utils.table.Action.PanelAction;
import Order.Modal.utils.table.Action.TableActionEvent;

import javax.swing.*;
import java.awt.*;

public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;
    private PanelAction action;

    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
        this.action = new PanelAction();
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
