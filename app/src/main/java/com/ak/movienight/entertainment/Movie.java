package com.ak.movienight.entertainment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AK on 23/03/2017.
 */

public class Movie implements Parcelable{
    private int mId;
    private String mTitle;
    private String mOverview;
    private int mYearReleased;
    private int[] mGenres;
    private int mNumOfRatings;
    private double mRating;


    public Movie(){}

    private Movie(Parcel in){
        mId=in.readInt();
        mTitle=in.readString();
        mOverview=in.readString();
        mYearReleased=in.readInt();
        mGenres=in.createIntArray();
        mNumOfRatings=in.readInt();
        mRating=in.readDouble();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public int getYearReleased() {
        return mYearReleased;
    }

    public void setYearReleased(String yearReleased) {
        mYearReleased = Integer.parseInt(yearReleased);
    }

    public int[] getGenres() {
        return mGenres;
    }

    public void setGenres(int[] genres) {
        mGenres = genres;
    }

    public int getNumOfRatings() {
        return mNumOfRatings;
    }

    public void setNumOfRatings(int numOfRatings) {
        mNumOfRatings = numOfRatings;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
        parcel.writeInt(mYearReleased);
        parcel.writeIntArray(mGenres);
        parcel.writeInt(mNumOfRatings);
        parcel.writeDouble(mRating);

    }
    public static final Creator<Movie> CREATOR=new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
    public static String getGenreName(int genreCode) {

        String genreName = "";
        switch (genreCode) {
            case 28:
                genreName = "Action";
            case 12:
                genreName = "Adventure";
            case 16:
                genreName = "Animation";
            case 35:
                genreName = "Comedy";
            case 80:
                genreName = "Crime";
            case 99:
                genreName = "Documentary";
            case 18:
                genreName = "Drama";
            case 10751:
                genreName = "Family";
            case 14:
                genreName = "Fantasy";
            case 36:
                genreName = "History";
            case 27:
                genreName = "Horror";
            case 10402:
                genreName = "Music";
            case 9648:
                genreName = "Mystery";
            case 10749:
                genreName = "Romance";
            case 878:
                genreName = "Science Fiction";
            case 10770:
                genreName = "TV Movie";
            case 53:
                genreName = "Thriller";
            case 10752:
                genreName = "War";
            case 37:
                genreName = "Western";
        }
        return genreName;
    }
    public static String getGenreID(String genreName) {

        String genreID = "";
        switch (genreName) {
            case "Action":
                genreID =28+"" ;
                break;
            case "Adventure":
                genreID =12+"" ;
                break;
            case "Animation":
                genreID = 16+"";
                break;
            case "Comedy":
                genreID = 35+"";
                break;
            case "Crime":
                genreID = 80+"";
                break;
            case "Documentary":
                genreID = 99+"";
                break;
            case "Drama":
                genreID = 18+"";
                break;
            case  "Family":
                genreID = 10751+"";
                break;
            case "Fantasy":
                genreID = 14+"";
                break;
            case "History":
                genreID = 36+"";
                break;
            case "Horror":
                genreID = 27+"";
                break;
            case "Music":
                genreID =10402 +"";
                break;
            case "Mystery":
                genreID = 9648+"";
                break;
            case "Romance":
                genreID = 10749+"";
                break;
            case "Science Fiction":
                genreID = 878+"";
                break;
            case "TV Movie":
                genreID =10770+"" ;
                break;
            case "Thriller":
                genreID = 53+"";
                break;
            case "War":
                genreID = 10752+"";
                break;
            case "Western":
                genreID = 37+"";
        }
        return genreID;
    }
}
