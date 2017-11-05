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

public class MovieCastRecyclerViewAdapter extends RecyclerView.Adapter<MovieCastRecyclerViewAdapter.MovieCastRecyclerViewAdapterViewHolder>{

    Context mContext;
    ArrayList<MovieDetailCastResponse.MovieCast> mMovieCastArrayList;
    CastClickedListener mListener;

    public MovieCastRecyclerViewAdapter(Context context, ArrayList<MovieDetailCastResponse.MovieCast> casts, CastClickedListener listener){
        mContext = context;
        mMovieCastArrayList = casts;
        mListener = listener;
    }

    @Override
    public MovieCastRecyclerViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.cast_card_layout,parent,false);
        return new MovieCastRecyclerViewAdapterViewHolder(rowView,mListener);
    }

    @Override
    public void onBindViewHolder(MovieCastRecyclerViewAdapterViewHolder holder, int position) {
        MovieDetailCastResponse.MovieCast cast = mMovieCastArrayList.get(position);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342" + cast.profilePath).into(holder.castCardProfileView);
        holder.castCardCharacterView.setText(cast.character);
        holder.castCardNameView.setText(cast.name);
    }

    @Override
    public int getItemCount() {
        return mMovieCastArrayList.size();
    }
    interface CastClickedListener{
        void onCastClicked(int position);
    }
    public class MovieCastRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView;
        ImageView castCardProfileView;
        TextView castCardNameView, castCardCharacterView;
        CastClickedListener castClickedListener;
        public MovieCastRecyclerViewAdapterViewHolder(View itemView, CastClickedListener listener) {
            super(itemView);
            this.itemView = itemView;
            castCardProfileView = itemView.findViewById(R.id.castCardProfileView);
            castCardNameView = itemView.findViewById(R.id.castCardNameView);
            castCardCharacterView = itemView.findViewById(R.id.castCardCharacterView);
            castClickedListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            castClickedListener.onCastClicked(position);
        }
    }
}
