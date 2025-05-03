package Order.Modal.Response.category;

import Order.Modal.Entity.categories;

import java.util.List;

public class CategoryResponse {
    private String message;
    private boolean success;
    private List<categories> data;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<categories> getData() {
        return data;
    }
}
