package com.example.nicomoccagatta.weatherapp.dummy;

import android.content.Context;

import com.example.nicomoccagatta.weatherapp.R;
import com.example.nicomoccagatta.weatherapp.util.JSONResourceReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class CountryListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Country> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
    public static void loadCountries(Context context) {
        if (!ITEMS.isEmpty())
            return;  // already filled

        JSONResourceReader reader = new JSONResourceReader(context.getResources(), R.raw.countries);

        Country[] countries = reader.constructUsingGson(Country[].class);

        for (Country c : countries) {
            ITEMS.add(c);
        }

        Collections.sort(ITEMS, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
    }
}
