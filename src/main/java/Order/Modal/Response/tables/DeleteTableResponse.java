package Order.Modal.Response.tables;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DeleteTableResponse {
    private boolean success;
    private String message;
}
