package com.github.simonoppowa.popularmoviesappstage1;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.simonoppowa.popularmoviesappstage1.model.Movie;
import com.github.simonoppowa.popularmoviesappstage1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Simon on 15.03.2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private Context context;

    private List<Movie> mPopularMovieList;

    private ListItemClickListener mListItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(Context context, ListItemClickListener listener, List<Movie> popularMovieList) {
        this.context = context;
        this.mListItemClickListener = listener;
        this.mPopularMovieList = popularMovieList;
    }

    public void setPopularMoviesList(List<Movie> popularMoviesList) {
        mPopularMovieList = popularMoviesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //setting title to textView
        holder.titleTV.setText(mPopularMovieList.get(position).getTitle());

        //setting image to imageView
        String imageUrlString = NetworkUtils.buildMovieImageUrlString(mPopularMovieList.get(position).getImagePath());
        Picasso.with(context).load(imageUrlString).into(holder.movieImageIV);

    }

    @Override
    public int getItemCount() {
        return mPopularMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView movieImageIV;
        public TextView titleTV;


        public ViewHolder(View itemView) {
            super(itemView);

            movieImageIV = (ImageView) itemView.findViewById(R.id.movie_image);
            titleTV = (TextView) itemView.findViewById(R.id.movie_title);

            itemView.setOnClickListener(this);

               // TODO (1) onClickListener only works on TextView
            movieImageIV.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedPosition);
        }
    }
}
