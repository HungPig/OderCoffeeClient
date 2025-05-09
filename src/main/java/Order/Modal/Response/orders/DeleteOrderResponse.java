package Order.Modal.Response.orders;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DeleteOrderResponse {
    private boolean success;
    private String message;
}
