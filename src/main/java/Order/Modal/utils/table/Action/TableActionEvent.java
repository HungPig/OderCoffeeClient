package Order.Modal.utils.table.Action;

public interface TableActionEvent {
    void onEdit(int row);
    void onDelete(int row);
}
