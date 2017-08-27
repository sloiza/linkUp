package com.example.nicomoccagatta.weatherapp.dummy;

/**
 * Created by alejandro on 8/27/17.
 */

public class Country {
    String code;
    String name;

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ", " + code;
    }
}
