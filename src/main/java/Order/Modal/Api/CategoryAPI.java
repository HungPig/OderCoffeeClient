package Order.Modal.Api;

import Order.Modal.Entity.categories;
import Order.Modal.Response.CategoryResponse;
import Order.Modal.Response.CreateCategoryResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CategoryAPI {
    @GET("/api/category")
    Call<CategoryResponse> getAllCategories();
    @POST("/api/category")
    Call<CreateCategoryResponse> addCategory(@Body categories category);
    @DELETE("/api/category/{id}")
    Call<CategoryResponse> deleteCategory(@Path("id") int id);
}
