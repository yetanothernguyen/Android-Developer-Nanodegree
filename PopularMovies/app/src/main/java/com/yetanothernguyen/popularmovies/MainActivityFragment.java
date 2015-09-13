package com.yetanothernguyen.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FetchMovieTask.OnTaskCompleted {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private final String MOVIES_KEY = "MOVIES";
    private MoviesAdapter mMoviesAdapter;
    private ArrayList<Movie> mMovies;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        GridView movies = (GridView) rootView.findViewById(R.id.movies_gridview);
        movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMoviesAdapter.getItem(position);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                startActivity(i);
            }
        });

        mMoviesAdapter = new MoviesAdapter(getActivity(), new ArrayList<Movie>());
        movies.setAdapter(mMoviesAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
            setMoviesAdapter();
        } else {
            mMovies = new ArrayList<>();
            fetchMovies();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES_KEY, mMovies);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTaskCompleted(ArrayList<Movie> movies) {
        if (movies != null) {
            mMovies.clear();
            mMovies.addAll(movies);
            setMoviesAdapter();
        }
    }

    public void fetchMovies() {
        Log.d(LOG_TAG, "Fetching movies");
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String sortBy = sharedPref.getString(getString(R.string.preference_sort_by_key), FetchMovieTask.SORT_BY_MOST_POPULAR);

        FetchMovieTask fetchMovieTask = new FetchMovieTask(this);
        fetchMovieTask.execute(sortBy);
    }

    protected void setMoviesAdapter() {
        mMoviesAdapter.clear();
        for (Movie movie : mMovies) {
            mMoviesAdapter.add(movie);
        }
    }
}
