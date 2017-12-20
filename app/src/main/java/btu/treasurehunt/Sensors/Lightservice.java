package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Lightservice {
    @GET("Light") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Light>> all();

    @GET("Light/{isbn}")
    Call<Light> get(@Path("isbn") String id);

    @POST("Light/new")
    Call<Light> create(@Body Light light);





}
