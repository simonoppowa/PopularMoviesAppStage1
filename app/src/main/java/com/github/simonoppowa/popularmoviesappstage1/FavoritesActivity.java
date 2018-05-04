package com.github.simonoppowa.popularmoviesappstage1;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.simonoppowa.popularmoviesappstage1.data.MovieContract;
import com.github.simonoppowa.popularmoviesappstage1.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView mFavoritesRecyclerView;
    private LinearLayoutManager mLinearLayoutMangager;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessage;

    private List<Movie> mFavoritesMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setTitle(R.string.favorites_activity_title);


        mFavoritesRecyclerView = findViewById(R.id.favorite_movies_recycler_view);
        mErrorMessage = findViewById(R.id.no_favorites);

        mFavoritesMoviesList = new ArrayList<>();

        mLinearLayoutMangager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mFavoritesRecyclerView.setLayoutManager(mLinearLayoutMangager);

        mMovieAdapter = new MovieAdapter(this, null, mFavoritesMoviesList);
        mFavoritesRecyclerView.setAdapter(mMovieAdapter);

        loadFavoritesMovies();
    }


    private void loadFavoritesMovies() {
        Cursor moviesCursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_MOVIES_URL, null, null, null, MovieContract.MovieEntry.COLUMN_DATE_ADDED + " DESC");

        while(moviesCursor.moveToNext()) {
            Movie newMovie = new Movie(moviesCursor.getInt(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID)), moviesCursor.getString(moviesCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE)), null, null, null, null, null);
            mFavoritesMoviesList.add(newMovie);
        }

        mMovieAdapter.setPopularMoviesList(mFavoritesMoviesList);
    }

}
