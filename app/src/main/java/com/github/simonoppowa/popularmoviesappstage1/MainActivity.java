package com.github.simonoppowa.popularmoviesappstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;
import com.github.simonoppowa.popularmoviesappstage1.utilities.JSONUtils;
import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private List<Movie> mPopularMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPopularMovies = new ArrayList<>();

        String moviesResultsString = fetchPopularMovies();

        setPopularMoviesList(moviesResultsString);

    }

    private String fetchPopularMovies() {
        FetchPopularMoviesTask fetchPopularMoviesTask = (FetchPopularMoviesTask) new FetchPopularMoviesTask();
        String fetchResults = null;

        try {
            fetchResults = fetchPopularMoviesTask.execute(NetworkUtils.buildPopularMoviesUrlByPopularity(1)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return fetchResults;
    }

    private void setPopularMoviesList(String moviesResultsString) {
        try {
            mPopularMovies = JSONUtils.getMovieListFromJSON(moviesResultsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
