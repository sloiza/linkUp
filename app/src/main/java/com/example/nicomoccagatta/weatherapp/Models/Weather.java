package com.example.nicomoccagatta.weatherapp.Models;

/**
 * Created by alejandro on 8/26/17.
 */

public class Weather {

    private String city;
    private String weatherCondition;
    private String temperature;
    private String pressure;

    public Weather(String city, String weatherCondition, String temperature, String pressure) {
        this.city = city;
        this.weatherCondition = weatherCondition;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}
