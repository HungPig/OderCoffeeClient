package Order.Modal.Api;
import Order.Modal.Entity.tables;
import Order.Modal.Response.tables.CreatedTableResponse;
import Order.Modal.Response.tables.DeleteTableResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface TableAPI {
    @GET("/api/table")
    Call<CreatedTableResponse> getAllOrder();
    @GET("/api/table/{id}")
    Call<CreatedTableResponse> getOrderId(@Path("id") String id);
    @POST("/api/table")
    Call<CreatedTableResponse> addOrder(@Body tables Order);
    @DELETE("/api/table/{id}")
    Call<DeleteTableResponse> deleteOrder(@Path("id") String id);
    @PATCH("/api/table/{id}")
    Call<CreatedTableResponse> updateOrder(@Path("id") String id, @Body tables Order);
}
