package com.yetanothernguyen.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nguyenvunguyen on 9/6/15.
 */
public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private final static String LOG_TAG = FetchMovieTask.class.getSimpleName();
    private OnTaskCompleted listener;

    public final static String SORT_BY_MOST_POPULAR = "popularity.desc";
    public final static String SORT_BY_HIGHEST_RATED = "vote_average.desc";

    public interface OnTaskCompleted {
        void onTaskCompleted(ArrayList<Movie> movies);
    }

    public FetchMovieTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson;
        String sortBy;

        final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
        final int MINIMUM_VOTE_COUNT = 1000;

        if (params.length == 0) {
            sortBy = SORT_BY_MOST_POPULAR;
        } else {
            sortBy = params[0];
        }

        try {
            Uri.Builder uriBuilder = Uri.parse(BASE_URL).buildUpon();
            uriBuilder.appendQueryParameter("api_key", BuildConfig.MOVIEDB_API_KEY);

            if (sortBy.equals(SORT_BY_MOST_POPULAR)) {
                uriBuilder.appendQueryParameter("sort_by", sortBy);
            } else if (sortBy.equals(SORT_BY_HIGHEST_RATED)) {
                uriBuilder.appendQueryParameter("sort_by", sortBy);
                uriBuilder.appendQueryParameter("vote_count.gte", Integer.toString(MINIMUM_VOTE_COUNT));
            }

            Uri builtUri = uriBuilder.build();
            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }
            moviesJson = buffer.toString();
        } catch (IOException e){
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream ", e);
                }
            }
        }

        try {
            return Movie.fromJson(moviesJson);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        listener.onTaskCompleted(movies);
    }
}
