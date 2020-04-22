package com.example.myprayer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrayerApi {

    @GET("timingsByCity?")
   Call<Response> getTimings(@Query("city") String city,@Query("country")String country,@Query("method")int method);
}
