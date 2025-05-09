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
public class orders_items implements Serializable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("order_id")
    private String order_id;
    @SerializedName("product_id")
    private Integer product_id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("subtotal")
    private int subtotal;
    @SerializedName("status")
    private Integer status;
    @SerializedName("notes")
    private String notes;
    @SerializedName("createAt")
    private Date createdAt;


}
