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
 * Created by jainl on 02-11-2017.
 */

public class TVCastRecyclerViewAdapter extends RecyclerView.Adapter<TVCastRecyclerViewAdapter.TVCastRecyclerViewAdapterViewHolder>{

    Context mContext;
    ArrayList<TVDetailCastResponse.TVCast> mTVCastArrayList;
    CastClickedListener mListener;

    public TVCastRecyclerViewAdapter(Context context, ArrayList<TVDetailCastResponse.TVCast> casts, CastClickedListener listener){
        mContext = context;
        mTVCastArrayList = casts;
        mListener = listener;
    }

    @Override
    public TVCastRecyclerViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.cast_card_layout,parent,false);
        return new TVCastRecyclerViewAdapterViewHolder(rowView,mListener);
    }

    @Override
    public void onBindViewHolder(TVCastRecyclerViewAdapterViewHolder holder, int position) {
        TVDetailCastResponse.TVCast cast = mTVCastArrayList.get(position);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342" + cast.profilePath).into(holder.castCardProfileView);
        holder.castCardCharacterView.setText(cast.character);
        holder.castCardNameView.setText(cast.name);
    }

    @Override
    public int getItemCount() {
        return mTVCastArrayList.size();
    }

    interface CastClickedListener{
        void onCastClicked(int position);
    }
    public class TVCastRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView;
        ImageView castCardProfileView;
        TextView castCardNameView, castCardCharacterView;
        CastClickedListener castClickedListener;
        public TVCastRecyclerViewAdapterViewHolder(View itemView, CastClickedListener listener) {
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
