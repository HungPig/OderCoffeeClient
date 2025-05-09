package Order.Modal.Response.order_items;

import Order.Modal.Entity.orders_items;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class CreatedOrderItemsReponse {
    private String message;
    private boolean success;
    private List<orders_items> data;
}
