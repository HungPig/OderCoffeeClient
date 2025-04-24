package Order.Modal.Response;

import Order.Modal.Entity.categories;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryResponse {
    private String message;
    private boolean success;
    private categories data;

    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    public categories getData() { return data; }
}
