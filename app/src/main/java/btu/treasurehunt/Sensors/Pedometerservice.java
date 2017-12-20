package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Pedometerservice {
    @GET("Pedometer") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Pedometer>> all();

    @GET("Pedometer/{isbn}")
    Call<Pedometer> get(@Path("isbn") String id);

    @POST("Pedometer/new")
    Call<Pedometer> create(@Body Pedometer pedometer);





}
