package com.yetanothernguyen.popularmovies;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FetchMovieTask.OnTaskCompleted {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private MoviesAdapter mMoviesAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        GridView movies = (GridView) rootView.findViewById(R.id.movies_gridview);

        mMoviesAdapter = new MoviesAdapter(getActivity(), new ArrayList<Movie>());
        movies.setAdapter(mMoviesAdapter);

        FetchMovieTask fetchMovieTask = new FetchMovieTask(this);
        fetchMovieTask.execute();

        return rootView;
    }

    @Override
    public void onTaskCompleted(ArrayList<Movie> movies) {
        Log.d(LOG_TAG, "onTaskCompleted: movies " + movies.size());
        mMoviesAdapter.clear();
        for (Movie movie : movies) {
            Log.d(LOG_TAG, "onTaskCompleted: adding " + movie.getTitle());
            mMoviesAdapter.add(movie);
        }
    }
}
