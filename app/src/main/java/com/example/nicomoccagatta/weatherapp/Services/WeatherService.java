package com.example.nicomoccagatta.weatherapp.Services;

import com.example.nicomoccagatta.weatherapp.Globals;
import com.example.nicomoccagatta.weatherapp.Models.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alejandro on 8/26/17.
 */

public class WeatherService {

    private WeatherAPIService api;

    public WeatherService() {
        this.api = getApi();
    }

    private WeatherAPIService getApi(){
        return new Retrofit.Builder()
                .baseUrl(Globals.getServerAddress())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPIService.class);
    }

    public void getWeather(String cityId, final Callback<Weather> callback) {
        api.getWeather(cityId).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather serverResponse = response.body();
                if (serverResponse != null) {
                    callback.onResponse(call, Response.success(serverResponse));
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                callback.onFailure(call,t);
            }
        });
    }
}
