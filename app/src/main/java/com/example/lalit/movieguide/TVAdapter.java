package com.example.lalit.movieguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jainl on 02-11-2017.
 */

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVAdapterViewHolder> {

    Context mContext;
    ArrayList<TV> mTVArrayList;
    TVClickListener mListener;
    private int POSTER_TYPE;
    public TVAdapter(Context context, ArrayList<TV> TVShows,int poster_type , TVClickListener listener){
        mContext = context;
        mTVArrayList = TVShows;
        mListener = listener;
        POSTER_TYPE = poster_type;
    }
    @Override
    public TVAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.tv_card_layout,parent,false);
        return new TVAdapterViewHolder(rowView,mListener);
    }

    @Override
    public void onBindViewHolder(TVAdapterViewHolder holder, int position) {
        TV tv = mTVArrayList.get(position);
        holder.TVCardTitleView.setText(tv.title);
        holder.isClicked = (FavouritesDatabase.getInstance(mContext).getTVDao().getTV(tv.id)) != null;
        if(holder.isClicked){
            Picasso.with(mContext).load(R.drawable.ic_star).into(holder.TVCardFavouriteView);
        }
        else{

            Picasso.with(mContext).load(R.drawable.ic_star_border).into(holder.TVCardFavouriteView);
        }

        if(POSTER_TYPE == Constants.KEY_POSTER_SMALL){
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/" + tv.posterPath).placeholder(R.drawable.placeholder_poster).into(holder.TVCardPosterView);
        }
        else if(POSTER_TYPE == Constants.KEY_POSTER_LARGE)
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w1280/" + tv.backdropPath).placeholder(R.drawable.placeholder_backdrop).into(holder.TVCardPosterView);
    }

    @Override
    public int getItemCount() {
        return mTVArrayList.size();
    }

    public static class TVAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView;
        TextView TVCardTitleView;
        ImageView TVCardPosterView;
        TVClickListener listener;
        ImageButton TVCardFavouriteView;
        Boolean isClicked;

        public void toggleFavouriteButton(){
            isClicked = !isClicked;
            if(isClicked){
                Picasso.with(itemView.getContext()).load(R.drawable.ic_star).into(TVCardFavouriteView);
            }
            else{

                Picasso.with(itemView.getContext()).load(R.drawable.ic_star_border).into(TVCardFavouriteView);
            }
        }

        public TVAdapterViewHolder(View itemView, TVClickListener listener ) {
            super(itemView);
            this.itemView = itemView;

            TVCardPosterView = itemView.findViewById(R.id.TVCardPosterView);
            TVCardTitleView = itemView.findViewById(R.id.TVCardTitleView);
            TVCardFavouriteView = itemView.findViewById(R.id.TVCardFavouriteView);
            TVCardFavouriteView.setOnClickListener(this);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(view == TVCardFavouriteView){
                Boolean actionComplete = listener.onFavouriteClicked(position,isClicked);
                if(actionComplete){
                    toggleFavouriteButton();
                }
            }
            else
                listener.onTVClicked(position);

        }
    }
    public interface TVClickListener{
        void onTVClicked(int position);
        Boolean onFavouriteClicked(int position, Boolean isClicked);
    }
}
