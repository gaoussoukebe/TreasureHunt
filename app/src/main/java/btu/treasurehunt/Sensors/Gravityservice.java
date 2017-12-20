package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Gravityservice {
    @GET("Gravity") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Gravity>> all();

    @GET("Gravity/{isbn}")
    Call<Gravity> get(@Path("isbn") String id);

    @POST("Gravity/new")
    Call<Gravity> create(@Body Gravity gravity);





}
