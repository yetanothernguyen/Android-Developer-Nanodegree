package com.yetanothernguyen.popularmovies;

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
public class FetchMovieTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    private OnTaskCompleted listener;

    public interface OnTaskCompleted {
        void onTaskCompleted(ArrayList<Movie> movies);
    }

    public FetchMovieTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson;

        try {
            URL url = new URL("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=7bb88989e38842716731af3ac8525132");
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
