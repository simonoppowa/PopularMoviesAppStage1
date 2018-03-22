package com.github.simonoppowa.popularmoviesappstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;
import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE_KEY = "movies";

    private TextView mTitleTV;
    private ImageView mImageIV;
    private TextView mReleaseDateTV;
    private TextView mRatingTV;
    private TextView mOverviewTV;

    private Movie mSelectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitleTV = (TextView) findViewById(R.id.title_tv);
        mImageIV = (ImageView) findViewById(R.id.image_tv);
        mReleaseDateTV = (TextView) findViewById(R.id.release_date_tv);
        mRatingTV = (TextView) findViewById(R.id.rating_tv);
        mOverviewTV = (TextView) findViewById(R.id.overview_tv);

        setTitle(R.string.movie_detail_name);

        Intent intent = getIntent();

        mSelectedMovie = (Movie) intent.getParcelableExtra(MOVIE_KEY);

        populateUI();
    }

    private void populateUI() {

        //mTitleTextView with original title
        if(mSelectedMovie.getTitle().equals(mSelectedMovie.getOriginalTitle())) {
            mTitleTV.setText(mSelectedMovie.getTitle());
        }
        else {
            mTitleTV.setText(mSelectedMovie.getTitle() + "\n(" + mSelectedMovie.getOriginalTitle() + ")");
        }

        //mImageIV
        Picasso.with(getApplicationContext()).load(mSelectedMovie.getImagePath()).into(mImageIV);

        //mReleaseDate
        mReleaseDateTV.setText(mSelectedMovie.getReleaseYearString());

        //mOverViewTV
        mOverviewTV.setText(mSelectedMovie.getOverview());

        //mRatingTV
        mRatingTV.setText(mSelectedMovie.getUserRating() + "/10");
    }
}
