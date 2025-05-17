package Order.Modal.Response.order_items;

import Order.Modal.Entity.orders_items;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CreatedOrderItemsReponse {
    private String message;
    private boolean success;
    private List<orders_items> data;
}
