package com.yetanothernguyen.popularmovies;

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

        fetchMovies(FetchMovieTask.SortBy.MOST_POPULAR);

        return rootView;
    }

    @Override
    public void onTaskCompleted(ArrayList<Movie> movies) {
        mMoviesAdapter.clear();
        for (Movie movie : movies) {
            mMoviesAdapter.add(movie);
        }
    }

    public void fetchMovies(FetchMovieTask.SortBy sortBy) {
        FetchMovieTask fetchMovieTask = new FetchMovieTask(this);
        fetchMovieTask.execute(sortBy);
    }
}
