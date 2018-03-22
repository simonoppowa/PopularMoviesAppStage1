package com.github.simonoppowa.popularmoviesappstage1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;
import com.github.simonoppowa.popularmoviesappstage1.utilities.JSONUtils;
import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, MovieAdapter.ListItemClickListener{

    private static final int NUMBER_COLUMNS = 2;
    private static final String MOVIE_KEY = "movies";

    private RecyclerView mMovieRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorTextView;

    private List<Movie> mPopularMovies;
    private boolean sortByPopularity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieRecyclerView = (RecyclerView) findViewById(R.id.movie_card_recycler_view);
        mErrorTextView = (TextView) findViewById(R.id.error_message_TV);


        //creating list
        mPopularMovies = new ArrayList<>();

        if(savedInstanceState != null && savedInstanceState.containsKey(MOVIE_KEY)) {
           mPopularMovies = savedInstanceState.getParcelableArrayList(MOVIE_KEY);
        }
        else {
            //filling list
            String moviesResultsString = fetchPopularMovies();

            if(checkForError(moviesResultsString)) {
                showErrorMessage();
            }

            else {
                setPopularMoviesList(moviesResultsString);
            }

        }

        //creating recyclerView
        mGridLayoutManager = new GridLayoutManager(this, NUMBER_COLUMNS);
        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);

        mMovieAdapter = new MovieAdapter(this, this, mPopularMovies);

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

        if(menuItemClicked == R.id.sort_by) {
            setPopupMenu();
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_KEY, (ArrayList<? extends Parcelable>) mPopularMovies);
    }

    private String fetchPopularMovies() {
        FetchPopularMoviesTask fetchPopularMoviesTask = (FetchPopularMoviesTask) new FetchPopularMoviesTask();
        String fetchResults = null;

        try {
            if(sortByPopularity) {
                fetchResults = fetchPopularMoviesTask.execute(NetworkUtils.buildPopularMoviesUrlByPopularity(1)).get();
            }
            else {
                fetchResults = fetchPopularMoviesTask.execute(NetworkUtils.buildPopularMoviesUrlByRating(1)).get();
            }

        } catch (InterruptedException | ExecutionException e) {
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

    private void setPopupMenu() {
        View menuItemView = findViewById(R.id.sort_by);

        PopupMenu popup = new PopupMenu(this, menuItemView);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    private boolean checkForError(String moviesString) {
        if(moviesString == null || moviesString.equals("")) {
            showErrorMessage();
            return true;
        }
        else {
            return false;
        }
    }

    private void showErrorMessage() {
        mMovieRecyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);

    }

    private void showMovieData() {
        mMovieRecyclerView.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        String moviesResultsString = null;
        int itemClicked= item.getItemId();

        if(itemClicked == R.id.sort_by_popularity_popup) {
            sortByPopularity = true;
            moviesResultsString = fetchPopularMovies();
        }

        if(itemClicked == R.id.sort_by_review_popup) {
            sortByPopularity = false;
            moviesResultsString = fetchPopularMovies();
        }
        if(checkForError(moviesResultsString)) {
            showErrorMessage();
        }
        else {
            setPopularMoviesList(moviesResultsString);
            showMovieData();

            mMovieAdapter.setPopularMoviesList(mPopularMovies);
            mMovieAdapter.notifyDataSetChanged();
        }

        return false;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(this, MovieDetailActivity.class);

        intent.putExtra(MOVIE_KEY, mPopularMovies.get(clickedItemIndex));

        startActivity(intent);
    }
}
