package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Gyroscopeservice {
    @GET("Gyroscope") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Gyroscope>> all();

    @GET("Gyroscope/{isbn}")
    Call<Gyroscope> get(@Path("isbn") String id);

    @POST("Gyroscope/new")
    Call<Gyroscope> create(@Body Gyroscope gyroscope);





}
