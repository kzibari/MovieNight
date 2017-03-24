package com.ak.movienight.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ak.movienight.R;
import com.ak.movienight.UI.MovieDetailsDialogFragment;
import com.ak.movienight.entertainment.Movie;

/**
 * Created by AK on 23/03/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Movie[] mMovies;
    private Context mContext;

    public MovieAdapter(Context context, Movie[] movies){
        mContext=context;
        mMovies=movies;
    }
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_result_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {
        holder.bindMovie(mMovies[position]);
    }

    @Override
    public int getItemCount() {
        return mMovies.length;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public TextView mYear;
        public TextView mRating;
        public TextView mNumOfRatings;
        public Bundle bundle;
        public String mOverview;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mTitle=(TextView) itemView.findViewById(R.id.titleTextView);
            mYear=(TextView) itemView.findViewById(R.id.yearTextView);
            mRating=(TextView) itemView.findViewById(R.id.rating);
            mNumOfRatings=(TextView) itemView.findViewById(R.id.numOfRatings);
            bundle=new Bundle();
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

            Bundle bundle=new Bundle();
            bundle.putString("OVERVIEW", mOverview);
            bundle.putString("TITLE", mTitle.getText().toString());
            MovieDetailsDialogFragment dialogFragment=new MovieDetailsDialogFragment();
            dialogFragment.setArguments(bundle);
            FragmentManager manager = ((Activity) view.getContext()).getFragmentManager();
            dialogFragment.show(manager, "movies_details");

        }
        public void bindMovie(Movie movie){
            mTitle.setText(movie.getTitle());
            mYear.setText(" ("+movie.getYearReleased()+")");
            mRating.setText("Average Vote: "+movie.getRating()+"");
            mNumOfRatings.setText("Total votes: "+movie.getNumOfRatings());
            bundle.putParcelable("MOVIE", movie);
            mOverview=movie.getOverview();

        }
    }


}