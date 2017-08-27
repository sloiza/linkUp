package com.example.nicomoccagatta.weatherapp.dummy;

/**
 * Created by alejandro on 8/27/17.
 */

public class City {
    String n;
    Integer id;
    String c;

    public City(String n, Integer id, String c) {
        this.n = n;
        this.id = id;
        this.c = c;
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

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}
