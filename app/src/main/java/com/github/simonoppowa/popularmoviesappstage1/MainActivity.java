package com.github.simonoppowa.popularmoviesappstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;
import com.github.simonoppowa.popularmoviesappstage1.utilities.JSONUtils;
import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final int NUMMBER_COLUMNS = 2;

    private RecyclerView mMovieRecyclerView;
    private GridLayoutManager mGridLayoutMangaer;
    private MovieAdapter mMovieAdapter;

    private List<Movie> mPopularMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //creating list
        mPopularMovies = new ArrayList<>();

        //filling list
        String moviesResultsString = fetchPopularMovies();

        setPopularMoviesList(moviesResultsString);

        //creating recyclerView
        mMovieRecyclerView = (RecyclerView) findViewById(R.id.movie_card_recycler_view);

        mGridLayoutMangaer = new GridLayoutManager(this, NUMMBER_COLUMNS);
        mMovieRecyclerView.setLayoutManager(mGridLayoutMangaer);

        mMovieAdapter = new MovieAdapter(this, mPopularMovies);

        mMovieRecyclerView.setAdapter(mMovieAdapter);


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
