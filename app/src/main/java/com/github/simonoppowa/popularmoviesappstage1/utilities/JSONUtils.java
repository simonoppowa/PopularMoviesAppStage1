package com.github.simonoppowa.popularmoviesappstage1.utilities;

import android.util.Log;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 14.03.2018.
 */

public class JSONUtils {

    private static final String JSON_RESULTS_TAG = "results";

    private static final String JSON_ID_TAG = "id";
    private static final String JSON_TITLE_TAG = "title";
    private static final String JSON_OVERVIEW_TAG = "overview";
    private static final String JSON_IMAGE_PATH_TAG = "poster_path";

    public static List<Movie> getMovieListFromJSON(String jsonString) throws JSONException {

        //Movie attributes
        int id;
        String title, overview, image_path;

        //MovieList
        List<Movie> movieList = new ArrayList<>();

        //full Json Object
        JSONObject fullJson = new JSONObject(jsonString);

        Log.d("MyActivity", fullJson.toString(4));

        //results Json Array
        JSONArray resultsJsonArray = fullJson.getJSONArray(JSON_RESULTS_TAG);

        for(int i = 0; i < resultsJsonArray.length(); i++) {

            //movie id
            id = resultsJsonArray.getJSONObject(i).getInt(JSON_ID_TAG);

            //movie title
            title = resultsJsonArray.getJSONObject(i).getString(JSON_TITLE_TAG);

            //movie overview
            overview = resultsJsonArray.getJSONObject(i).getString(JSON_OVERVIEW_TAG);

            //movie image_path
            image_path = resultsJsonArray.getJSONObject(i).getString(JSON_IMAGE_PATH_TAG);

            //movie object
            Movie newMovie = new Movie(id, title, overview, image_path);

            movieList.add(newMovie);
        }

        return movieList;
    }
}
