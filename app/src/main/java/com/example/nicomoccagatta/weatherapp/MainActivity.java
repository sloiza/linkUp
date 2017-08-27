package com.example.nicomoccagatta.weatherapp;


import android.content.Intent;
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

    private ProgressBar progressBar;
    private Handler handler = new Handler();  // for the progress bar

    private String currentCityId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Buenos Aires, AR");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                reloadData();
            }
        });
        reloadData();
        Log.i("MAIN_ACTIVITY", "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();

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
            Intent intent = new Intent(this, CityListActivity.class);
            startActivityForResult(intent, 27);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
            if (resultCode == RESULT_OK) {
                String cityId = data.getStringExtra(CityListActivity.MESSAGE_CITY_ID);
                Log.i("ID", cityId);
                currentCityId = cityId;
            }
    }


    public void reloadData() {
        showProgressBar();
        new WeatherService().getWeather(currentCityId, new Callback<ServerResponse<Weather>>() {
            @Override
            public void onResponse(Call<ServerResponse<Weather>> call, Response<ServerResponse<Weather>>response) {
                if (response.body() != null) {
                    Log.i("WATHER_SERVICE", response.body().data.getCity() + response.body().data.getWeatherCondition());
                    TextView temp = (TextView) findViewById(R.id.text_temp);
                    temp.setText(String.format("%s°C", response.body().data.getTemperature()));

                    TextView press = (TextView) findViewById(R.id.text_press);
                    press.setText(String.format("%s Hpa", response.body().data.getPressure()));

                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    toolbar.setTitle(String.format("%s, %s", response.body().data.getCity(), response.body().data.getCountry()));
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

        handler.post(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
        TextView temp = (TextView) findViewById(R.id.text_temp);
        temp.setVisibility(View.VISIBLE);

        TextView press = (TextView) findViewById(R.id.text_press);
        press.setVisibility(View.VISIBLE);
    }
}
