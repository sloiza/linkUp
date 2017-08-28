package com.example.nicomoccagatta.weatherapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.example.nicomoccagatta.weatherapp.Models.ServerResponse;
import com.example.nicomoccagatta.weatherapp.Models.Weather;
import com.example.nicomoccagatta.weatherapp.Services.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String CITY_ID_STATE_KEY = "CITY_ID_STATE_KEY";
    public static final String CITY_NAME_STATE_KEY = "CITY_NAME_STATE_KEY";

    private ProgressBar progressBar;
    private Handler handler = new Handler();  // for the progress bar

    private String currentCityId;
    private String currentCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLastState();

        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentCityName);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadData(currentCityId);
            }
        });
        reloadData(currentCityId);
        Log.i("MAIN_ACTIVITY", "onCreate");
    }

    private void loadLastState() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultCityIdValue = getResources().getString(R.string.city_id_default);
        currentCityId = sharedPref.getString(CITY_ID_STATE_KEY, defaultCityIdValue);

        String defaultCityNameValue = "No Data";
        currentCityName = sharedPref.getString(CITY_NAME_STATE_KEY, defaultCityNameValue);
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("STATE", "SAVING STATE");
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CITY_ID_STATE_KEY, currentCityId);
        editor.putString(CITY_NAME_STATE_KEY, currentCityName);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.select_city) {
            Intent intent = new Intent(this, CountryListActivity.class);
            startActivityForResult(intent, 27);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
            if (resultCode == RESULT_OK) {
                String cityId = data.getStringExtra(CityListActivity.MESSAGE_CITY_ID);
                Log.i("ID", cityId);
                reloadData(cityId);
            }
    }


    public void reloadData(final String cityId) {
        showProgressBar();
        new WeatherService().getWeather(cityId, new Callback<ServerResponse<Weather>>() {
            @Override
            public void onResponse(Call<ServerResponse<Weather>> call, Response<ServerResponse<Weather>>response) {
                if (response.body() != null) {
                    currentCityId = cityId;
                    Log.i("WATHER_SERVICE", response.body().data.getCity() + response.body().data.getWeatherCondition());
                    TextView temp = (TextView) findViewById(R.id.text_temp);
                    temp.setText(String.format("%s°C", response.body().data.getTemperature()));

                    TextView press = (TextView) findViewById(R.id.text_press);
                    press.setText(String.format("%s Hpa", response.body().data.getPressure()));

                    currentCityName = String.format("%s, %s", response.body().data.getCity(), response.body().data.getCountry());
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<ServerResponse<Weather>> call, Throwable t) {
                Log.e("WATHER_SERVICE", "ERROR: It wasn't possible to retrieve the weather information", t);
                Toast.makeText(getBaseContext(), "It wasn't possible to retrieve the weather information", Toast.LENGTH_LONG).show();
                hideProgressBar();
            }
        });
    }

    private void showProgressBar(){
        TextView temp = (TextView) findViewById(R.id.text_temp);
        temp.setVisibility(View.INVISIBLE);

        TextView press = (TextView) findViewById(R.id.text_press);
        press.setVisibility(View.INVISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Loading...");

        handler.post(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentCityName);

        TextView temp = (TextView) findViewById(R.id.text_temp);
        temp.setVisibility(View.VISIBLE);

        TextView press = (TextView) findViewById(R.id.text_press);
        press.setVisibility(View.VISIBLE);
    }
}
