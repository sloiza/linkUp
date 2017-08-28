package com.example.nicomoccagatta.weatherapp;

/**
 * Created by alejandro on 8/26/17.
 */

public class Globals {

//    public static String serverAddress = "http://192.168.0.125:8080";
    private static String serverAddress = "https://weather-server-tdp.herokuapp.com";

    public static String getServerAddress() {
        return serverAddress;
    }
}
