package com.example.lalit.movieguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jainl on 29-10-2017.
 */

public class MovieTrailerRecyclerViewAdapter extends RecyclerView.Adapter<MovieTrailerRecyclerViewAdapter.TrailerViewHolder> {
    Context mContext;
    ArrayList<MovieDetailTrailersResponse.MovieTrailer> mMovieTrailerArrayListArrayList;
    MovieTrailerRecyclerViewAdapter.TrailerClickedListener mListener;
    public MovieTrailerRecyclerViewAdapter(Context context, ArrayList<MovieDetailTrailersResponse.MovieTrailer> trailerList, TrailerClickedListener listener) {
        mContext = context;
        mMovieTrailerArrayListArrayList = trailerList;
        mListener = listener;
    }


    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.trailer_row_layout,parent,false);
        return new TrailerViewHolder(rowView,mListener);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        String trailerKey = mMovieTrailerArrayListArrayList.get(position).key;
        Picasso.with(mContext).load("http://img.youtube.com/vi/"+trailerKey+"/hqdefault.jpg").into(holder.trailerPoster);
        holder.trailerTitle.setText(mMovieTrailerArrayListArrayList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mMovieTrailerArrayListArrayList.size();
    }

    interface TrailerClickedListener {
        void onTrailerClicked(int position);
    }
    //http://img.youtube.com/vi/trailer_key/hqdefault.jpg
    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView;
        TextView trailerTitle;
        ImageView trailerPoster;
        TrailerClickedListener mListener;
        public TrailerViewHolder(View itemView, TrailerClickedListener listener) {
            super(itemView);
            this.itemView = itemView;
            mListener = listener;
            trailerTitle = itemView.findViewById(R.id.videoCardTitleView);
            trailerPoster = itemView.findViewById(R.id.videoCardPosterView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onTrailerClicked(position);
        }
    }
}
