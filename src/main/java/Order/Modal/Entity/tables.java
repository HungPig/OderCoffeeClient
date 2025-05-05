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
public class tables implements Serializable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("status")
    private String Status;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
}
