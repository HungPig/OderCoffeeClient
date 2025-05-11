package Order.Modal.Entity;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@Setter
public class orders implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("table_id")
    private int table_id;
    @SerializedName("status")
    private String status;
    @SerializedName("total_amount")
    private Integer total_amount;
    @SerializedName("createdAt")
    private String created_at;
    @SerializedName("items")
    private Integer items;
    @SerializedName("updatedAt")
    private String updated_at;
}
