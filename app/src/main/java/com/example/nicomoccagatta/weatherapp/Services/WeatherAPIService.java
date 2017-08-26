package com.example.nicomoccagatta.weatherapp.Services;

import com.example.nicomoccagatta.weatherapp.Models.ServerResponse;
import com.example.nicomoccagatta.weatherapp.Models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by alejandro on 8/26/17.
 */

public interface WeatherAPIService {

    @GET("/api/weather/{cityId}")
    Call<ServerResponse<Weather>> getWeather(@Path("cityId") String cityId);
}
