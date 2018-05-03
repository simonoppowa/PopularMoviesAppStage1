package com.github.simonoppowa.popularmoviesappstage1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;
import com.github.simonoppowa.popularmoviesappstage1.model.Review;
import com.github.simonoppowa.popularmoviesappstage1.model.Video;
import com.github.simonoppowa.popularmoviesappstage1.utilities.JSONUtils;
import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;
import com.github.simonoppowa.popularmoviesappstage1.utilities.YoutubeUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements VideoAdapter.ListItemClickListener{

    private static final String MOVIE_KEY = "movies";

    private RecyclerView mVideoRecyclerView;
    private LinearLayoutManager mHorizontalLinearLayoutManager;
    private VideoAdapter mVideoAdapter;

    private RecyclerView mReviewRecyclerView;
    private LinearLayoutManager mVerticalLinearLayoutManager;
    private ReviewAdapter mReviewAdapter;

    private List<Video> mVideoList;
    private List<Review> mReviewList;

    private LinearLayout mExtraDetailLayout;
    private ProgressBar mExtraDetailProgressBar;
    private TextView mTitleTV;
    private ImageView mImageIV;
    private TextView mReleaseDateTV;
    private TextView mRatingTV;
    private TextView mOverviewTV;

    private TextView mReviewErrorMessage;
    private TextView mVideoErrorMessage;

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

        mReviewErrorMessage = (TextView) findViewById(R.id.review_error_message);
        mVideoErrorMessage = (TextView) findViewById(R.id.video_error_message);

        mExtraDetailLayout = findViewById(R.id.movie_extra_detail);
        mExtraDetailProgressBar = findViewById(R.id.extra_detail_progressbar);

        mVideoList = new ArrayList<>();
        mReviewList = new ArrayList<>();

        setTitle(R.string.movie_detail_name);

        Intent intent = getIntent();

        mSelectedMovie = (Movie) intent.getParcelableExtra(MOVIE_KEY);

        if(mSelectedMovie == null) {
            throw new NullPointerException("No Movie was passed to MovieDetailActivity");
        }

        //video recyclcerview
        mVideoRecyclerView = findViewById(R.id.video_item_recycler_view);
        mHorizontalLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mVideoRecyclerView.setLayoutManager(mHorizontalLinearLayoutManager);

        mVideoAdapter = new VideoAdapter(this, this, mVideoList);

        mVideoRecyclerView.setAdapter(mVideoAdapter);

        //review recyclerview
        mReviewRecyclerView = findViewById(R.id.review_item_recycler_view);
        mVerticalLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewRecyclerView.setLayoutManager(mVerticalLinearLayoutManager);

        mReviewAdapter = new ReviewAdapter(this, mReviewList);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
        mReviewRecyclerView.setNestedScrollingEnabled(false);

        populateMainUI();

        new ExtraDetailsQueryTask(this).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void populateMainUI() {

        //mTitleTextView with original title
        if(mSelectedMovie.getTitle().equals(mSelectedMovie.getOriginalTitle())) {
            mTitleTV.setText(mSelectedMovie.getTitle());
        }
        else {
            mTitleTV.setText(mSelectedMovie.getTitle() + "\n(" + mSelectedMovie.getOriginalTitle() + ")");
        }

        //mImageIV
        Picasso.with(getApplicationContext()).load(mSelectedMovie.getImagePath()).into(mImageIV);

        mReleaseDateTV.setText(mSelectedMovie.getReleaseYearString());

        mOverviewTV.setText(mSelectedMovie.getOverview());

        mRatingTV.setText(mSelectedMovie.getUserRating() + "/10");

    }

    public class ExtraDetailsQueryTask extends AsyncTask {

        private final Context context;

        public ExtraDetailsQueryTask(Context context) {
            this.context = context;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                //videos
                URL videosUrl = NetworkUtils.buildMovieVideosUrl(mSelectedMovie.getId());
                String jsonVideosString = NetworkUtils.getResponseFromHttp(videosUrl);
                mVideoList = JSONUtils.getMovieVideosFromJSON(jsonVideosString);

                //reviews
                URL reviewsUrl = NetworkUtils.buildMovieReviewsUrl(mSelectedMovie.getId());
                String jsonReviewString = NetworkUtils.getResponseFromHttp(reviewsUrl);
                mReviewList = JSONUtils.getMovieReviewsFromJSON(jsonReviewString);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            //check if videos available
            if(!mVideoList.isEmpty()) {
                mVideoAdapter.setVideoList(mVideoList);
            } else {
                mVideoRecyclerView.setVisibility(View.GONE);
                mVideoErrorMessage.setVisibility(View.VISIBLE);
            }

            //check if reviews available
            if(!mReviewList.isEmpty()) {
                mReviewAdapter.setReviewList(mReviewList);
            } else {
                mReviewRecyclerView.setVisibility(View.GONE);
                mReviewErrorMessage.setVisibility(View.VISIBLE);
            }

            mExtraDetailLayout.setVisibility(View.VISIBLE);
            mExtraDetailProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Uri youtubeLink = Uri.parse(String.valueOf(YoutubeUtils.buildYoutubeVideoLink(mVideoList.get(clickedItemIndex).getKey())));

        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, youtubeLink);

        startActivity(youtubeIntent);
    }
}
