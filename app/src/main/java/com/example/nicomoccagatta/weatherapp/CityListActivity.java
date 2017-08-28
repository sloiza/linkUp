package com.example.nicomoccagatta.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nicomoccagatta.weatherapp.dummy.City;
import com.example.nicomoccagatta.weatherapp.dummy.CityListContent;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    public static final String MESSAGE_CITY_ID = "com.weatherapp.MESSAGE_CITY_ID";


    private ProgressBar progressBar;
    private SearchView searchView;
    private Handler handler = new Handler();  // for the progress bar

    private String countryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        searchView = (SearchView) findViewById(R.id.search_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        countryId = intent.getStringExtra(CountryListActivity.MESSAGE_COUNTRY_ID);

        View recyclerView = findViewById(R.id.city_list);
        assert recyclerView != null;

//        showProgressBar();
        setupRecyclerView((RecyclerView) recyclerView);
//        hideProgressBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();  // back to stacked activity -- onResume
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        CityListContent.loadCities(getBaseContext(), countryId);
        final CityListActivity.SimpleItemRecyclerViewAdapter adapter = new CityListActivity.SimpleItemRecyclerViewAdapter(CityListContent.ITEMS);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void showProgressBar(){
        handler.post(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<CityListActivity.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<City> mValues;
        private List<City> itemsCopy = new ArrayList<>();

        public SimpleItemRecyclerViewAdapter(List<City> items) {
            mValues = items;
            itemsCopy.addAll(items);
        }

        @Override
        public CityListActivity.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.city_list_content, parent, false);
            return new CityListActivity.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CityListActivity.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText("");
            holder.mContentView.setText(mValues.get(position).getN());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ITEM", String.valueOf(holder.mItem.getId()));

                    Intent intent = new Intent();
                    String cityId = String.valueOf(mValues.get(holder.getAdapterPosition()).getId());
                    intent.putExtra(MESSAGE_CITY_ID, cityId);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void filter(String text) {
            mValues.clear();
            if(text.isEmpty()){
                mValues.addAll(itemsCopy);
            } else{
                text = text.toLowerCase();
                for(City item: itemsCopy){
                    if(item.getN().toLowerCase().contains(text)){
                        mValues.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public City mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.city_id);
                mContentView = (TextView) view.findViewById(R.id.city_content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}