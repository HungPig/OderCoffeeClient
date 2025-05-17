package Order.Modal.Api;

import Order.Modal.Entity.orders;
import Order.Modal.Response.ApiResponse;
import Order.Modal.Response.orders.CreatedOrderResponse;
import Order.Modal.Response.orders.DeleteOrderResponse;
import Order.Modal.Response.orders.OrderResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface OrderAPI {
    @GET("/api/order")
    Call<OrderResponse> getAllOrder();
    @GET("/api/order/{id}")
    Call<CreatedOrderResponse> getOrderId(@Path("id") String id);
    @POST("/api/order")
    Call<CreatedOrderResponse> addOrder(@Body orders Order);
    @DELETE("/api/order/{id}")
    Call<DeleteOrderResponse> deleteOrder(@Path("id") String id);
    @PATCH("/api/order/{id}")
    Call<CreatedOrderResponse> updateOrder(@Path("id") String id, @Body orders Order);
    @GET("/api/order/table/{tableId}")
    Call<ApiResponse<List<orders>>> getOrdersByTable(@Path("tableId") String tableId);
}
