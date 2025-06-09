package Order.Modal.Entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
@Data
public class products implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private int price;
    @SerializedName("image")
    private String image;
    @SerializedName("status")
    private int status;
    @SerializedName("category_id")
    private int category_id;
    @Override
    public String toString() {
        return name;
    }
}
