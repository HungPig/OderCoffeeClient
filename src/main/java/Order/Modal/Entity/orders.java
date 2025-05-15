package Order.Modal.Entity;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class orders implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("table_id")
    private Integer table_id;
    @SerializedName("status")
    private String status;
    @SerializedName("total_amount")
    private Integer total_amount;
    @SerializedName("createdAt")
    private String created_at;
    @SerializedName("updatedAt")
    private String updated_at;
    @SerializedName("items")
    private List<orders_items> items;
    public int calculateTotalAmount() {
        int total = 0;
        if (items != null) {
            for (orders_items item : items) {
                total += item.getSubtotal(); // Lấy subtotal của từng item và cộng dồn
            }
        }
        return total;
    }
}
