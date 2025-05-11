package Order.Modal.Response.tables;

import Order.Modal.Entity.tables;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableResponse {
    private String message;
    private boolean success;
    private List<tables> data;

    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    public List<tables> getData() { return data; }
}
