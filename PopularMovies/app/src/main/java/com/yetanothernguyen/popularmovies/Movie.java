package com.yetanothernguyen.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nguyenvunguyen on 9/6/15.
 */
public class Movie {

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
}
