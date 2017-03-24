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
import com.ak.movienight.UI.TVShowDetailsDialogFragment;
import com.ak.movienight.entertainment.TVShow;

/**
 * Created by AK on 23/03/2017.
 */

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>{
        private TVShow[] mTvShows;
        private Context mContext;

        public TVShowAdapter(Context context, TVShow[] tvShows){
            mContext=context;
            mTvShows=tvShows;
        }
        @Override
        public TVShowAdapter.TVShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_result_item, parent, false);
            return new TVShowAdapter.TVShowViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TVShowAdapter.TVShowViewHolder holder, int position) {
            holder.bindTVShow(mTvShows[position]);
        }

        @Override
        public int getItemCount() {
            return mTvShows.length;
        }

        public class TVShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView mTitle;
            public TextView mYear;
            public TextView mRating;
            public TextView mNumOfRatings;
            public String mOverview;


            public TVShowViewHolder(View itemView) {
                super(itemView);
                mTitle=(TextView) itemView.findViewById(R.id.titleTextView);
                mYear=(TextView) itemView.findViewById(R.id.yearTextView);
                mRating=(TextView) itemView.findViewById(R.id.rating);
                mNumOfRatings=(TextView) itemView.findViewById(R.id.numOfRatings);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("OVERVIEW", mOverview);
                bundle.putString("TITLE", mTitle.getText().toString());
                TVShowDetailsDialogFragment dialogFragment=new TVShowDetailsDialogFragment();
                dialogFragment.setArguments(bundle);

                FragmentManager manager = ((Activity) view.getContext()).getFragmentManager();
                dialogFragment.show(manager, "tvshows_details");

            }
            public void bindTVShow(TVShow tvShow){
                mTitle.setText(tvShow.getName());
                mYear.setText(" ("+tvShow.getFirstAirYear()+")");
                mRating.setText("Average Vote: "+tvShow.getRating()+"");
                mNumOfRatings.setText("Total votes: "+tvShow.getNumOfRatings());
                mOverview=tvShow.getOverview();
            }
        }
}
