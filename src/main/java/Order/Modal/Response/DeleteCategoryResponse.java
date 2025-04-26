package Order.Modal.Response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@RequiredArgsConstructor
@Getter
@Setter
public class DeleteCategoryResponse {
    private boolean success;
    private String message;
}
