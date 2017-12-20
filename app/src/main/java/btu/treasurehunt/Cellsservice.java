package btu.treasurehunt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Cellsservice {
    @GET("cells") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Cells>> all();

    @GET("cells/{isbn}")
    Call<Cells> get(@Path("isbn") String id);

    @POST("cells/new")
    Call<Cells> create(@Body Cells cells);





}
