package com.yetanothernguyen.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nguyenvunguyen on 9/6/15.
 */
public class Movie implements Parcelable {

    private String title;
    private String image;
    private String plotSynopsis;
    private double userRating;
    private String releaseDate;

    public Movie(JSONObject json)
            throws JSONException {
        this.setTitle(json.getString("original_title"));
        this.setImage(json.getString("poster_path"));
        this.setPlotSynopsis(json.getString("overview"));
        this.setUserRating(json.getDouble("vote_average"));
        this.setReleaseDate(json.getString("release_date"));
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getImageURL(){
        return "https://image.tmdb.org/t/p/w185" + this.getImage();
    }

    public String getFormattedUserRating() {
        return Double.toString(this.getUserRating()) + "/10";
    }

    public int getReleaseYear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(this.getReleaseDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ArrayList<Movie> fromJson(String json)
            throws JSONException {
        JSONObject moviesJson = new JSONObject(json);
        JSONArray moviesArray = moviesJson.getJSONArray("results");
        ArrayList<Movie> movies = new ArrayList();

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieJson = moviesArray.getJSONObject(i);
            movies.add(new Movie(movieJson));
        }

        return movies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(plotSynopsis);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        title = in.readString();
        image = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readDouble();
        releaseDate = in.readString();
    }
}
