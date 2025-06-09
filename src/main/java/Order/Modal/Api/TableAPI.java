package Order.Modal.Api;
import Order.Modal.Entity.tables;
import Order.Modal.Response.tables.TableResponse;
import Order.Modal.Response.tables.DeleteTableResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface TableAPI {
    @GET("/api/table")
    Call<TableResponse> getAllTabel();
    @GET("/api/table/{id}")
    Call<TableResponse> getTabelId(@Path("id") int id);
    @POST("/api/table")
    Call<TableResponse> addTable(@Body tables table);
    @DELETE("/api/table/{id}")
    Call<DeleteTableResponse> deleteTable(@Path("id") String id);
    @PATCH("/api/table/{id}")
    Call<TableResponse> updateTable(@Path("id") String id, @Body tables table);
}
