package Order.Modal.Api;

import Order.Modal.Entity.categories;
import Order.Modal.Response.CategoryResponse;
import Order.Modal.Response.CreateCategoryResponse;
import Order.Modal.Response.DeleteCategoryResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CategoryAPI {
    @GET("/api/category")
    Call<CategoryResponse> getAllCategories();
    @GET("/api/category/{id}")
    Call<CreateCategoryResponse> getCategoryId(@Path("id") String id);
    @POST("/api/category")
    Call<CreateCategoryResponse> addCategory(@Body categories category);
    @DELETE("/api/category/{id}")
    Call<DeleteCategoryResponse> deleteCategory(@Path("id") String id);
    @PATCH("/api/category/{id}")
    Call<CreateCategoryResponse> updateCategory(@Path("id") String id, @Body categories category);

}
