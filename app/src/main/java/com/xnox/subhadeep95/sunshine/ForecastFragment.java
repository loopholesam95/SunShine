package com.xnox.subhadeep95.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Subhadeep95 on 1/14/2017.
 */

public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        String[] forecastArray = {
                "Monday - Sunny - 88/63",
                "Tuesday - Foggy - 70/40",
                "Weds - Cloudy - 72/63",
                "Thrus - Asteroids - 75/65",
                "Fri - Heavy Rain - 65/56",
                "Sat - HELP TRAPPED IN WEATHERSTATION - 60/51",
                "Sun - Sunny 80/68 "
        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForecast
        );

        ListView listview = (ListView) rootView.findViewById(R.id.listview_forecast);
        listview.setAdapter(mForecastAdapter);


        }


    }

    public class FetchweatherTask extends AsyncTask<Void, Void, Void> {


        private final String LOG_TAG = FetchweatherTask.class.getSimpleName();
@Override
        protected Void doInBackground(Void... params) {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;

    String forecastJsonstr = null;

    try {
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null)
        {
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = reader.readLine())!= null)
        {
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0)
        {
            return null;
        }
        forecastJsonstr = buffer.toString();
    } catch (IOException e) {
        Log.e(LOG_TAG, "Exception caught", e);
        return null;

    }
    finally {
        if (urlConnection != null)
        {
            urlConnection.disconnect();
        }
        if (reader != null)
        {
            try{
                 reader.close();
            }
            catch(final IOException e)
            {
                Log.e(LOG_TAG,"Error closing stream",e);
            }
        }
    }

}
}

