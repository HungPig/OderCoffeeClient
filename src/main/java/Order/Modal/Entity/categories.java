package Order.Modal.Entity;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@RequiredArgsConstructor
@Getter
@Setter
public class categories implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
}
