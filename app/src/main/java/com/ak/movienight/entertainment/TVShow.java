package com.ak.movienight.entertainment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AK on 23/03/2017.
 */

public class TVShow implements Parcelable{
    private int mId;
    private String mName;
    private String mOverview;
    private int mFirstAirYear;
    private int[] mGenres;
    private int mNumOfRatings;
    private double mRating;

    public TVShow(){}
    protected TVShow(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mOverview = in.readString();
        mFirstAirYear = in.readInt();
        mGenres = in.createIntArray();
        mNumOfRatings = in.readInt();
        mRating = in.readDouble();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public int getFirstAirYear() {
        return mFirstAirYear;
    }

    public void setFirstAirYear(String firstAirYear) {
        mFirstAirYear = Integer.parseInt(firstAirYear);
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
        parcel.writeString(mName);
        parcel.writeString(mOverview);
        parcel.writeInt(mFirstAirYear);
        parcel.writeIntArray(mGenres);
        parcel.writeInt(mNumOfRatings);
        parcel.writeDouble(mRating);

    }
}
