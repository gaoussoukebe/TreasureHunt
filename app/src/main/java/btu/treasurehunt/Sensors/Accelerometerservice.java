package btu.treasurehunt.Sensors;

import btu.treasurehunt.*;
import java.util.List;

import retrofit2.Call;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Accelerometerservice {
    @GET("Accelerometer") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Accelerometer>> all();

    @GET("Accelerometer/{id}")
    Call<Accelerometer> get(@Path("id") long id);

    @POST("Accelerometer/new")
    Call<Accelerometer> create(@Body Accelerometer accelerometer);





}
