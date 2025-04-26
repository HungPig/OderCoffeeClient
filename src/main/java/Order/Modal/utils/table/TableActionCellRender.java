package Order.Modal.utils.table;

import Order.Modal.utils.table.Action.PanelAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableActionCellRender extends DefaultTableCellRenderer {

    private PanelAction action;

    public TableActionCellRender() {
        this.action = new PanelAction();  // Tạo một lần duy nhất
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        if (isSeleted) {
            action.setBackground(jtable.getSelectionBackground());
        } else {
            action.setBackground(jtable.getBackground());
        }
        return action;
    }
}

