package Order.Modal.utils.table.Action;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelAction extends JPanel {
    private ButtonAction cmdEdit;
    private ButtonAction cmdDelete;

    public PanelAction() {
        initComponents();
    }

    public void initEvent(TableActionEvent event, int row) {
        cmdEdit.addActionListener(e -> event.onEdit(row));
        cmdDelete.addActionListener(e -> event.onDelete(row));
    }

    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        cmdEdit = new ButtonAction();
        cmdEdit.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Order/images/edit.png"))));

        cmdDelete = new ButtonAction();
        cmdDelete.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Order/images/delete.png"))));
        add(cmdEdit);
        add(cmdDelete);
    }
}
