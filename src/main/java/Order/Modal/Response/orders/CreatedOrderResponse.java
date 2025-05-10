package Order.Modal.Response.orders;

import Order.Modal.Entity.categories;
import Order.Modal.Entity.orders;
import Order.Modal.Entity.tables;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatedOrderResponse {
    private String message;
    private boolean success;
    private orders data;
}
