package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Magnetometerservice {
    @GET("Magnetometer") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Magnetometer>> all();

    @GET("Magnetometer/{isbn}")
    Call<Magnetometer> get(@Path("isbn") String id);

    @POST("Magnetometer/new")
    Call<Magnetometer> create(@Body Magnetometer magnetometer);





}
