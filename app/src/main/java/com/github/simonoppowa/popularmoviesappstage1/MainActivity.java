package com.github.simonoppowa.popularmoviesappstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchPopularMovies();
    }

    private void fetchPopularMovies() {
        new FetchPopularMoviesTask().execute(NetworkUtils.buildPopularMoviesUrlByPopularity(1));
    }


}
