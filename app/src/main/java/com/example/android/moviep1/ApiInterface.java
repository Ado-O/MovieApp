package com.example.android.moviep1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 7.12.2017.
 */

public interface ApiInterface {
    @GET("3/movie/{category}")
    Call<MovieResult> getMovies(
            @Path("category")String category,
            @Query("api_key")String apiKey,
            @Query("page")int page
            );
}
