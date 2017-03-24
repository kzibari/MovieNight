package com.ak.movienight.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ak.movienight.R;
import com.ak.movienight.entertainment.Movie;
import com.ak.movienight.entertainment.TVShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG=MainActivity.class.getSimpleName();
    public static final int DELAY=600;
    public static final String MOVIES="MOVIES";
    public static final String TVSHOWS="TVSHOWS";
    public static final String API_KEY= "598884b2d394cfd4facd400eb67ea85f";

    @BindView(R.id.mYearReleasedSpinner) Spinner mYearReleasedSpinner;
    @BindView(R.id.ratingThresholdSpinner) Spinner mRatingThresholdSpinner;
    @BindView(R.id.minNumOfRatingsSpinner) Spinner mNumOfRatingsSpinner;
    @BindView(R.id.genreSpinner) Spinner mGenreSpinner;
    @BindView(R.id.moviesSearch) Button mMoviesSearch;
    @BindView(R.id.tvshowsSearch) Button mTVShowsSearch;

    private String yearReleased="";
    private String ratingThreshold="";
    private String numOfRatings="";
    private String genreNum="";
    private int total_num_of_movies=0;
    private int total_num_of_tvshows=0;

    private ArrayList<Movie> mTotalMovies;
    private ArrayList<TVShow> mTotalTVShows;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTotalMovies=new ArrayList<>(0);
        mTotalTVShows=new ArrayList<>(0);
        handleOptionsSelections();
    }

    private String createMoviesURL(String year, String ratings, String threshold, String genre) {
        return "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&primary_release_year="+year+"&vote_count.gte="+ratings+"&vote_average.gte="+threshold+"&with_genres="+genre;
    }
    private String createTVShowsURL(String year, String ratings, String threshold, String genre) {
        int followingYear=Integer.parseInt(year)+1;
        return "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&first_air_date.gte="+year+"&first_air_date.lte="+followingYear+"&vote_count.gte="+ratings+"&vote_average.gte="+threshold+"&with_genres="+genre;

    }

    private void handleOptionsSelections() {
        ArrayAdapter<CharSequence> adapterNumOfRatings = ArrayAdapter.createFromResource(this,
                R.array.num_of_ratings, android.R.layout.simple_spinner_item);
        adapterNumOfRatings.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNumOfRatingsSpinner.setAdapter(adapterNumOfRatings);
        mNumOfRatingsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numOfRatings=getNumOfRatings(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                numOfRatings="";
            }
        });
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYearReleasedSpinner.setAdapter(adapterYear);
        mYearReleasedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Any"))
                    yearReleased="";
                else
                    yearReleased=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                yearReleased="";
            }
        });
        ArrayAdapter<CharSequence> adapterRatingThreshold = ArrayAdapter.createFromResource(this,
                R.array.ratings, android.R.layout.simple_spinner_item);
        adapterRatingThreshold.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRatingThresholdSpinner.setAdapter(adapterRatingThreshold);

        mRatingThresholdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Any"))
                    ratingThreshold="";
                else
                    ratingThreshold=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ratingThreshold="";
            }
        });

        ArrayAdapter<CharSequence> adapterGenreSpinner = ArrayAdapter.createFromResource(this,
                R.array.genreNames, android.R.layout.simple_spinner_item);
        adapterGenreSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenreSpinner.setAdapter(adapterGenreSpinner);

        mGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Any"))
                    genreNum="";
                else
                    genreNum= adapterView.getItemAtPosition(i)+"";
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                genreNum="";
            }
        });
    }

    private String getNumOfRatings(int position){
        if(position==0)
            return "";
        else if (position==1)
            return "1";
        else if (position==2)
            return "100";
        else if (position==3)
            return "500";
        else
            return "1000";
    }

    @OnClick (R.id.moviesSearch)
    public void searchMovies(View view){
        mTotalMovies.clear();
        Intent intent=new Intent(this, MoviesActivity.class);
        String url=createMoviesURL(yearReleased,numOfRatings,ratingThreshold,Movie.getGenreID(genreNum));
        getTotalNumOfMovies(url);

        //Introduce delay of 0.7 seconds! Otherwise null pointer for Movies array.  This is a bandaid solution.
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v(TAG,url);
        Log.v(TAG,"Total Movies: "+total_num_of_movies);
        getAllMovies(url,total_num_of_movies);
        //Introduce delay of 0.7 seconds! Otherwise null pointer for Movies array.  This is a bandaid solution.
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intent.putExtra(MOVIES, mTotalMovies.toArray(new Movie[mTotalMovies.size()]));
        startActivity(intent);
    }
    @OnClick (R.id.tvshowsSearch)
    public void searchTVShows(View view){
        mTotalTVShows.clear();
        Intent intent=new Intent(this, TVShowsActivity.class);
        String url=createTVShowsURL(yearReleased,numOfRatings,ratingThreshold,Movie.getGenreID(genreNum));
        getTotalNumOfTVShows(url);

        //Introduce delay of 0.7 seconds! Otherwise null pointer for Movies array.  This is a bandaid solution.
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v(TAG,url);
        Log.v(TAG,"Total TV shows: "+total_num_of_tvshows);
        getAllTVShows(url,total_num_of_tvshows);
        //Introduce delay of 0.7 seconds! Otherwise null pointer for Movies array.  This is a bandaid solution.
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intent.putExtra(TVSHOWS, mTotalTVShows.toArray(new TVShow[mTotalTVShows.size()]));
        startActivity(intent);
    }

    private void getAllTVShows(String url, int total_num) {
        //only get up to first 10 pages (~200 tv shows) due to site request restrictions
        int pages=1;
        if(total_num>400)
            pages=20;
        else
            pages=(total_num/20)+1;
        for(int i=1; i<=pages; i++)
            getTVShows(url+"&page="+i);
    }
    private void getAllMovies(String url, int total_num) {

        //only get up to first 10 pages (~200 movies) due to site request restrictions
        int pages=1;
        if(total_num>200)
            pages=10;
        else
            pages=(total_num/20)+1;
        for(int i=1; i<=pages; i++)
            getMovies(url+"&page="+i);
    }
    private void getTotalNumOfTVShows(String url){
        if (isNetworkAvailable()) {
            Request requestTVShows = new Request.Builder().url(url).build();
            Call callMovies = client.newCall(requestTVShows);
            //Get TV Shows
            callMovies.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    alertUserAboutError();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            JSONObject data=new JSONObject(response.body().string());
                            total_num_of_tvshows=data.getInt("total_results");
                        } else
                            alertUserAboutError();
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught.", e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_LONG).show();
    }
    private void getTotalNumOfMovies(String url) {
        if (isNetworkAvailable()) {
            Request requestMovies = new Request.Builder().url(url).build();
            Call callMovies = client.newCall(requestMovies);
            //Get movies
            callMovies.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    alertUserAboutError();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            JSONObject data=new JSONObject(response.body().string());
                            total_num_of_movies=data.getInt("total_results");
                        } else
                            alertUserAboutError();
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught.", e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_LONG).show();
    }

    private void getTVShows(String url) {
        if(isNetworkAvailable()) {
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    alertUserAboutError();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            TVShow[] temp = getTVShowsDetails(jsonData);
                            for (int j = 0; j < temp.length; j++) {
                                mTotalTVShows.add(temp[j]);
                            }
                        } else
                            alertUserAboutError();
                    } catch (IOException e) {
                        Log.v(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_LONG).show();
    }
    public void getMovies(String url){

        if(isNetworkAvailable()) {
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    alertUserAboutError();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            Movie[] temp = getMoviesDeatils(jsonData);
                            for (int j = 0; j < temp.length; j++) {
                                mTotalMovies.add(temp[j]);
                            }
                        } else
                            alertUserAboutError();
                    } catch (IOException e) {
                        Log.v(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_LONG).show();
    }

    private TVShow[] getTVShowsDetails(String jsonData) throws JSONException {
        JSONObject tvShowsData = new JSONObject(jsonData);
        JSONArray data = tvShowsData.getJSONArray("results");
        TVShow[] tvShows = new TVShow[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonTVShow = data.getJSONObject(i);
            TVShow tvshow = new TVShow();

            try {
                tvshow.setName(jsonTVShow.getString("name"));
                tvshow.setOverview(jsonTVShow.getString("overview"));
                tvshow.setNumOfRatings(jsonTVShow.getInt("vote_count"));
                tvshow.setRating(jsonTVShow.getDouble("vote_average"));
                tvshow.setFirstAirYear(jsonTVShow.getString("first_air_date").substring(0, 4));
                JSONArray genres = jsonTVShow.getJSONArray("genre_ids");
                int[] tempArray = new int[genres.length()];
                for (int j = 0; j < genres.length(); j++)
                    tempArray[j] = genres.getInt(j);
                tvshow.setGenres(tempArray);
                tvShows[i] = tvshow;
            }catch (Exception e){
                continue;
            }
        }
        return tvShows;
    }
    private Movie[] getMoviesDeatils(String jsonData) throws JSONException {
        JSONObject moviesData=new JSONObject(jsonData);
        JSONArray array=moviesData.getJSONArray("results");
        Movie[] movies=new Movie[array.length()];

        for(int i=0; i<array.length(); i++){

            try {
                JSONObject jsonMovie = array.getJSONObject(i);
                Movie movie = new Movie();

                movie.setTitle(jsonMovie.getString("title"));
                movie.setOverview(jsonMovie.getString("overview"));
                movie.setNumOfRatings(jsonMovie.getInt("vote_count"));
                movie.setRating(jsonMovie.getDouble("vote_average"));
                movie.setYearReleased(jsonMovie.getString("release_date").substring(0, 4));
                JSONArray genres = jsonMovie.getJSONArray("genre_ids");
                int[] tempArray = new int[genres.length()];
                for (int j = 0; j < genres.length(); j++)
                    tempArray[j] = genres.getInt(j);
                movie.setGenres(tempArray);
                movies[i] = movie;
            }catch (Exception e){
                continue;
            }
        }
        return movies;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog=new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        boolean isAvailable=false;
        if(networkInfo!=null && networkInfo.isConnected())
            isAvailable=true;
        return isAvailable;
    }
}
