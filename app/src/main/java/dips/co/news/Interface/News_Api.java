package dips.co.news.Interface;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Url;


/**
 * Created by Dipesh on 03-Jan-17.
 */

public interface News_Api {

//    @FormUrlEncoded
//@Field("user_id") String user_id,
    @GET("sources??language=en")
    Call<JsonObject> sources();
//    @Path("en") String language

    @GET("articles?&apiKey=f1212f05bf0747bcb74e4cce276c0b24&sortBy=top")
    Call<JsonObject> articles(@Query("source") String source);

//    @GET("articles?&apiKey=f1212f05bf0747bcb74e4cce276c0b24&sortBy=latest")
//    Call<JsonObject> article(@Query("source") String source);
}
