package com.ak.movienight.UI;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ak.movienight.R;
import com.ak.movienight.adapters.MovieAdapter;
import com.ak.movienight.adapters.TVShowAdapter;
import com.ak.movienight.entertainment.Movie;
import com.ak.movienight.entertainment.TVShow;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TVShowsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        Parcelable[] parcelables=intent.getParcelableArrayExtra(MainActivity.TVSHOWS);
        TVShow[] tvshows=Arrays.copyOf(parcelables,parcelables.length,TVShow[].class);

        TVShowAdapter adapter=new TVShowAdapter(this, tvshows);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);
    }
    @OnClick (R.id.startAgainButton)
    public void startAgain(View view){
        finish();
    }
}
