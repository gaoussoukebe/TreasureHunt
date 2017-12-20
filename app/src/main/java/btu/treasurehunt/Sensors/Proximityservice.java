package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Proximityservice {
    @GET("Proximity") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Proximity>> all();

    @GET("Proximity/{isbn}")
    Call<Proximity> get(@Path("isbn") String id);

    @POST("Proximity/new")
    Call<Proximity> create(@Body Proximity proximity);





}
