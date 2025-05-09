package Order.Modal.Response.order_items;

import Order.Modal.Entity.orders_items;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse {
    private String message;
    private boolean success;
    private orders_items data;

}
