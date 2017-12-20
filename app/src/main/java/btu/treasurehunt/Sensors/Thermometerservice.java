package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Thermometerservice {
    @GET("Thermometer") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Thermometer>> all();

    @GET("Thermometer/{isbn}")
    Call<Thermometer> get(@Path("isbn") String id);

    @POST("Thermometer/new")
    Call<Thermometer> create(@Body Thermometer thermometer);





}
