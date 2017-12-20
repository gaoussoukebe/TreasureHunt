package btu.treasurehunt.Sensors;

import java.util.List;

import btu.treasurehunt.Account;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface sensorBatchservice {
    @GET("sensorBatch") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<Account>> all();

    @GET("sensorBatch/{isbn}")
    Call<Account> get(@Path("isbn") String id);

    @POST("sensorBatch/new")
    Call<sensorBatch> create(@Body sensorBatch sensorBatch);





}
