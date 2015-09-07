package com.yetanothernguyen.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(DetailActivity.EXTRA_MOVIE);

        TextView titleView = (TextView) rootView.findViewById(R.id.textview_title);
        titleView.setText(movie.getTitle());

        TextView releaseDateView = (TextView) rootView.findViewById(R.id.textview_release_date);
        releaseDateView.setText(Integer.toString(movie.getReleaseYear()));

        TextView ratingView = (TextView) rootView.findViewById(R.id.textview_rating);
        ratingView.setText(movie.getFormattedUserRating());

        TextView synopsisView = (TextView) rootView.findViewById(R.id.textview_synopsis);
        synopsisView.setText(movie.getPlotSynopsis());

        ImageView ivPoster = (ImageView) rootView.findViewById(R.id.imageview_poster);
        Picasso.with(getActivity()).load(movie.getImageURL()).into(ivPoster);

        return rootView;
    }
}
