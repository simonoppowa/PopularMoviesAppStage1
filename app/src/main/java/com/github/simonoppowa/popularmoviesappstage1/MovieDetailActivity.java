package com.github.simonoppowa.popularmoviesappstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();

        Movie movie = (Movie) intent.getSerializableExtra("Movie");

        Log.d("MyActivity", movie.getTitle());
    }
}
