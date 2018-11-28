package com.example.bhavikvashi.quickreportapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Event>> {
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=20";
    private EarthquakeAdapter mAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.mainlist);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Event>());
        earthquakeListView.setAdapter(mAdapter);
        mEmptyStateTextView = (TextView) findViewById(R.id.emptystate);
        mEmptyStateTextView.setText("Earth quakes are getting");
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }
        else
        {
            View loadingIndicator = findViewById(R.id.loading);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No internet");
        }



    }



    @Override
    public Loader<List<Event>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new EQloader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Event>> loader, List<Event> earthquakes) {
        // Clear the adapter of previous earthquake data
        ProgressBar p1=(ProgressBar)findViewById(R.id.loading);
        p1.setVisibility(View.INVISIBLE);
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
        mEmptyStateTextView.setText(R.string.no_earthquakes);
    }
    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {
        mAdapter.clear();

    }
}


