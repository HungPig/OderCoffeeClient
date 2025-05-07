package Order.Modal.Api;
import Order.Modal.Entity.tables;
import Order.Modal.Response.tables.CreatedTableResponse;
import Order.Modal.Response.tables.DeleteTableResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface TableAPI {
    @GET("/api/table")
    Call<CreatedTableResponse> getAllTabel();
    @GET("/api/table/{id}")
    Call<CreatedTableResponse> getTabelId(@Path("id") int id);
    @POST("/api/table")
    Call<CreatedTableResponse> addTable(@Body tables table);
    @DELETE("/api/table/{id}")
    Call<DeleteTableResponse> deleteTable(@Path("id") String id);
    @PATCH("/api/table/{id}")
    Call<CreatedTableResponse> updateTable(@Path("id") String id, @Body tables table);
}
