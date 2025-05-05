package Order.Modal.Response.tables;

import Order.Modal.Entity.tables;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreatedTableResponse {
    private String message;
    private boolean success;
    private tables data;

    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    public tables getData() { return data; }
}
