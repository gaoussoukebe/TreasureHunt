package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Barometerservice {
    @GET("Barometer") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Barometer>> all();

    @GET("Barometer/{isbn}")
    Call<Barometer> get(@Path("isbn") String id);

    @POST("Barometer/new")
    Call<Barometer> create(@Body Barometer barometer);
}
