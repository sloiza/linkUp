package com.example.nicomoccagatta.weatherapp.Services;

import com.example.nicomoccagatta.weatherapp.Globals;
import com.example.nicomoccagatta.weatherapp.Models.ServerResponse;
import com.example.nicomoccagatta.weatherapp.Models.Weather;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(Globals.getServerAddress())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPIService.class);
    }

    public void getWeather(String cityId, final Callback<ServerResponse<Weather>> callback) {
        api.getWeather(cityId).enqueue(new Callback<ServerResponse<Weather>>() {
            @Override
            public void onResponse(Call<ServerResponse<Weather>> call, Response<ServerResponse<Weather>> response) {
                Weather serverResponse = response.body().data;
                if (serverResponse != null) {
                    callback.onResponse(call, Response.success(response.body()));
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<Weather>> call, Throwable t) {
                callback.onFailure(call,t);
            }
        });
    }
}
