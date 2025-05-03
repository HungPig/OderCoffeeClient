package Order.Modal.Api;

import Order.Modal.Entity.categories;
import Order.Modal.Entity.products;
import Order.Modal.Response.ApiResponse;
import Order.Modal.Response.category.DeleteCategoryResponse;
import Order.Modal.Response.products.CreatedProductReponse;
import Order.Modal.Response.products.DeleteProductResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ProductAPI {
    @GET("api/products")
    Call<ApiResponse<List<products>>> getAllProducts();
    @GET("/api/products/{id}")
    Call<CreatedProductReponse> getProductId(@Path("id") String id);
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
    @Multipart
    @PATCH("/api/products/{id}")
    Call<CreatedProductReponse> updateProduct(
            @Path("id") String id,
            @Part("name") RequestBody name,
            @Part("description") RequestBody description,
            @Part("price") RequestBody price,
            @Part("status") RequestBody status,
            @Part("category_id") RequestBody categoryId,
            @Part MultipartBody.Part image
    );
    @DELETE("/api/products/{id}")
    Call<DeleteProductResponse> deleteProduct(@Path("id") String id);
    @GET("api/products/search")
    Call<ApiResponse<List<products>>> getProductBySearch(@Query("keyword") String keyword);
}
