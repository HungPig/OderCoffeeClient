package Order.Modal.Response.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteProductResponse {
    private boolean success;
    private String message;
}
