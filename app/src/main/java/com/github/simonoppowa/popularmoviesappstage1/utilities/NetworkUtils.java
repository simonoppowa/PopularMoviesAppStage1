package com.github.simonoppowa.popularmoviesappstage1.utilities;

import android.net.Uri;
import android.util.Log;

import com.github.simonoppowa.popularmoviesappstage1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Simon on 14.03.2018.
 */

public final class NetworkUtils {

    private static final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIEDB_POPULAR_TAG = "popular";

    private static final String MOVIEDB_API_KEY_TAG = "api_key";
    private static final String MOVIEDB_LANGUAGE_TAG = "language";
    private static final String MOVIEDB_PAGE_TAG = "page";
    private static final String MOVIEDB_SORT_BY_TAG = "sort_by";

    private static String LANGUAGE_PARAM = "en-US";
    private static String SORT_BY_POPULARITY_PARAM = "popularity.desc";
    private static String SORT_BY_REVIEW_PARAM = "vote_average.desc";


    public static URL buildPopularMoviesUrlByPopularity(int page) {

        String myApiKey = BuildConfig.MY_MOVIE_DB_API_KEY;

        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL + MOVIEDB_POPULAR_TAG).buildUpon()
                .appendQueryParameter(MOVIEDB_API_KEY_TAG, myApiKey)
                .appendQueryParameter(MOVIEDB_LANGUAGE_TAG, LANGUAGE_PARAM)
                .appendQueryParameter(MOVIEDB_SORT_BY_TAG, SORT_BY_POPULARITY_PARAM)
                .appendQueryParameter(MOVIEDB_PAGE_TAG, Integer.toString(page))
                .build();

        URL builtUrl = null;

        try {
            builtUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("MyActivity",builtUrl.toString());

        return builtUrl;
    }

    public static String getResponseFromHttp(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if(hasInput = scanner.hasNext());
            if(hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
