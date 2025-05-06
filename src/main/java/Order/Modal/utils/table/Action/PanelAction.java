package Order.Modal.utils.table.Action;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelAction extends JPanel {
    private ButtonAction cmdEdit;
    private ButtonAction cmdDelete;

    public PanelAction() {
        setLayout(new MigLayout("insets 0, gap 10, align left", "[]10[]", "center"));
        initComponents();
    }

    public void initEvent(TableActionEvent event, int row) {
        cmdEdit.addActionListener(e -> event.onEdit());
        cmdDelete.addActionListener(e -> event.onDelete());
    }

    private void initComponents() {
        cmdEdit = new ButtonAction();
        cmdEdit.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Order/images/edit.png"))));
        cmdDelete = new ButtonAction();
        cmdDelete.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Order/images/delete.png"))));
        add(cmdEdit, "cell 0 0, height 65!");
        add(cmdDelete, "cell 0 0, height 65!");
    }
}
