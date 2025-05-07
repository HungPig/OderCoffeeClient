package Order.Modal.Response.orders;

import Order.Modal.Entity.orders;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderResponse {
    private String message;
    private boolean success;
    private List<orders> data;
}
