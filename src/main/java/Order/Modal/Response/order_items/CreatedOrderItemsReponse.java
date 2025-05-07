package Order.Modal.Response.order_items;

import Order.Modal.Entity.orders_items;

public class CreatedOrderItemsReponse {
    private String message;
    private boolean success;
    private orders_items data;

    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    public orders_items getData() { return data; }
}
