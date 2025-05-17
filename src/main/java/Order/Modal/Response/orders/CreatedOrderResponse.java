package Order.Modal.Response.orders;

import Order.Modal.Entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatedOrderResponse {
    private String message;
    private boolean success;
    private orders data;
    private List<orders_items> items;
    private List<products> productsList;
}
