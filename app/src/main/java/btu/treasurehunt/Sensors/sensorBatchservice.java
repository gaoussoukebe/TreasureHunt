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
    Call<List<sensorBatch>> all();

    @GET("sensorBatch/{id}")
    Call<sensorBatch> get(@Path("id") long id);

    @POST("sensorBatch/new")
    Call<sensorBatch> create(@Body sensorBatch sensorBatch);





}
