package Order.Modal.Response.products;

import Order.Modal.Entity.products;

public class CreatedProductReponse {
    private String message;
    private boolean success;
    private products data;

    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    public products getData() { return data; }
}
