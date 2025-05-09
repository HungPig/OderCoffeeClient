package Order.Modal.Api;

import Order.Modal.Entity.orders;
import Order.Modal.Entity.orders_items;
import Order.Modal.Response.order_items.CreatedOrderItemsReponse;
import Order.Modal.Response.order_items.OrderItemResponse;
import Order.Modal.Response.orders.CreatedOrderResponse;
import Order.Modal.Response.orders.DeleteOrderResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface OrderItemsAPI {
    @GET("/api/orderItem")
    Call<CreatedOrderItemsReponse> getAllOrderItem();
    @GET("/api/orderItem/{id}")
    Call<OrderItemResponse> getOrderItemsId(@Path("id") String id);
    @POST("/api/orderItem")
    Call<CreatedOrderItemsReponse> addOrderItems(@Body orders_items orders_item);
    @DELETE("/api/orderItem/{id}")
    Call<CreatedOrderItemsReponse> deleteOrderItems(@Path("id") String id);
    @PATCH("/api/orderItem/{id}")
    Call<CreatedOrderItemsReponse> updateOrderItems(@Path("id") String id, @Body orders_items orders_item);
}
