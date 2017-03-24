package com.ak.movienight.entertainment;

import java.util.ArrayList;

/**
 * Created by AK on 23/03/2017.
 */

public class MovieCollection {
    private Movie[] mMovies;
    private ArrayList<Movie> mAllMovies;

    public ArrayList<Movie> getAllMovies() {
        return mAllMovies;
    }

    public void setAllMovies(ArrayList<Movie> allMovies) {
        mAllMovies = allMovies;
    }

    public Movie[] getMovies() {
        return mMovies;
    }

    public void setMovies(Movie[] movies) {
        mMovies = movies;
    }

}
