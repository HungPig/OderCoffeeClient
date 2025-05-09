package Order.Modal.Entity;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("items")
    private Integer items;
    @SerializedName("updated_at")
    private Date updatedAt;
}
