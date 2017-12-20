package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import btu.treasurehunt.R;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Rotationservice {
    @GET("Rotation") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Rotation>> all();

    @GET("Rotation/{isbn}")
    Call<Rotation> get(@Path("isbn") String id);

    @POST("Rotation/new")
    Call<Rotation> create(@Body Rotation rotation);





}
