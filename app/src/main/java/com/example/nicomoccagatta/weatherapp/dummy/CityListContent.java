package com.example.nicomoccagatta.weatherapp.dummy;

import android.content.Context;
import android.util.Log;

import com.example.nicomoccagatta.weatherapp.R;
import com.example.nicomoccagatta.weatherapp.util.JSONResourceReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by alejandro on 8/27/17.
 */

public class CityListContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<City> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static void loadCities(Context context, String countryID) {
        ITEMS.clear();  // removes all elements from arraylist

        Log.i("RESOURCE", "country_" + countryID.toLowerCase());
        JSONResourceReader reader = new JSONResourceReader(context.getResources(), context.getResources().getIdentifier("country_" + countryID.toLowerCase(),
                "raw", context.getPackageName()));

        City[] cities = reader.constructUsingGson(City[].class);

        for (City c: cities) {
            ITEMS.add(c);
        }

        Collections.sort(ITEMS, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                return c1.getN().compareTo(c2.getN());
            }
        });
    }
}
