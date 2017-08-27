package com.example.nicomoccagatta.weatherapp.dummy;

/**
 * Created by alejandro on 8/27/17.
 */

public class City {
    String n;
    Integer id;

    public City(String n, Integer id) {
        this.n = n;
        this.id = id;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
