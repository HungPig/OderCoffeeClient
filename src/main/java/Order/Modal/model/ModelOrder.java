package Order.Modal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class ModelOrder {
    private int id;
    private int tableId;
    private String status;
    private int total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String tableStatus;
    private String tableImage;

    public ModelOrder(int id, int tableId, String status, int total, LocalDateTime createdAt, LocalDateTime updatedAt, String tableStatus, String tableImage) {
        this.id = id;
        this.tableId = tableId;
        this.status = status;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tableStatus = tableStatus;
        this.tableImage = tableImage;
    }
}
