package com.ak.movienight.UI;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ak.movienight.R;
import com.ak.movienight.adapters.MovieAdapter;
import com.ak.movienight.entertainment.Movie;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MoviesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        Parcelable[] parcelables=intent.getParcelableArrayExtra(MainActivity.MOVIES);
        Movie[] movies=Arrays.copyOf(parcelables,parcelables.length,Movie[].class);

        MovieAdapter adapter=new MovieAdapter(this, movies);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);

    }
    @OnClick(R.id.startAgainButton)
    public void startAgain(View view){
        finish();
    }

}
