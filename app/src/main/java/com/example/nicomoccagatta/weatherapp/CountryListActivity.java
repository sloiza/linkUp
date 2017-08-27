package com.example.nicomoccagatta.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.nicomoccagatta.weatherapp.dummy.CountryListContent;
import com.example.nicomoccagatta.weatherapp.dummy.Country;


import java.util.List;

/**
 * An activity representing a list of Cities.
 */
public class CountryListActivity extends AppCompatActivity {

    public static final String MESSAGE_COUNTRY_ID = "com.weatherapp.MESSAGE_COUNTRY_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.city_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            String cityId = data.getStringExtra(CityListActivity.MESSAGE_CITY_ID);
            Log.i("ID", cityId);
//            Intent intent = new Intent();
//            intent.putExtra(CityListActivity.MESSAGE_CITY_ID, cityId);
            setResult(RESULT_OK, data);
            finish();
        }
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
        CountryListContent.loadCountries(getBaseContext());
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(CountryListContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Country> mValues;

        public SimpleItemRecyclerViewAdapter(List<Country> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.country_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter = this;
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getCode());
            holder.mContentView.setText(mValues.get(position).getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                        intent.putExtra(CityDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    Log.i("ITEM", holder.mItem.getCode());
                    Intent intent = new Intent(getBaseContext(), CityListActivity.class);
                    String countryId = holder.mItem.getCode();
                    intent.putExtra(MESSAGE_COUNTRY_ID, countryId);
//                    setResult(RESULT_OK, intent);
//                    finish();
                    startActivityForResult(intent, 28);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Country mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
