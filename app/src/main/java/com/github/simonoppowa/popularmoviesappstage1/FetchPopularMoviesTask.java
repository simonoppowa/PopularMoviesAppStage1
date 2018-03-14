package com.github.simonoppowa.popularmoviesappstage1;

import android.os.AsyncTask;
import android.util.Log;

import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Simon on 14.03.2018.
 */

public class FetchPopularMoviesTask extends AsyncTask<URL, Void, String>{
    @Override
    protected String doInBackground(URL... urls) {
        URL fetchUrl = urls[0];

        String fetchResults = null;

        try {
            fetchResults = NetworkUtils.getResponseFromHttp(fetchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("MyActivity", fetchResults);
        return fetchResults;
    }
}
