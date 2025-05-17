package Order.Modal.Api;

import Order.Modal.Entity.orders_items;
import Order.Modal.Response.ApiResponse;
import Order.Modal.Response.order_items.CreatedOrderItemsReponse;
import Order.Modal.Response.order_items.OrderItemResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface OrderItemAPI {
    @GET("/api/orderItems")
    Call<CreatedOrderItemsReponse> getAllOrderItem();
    @GET("/api/orderItems/{id}")
    Call<OrderItemResponse> getOrderItemsId(@Path("id") String id);
    @POST("/api/orderItems")
    Call<ApiResponse<orders_items>> addOrderItems(@Body orders_items orders_item);
    @DELETE("/api/orderItems/{id}")
    Call<OrderItemResponse> deleteOrderItems(@Path("id") String id);
    @PATCH("/api/orderItems/{id}")
    Call<OrderItemResponse> updateOrderItems(@Path("id") String id, @Body orders_items orders_item);
}
