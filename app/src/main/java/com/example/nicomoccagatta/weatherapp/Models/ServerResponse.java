package com.example.nicomoccagatta.weatherapp.Models;

/**
 * Created by alejandro on 8/26/17.
 */

public class ServerResponse<T> {

    public T data;
    public String statusCode;

    public ServerResponse(T data, String statusCode) {
        this.data = data;
        this.statusCode = statusCode;
    }
}
