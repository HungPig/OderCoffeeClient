package Order.Modal.Response.order_items;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DeleteOrderItemsResponse {
    private boolean success;
    private String message;
}
