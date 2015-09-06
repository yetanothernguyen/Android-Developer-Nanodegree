package com.yetanothernguyen.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nguyenvunguyen on 9/6/15.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    private final String LOG_TAG = MoviesAdapter.class.getSimpleName();

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        Log.d(LOG_TAG, "getView: movie " + movie.getTitle());
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }

        Log.d(LOG_TAG, "getView: loading " + movie.getImageURL());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview_poster);
        Picasso.with(getContext()).load(movie.getImageURL()).into(imageView);

        return convertView;
    }
}
