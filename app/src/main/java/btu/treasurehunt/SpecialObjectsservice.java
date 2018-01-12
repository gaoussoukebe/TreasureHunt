package btu.treasurehunt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SpecialObjectsservice {
    @GET("SpecialObjects") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<SpecialObjects>> all();

    @GET("SpecialObjects/{isbn}")
    Call<SpecialObjects> get(@Path("isbn") String id);

    @POST("SpecialObjects/new")
    Call<SpecialObjects> create(@Body SpecialObjects specialObjects);
    @GET("SpecialObjects/check/{id}")
    Call<SpecialObjects> check(@Path("id") long id);
    @GET("SpecialObjects/checkall")
    Call<SpecialObjects> checkall();





}
