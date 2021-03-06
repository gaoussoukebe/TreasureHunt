package btu.treasurehunt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsermapCellsservice {
    @GET("usermapcells") /** Gets data from server app https://restfulap.herokuapp.com/accounts **/
    Call<List<UsermapCells>> all();

    @GET("usermapcells/{isbn}")
    Call<UsermapCells> get(@Path("isbn") long id);

    @POST("usermapcells/new")
    Call<UsermapCells> create(@Body UsermapCells usermapCells);
    @GET("usermapcells/dig/{id}/{cellid}")
    Call<UsermapCells> dig(@Path("id") long id, @Path("cellid") long cellid);
    @GET("usermapcells/user/{id}")
    Call<List<UsermapCells>> user(@Path("id") long id);





}
