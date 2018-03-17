package com.github.simonoppowa.popularmoviesappstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;
import com.github.simonoppowa.popularmoviesappstage1.utilities.JSONUtils;
import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private static final int NUMMBER_COLUMNS = 2;

    private RecyclerView mMovieRecyclerView;
    private GridLayoutManager mGridLayoutMangaer;
    private MovieAdapter mMovieAdapter;

    private List<Movie> mPopularMovies;
    private boolean sort_by_popularity = true;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemClicked = item.getItemId();

           // TODO (1)
        if(menuItemClicked == R.id.sort_by) {

            View menuItemView = findViewById(R.id.sort_by);

            PopupMenu popup = new PopupMenu(this, menuItemView);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        return true;
    }

    private String fetchPopularMovies() {
        FetchPopularMoviesTask fetchPopularMoviesTask = (FetchPopularMoviesTask) new FetchPopularMoviesTask();
        String fetchResults = null;

        try {
            if(sort_by_popularity) {
                fetchResults = fetchPopularMoviesTask.execute(NetworkUtils.buildPopularMoviesUrlByPopularity(1)).get();
            }
            else {
                fetchResults = fetchPopularMoviesTask.execute(NetworkUtils.buildPopularMoviesUrlByRating(1)).get();
            }

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


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if(item.getItemId() == R.id.sort_by_popularity_popup) {
            sort_by_popularity = true;
            String moviesResultsString = fetchPopularMovies();

            setPopularMoviesList(moviesResultsString);
        }

        if(item.getItemId() == R.id.sort_by_review_popup) {
            sort_by_popularity = false;
            String moviesResultsString = fetchPopularMovies();

            setPopularMoviesList(moviesResultsString);

        }
           // TODO (1)

        mMovieAdapter.setPopularMoviesList(mPopularMovies);
        mMovieAdapter.notifyDataSetChanged();

        return false;
    }
}
