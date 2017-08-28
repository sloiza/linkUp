package com.example.nicomoccagatta.weatherapp.Models;

import android.content.Intent;

/**
 * Created by alejandro on 8/26/17.
 */

public class Weather {

    private String city;
    private String weatherCondition;
    private String temperature;
    private String pressure;
    private String country;
    private Boolean isNight;
    private Integer imageCond;

    public Weather(String city, String weatherCondition, String temperature, String pressure, String country, Boolean isNight, Integer imageCond) {
        this.city = city;
        this.weatherCondition = weatherCondition;
        this.temperature = temperature;
        this.pressure = pressure;
        this.country = country;
        this.isNight = isNight;
        this.imageCond = imageCond;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getNight() {
        return isNight;
    }

    public void setNight(Boolean night) {
        isNight = night;
    }

    public Integer getImageCond() {
        return imageCond;
    }

    public void setImageCond(Integer imageCond) {
        this.imageCond = imageCond;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", temperature='" + temperature + '\'' +
                ", pressure='" + pressure + '\'' +
                ", country='" + country + '\'' +
                ", isNight=" + isNight +
                ", imageCond=" + imageCond +
                '}';
    }
}
