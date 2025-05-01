package Order.Modal.Api;

import Order.Modal.Entity.products;
import Order.Modal.Response.ApiResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ProductAPI {
    @GET("api/products")
    Call<ApiResponse<List<products>>> getAllProducts();
    @Multipart
    @POST("/api/products")
    Call<ApiResponse> createProduct(
            @Part("name") RequestBody name,
            @Part("description") RequestBody description,
            @Part("price") RequestBody price,
            @Part("status") RequestBody status,
            @Part("category_id") RequestBody category_id,
            @Part MultipartBody.Part image
    );
}
