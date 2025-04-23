package Order.Modal.Api;

import Order.Modal.Entity.categories;
import Order.Modal.Response.CategoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface CategoryAPI {
    @GET("/api/category")
    Call<CategoryResponse> getAllCategories();
}
