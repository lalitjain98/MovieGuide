package com.example.lalit.movieguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jainl on 26-10-2017.
 */

public class SmallImageRecyclerViewAdapter extends Adapter<SmallImageRecyclerViewAdapter.SmallImageRecyclerViewAdapterViewHolder>{

    Context mContext;
    ArrayList<Movie> mMovieArrayList;
    ArrayList<Movie> mTVArrayList;
    SmallImageMovieClickListener mListener;
    private int POSTER_TYPE;
    public SmallImageRecyclerViewAdapter(Context context, ArrayList<Movie> movies,int poster_type , SmallImageMovieClickListener listener){
        mContext = context;
        mMovieArrayList = movies;
        mListener = listener;
        POSTER_TYPE = poster_type;
    }
    @Override
    public SmallImageRecyclerViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.movie_card_layout,parent,false);
        return new SmallImageRecyclerViewAdapterViewHolder(rowView,mListener);
    }

    @Override
    public void onBindViewHolder(SmallImageRecyclerViewAdapterViewHolder holder, int position) {
        Movie movie = mMovieArrayList.get(position);

        holder.isClicked = (FavouritesDatabase.getInstance(mContext).getMovieDao().getMovie(movie.id)) != null;
        if(holder.isClicked){
            Picasso.with(mContext).load(R.drawable.ic_star).into(holder.movieCardFavouriteView);
        }
        else{

            Picasso.with(mContext).load(R.drawable.ic_star_border).into(holder.movieCardFavouriteView);
        }


        holder.movieCardTitleView.setText(movie.title);
        if(POSTER_TYPE == Constants.KEY_POSTER_SMALL){
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/" + movie.posterPath).into(holder.movieCardPosterView);
        }
        else if(POSTER_TYPE == Constants.KEY_POSTER_LARGE)
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w1280/" + movie.backdropPath).into(holder.movieCardPosterView);
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }

    public static class SmallImageRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView;
        TextView movieCardTitleView;
        ImageView movieCardPosterView;
        SmallImageMovieClickListener listener;
        ImageButton movieCardFavouriteView;
        Boolean isClicked;

        public void toggleFavouriteButton(){
            isClicked = !isClicked;
            if(isClicked){
                Picasso.with(itemView.getContext()).load(R.drawable.ic_star).placeholder(R.drawable.placeholder_poster).into(movieCardFavouriteView);
            }
            else{

                Picasso.with(itemView.getContext()).load(R.drawable.ic_star_border).placeholder(R.drawable.placeholder_backdrop).into(movieCardFavouriteView);
            }
        }
        public SmallImageRecyclerViewAdapterViewHolder(View itemView, SmallImageMovieClickListener listener) {
            super(itemView);
            this.itemView = itemView;
            movieCardPosterView = itemView.findViewById(R.id.movieCardPosterView);
            movieCardTitleView = itemView.findViewById(R.id.movieCardTitleView);
            movieCardFavouriteView = itemView.findViewById(R.id.movieCardFavouriteView);
            itemView.setOnClickListener(this);
            movieCardFavouriteView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if(view == movieCardFavouriteView){
                Boolean actionComplete = listener.onFavouriteClicked(position,isClicked);
                if(actionComplete){
                    Log.d("Button Clicked", "True");
                    toggleFavouriteButton();
                }
                else{
                    Log.d("Button Clicked", "False");
                }
            }
            else
                listener.onMovieClicked(position);
        }
    }
    public interface SmallImageMovieClickListener{
        void onMovieClicked(int position);
        Boolean onFavouriteClicked(int position,Boolean isClicked);
    }
}
