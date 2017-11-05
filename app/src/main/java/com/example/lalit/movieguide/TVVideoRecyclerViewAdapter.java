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

public class TVVideoRecyclerViewAdapter extends RecyclerView.Adapter<TVVideoRecyclerViewAdapter.VideoViewHolder> {
    Context mContext;
    ArrayList<TVDetailVideosResponse.TVVideo> mTVVideoArrayList;
    VideoClickedListener mListener;
    public TVVideoRecyclerViewAdapter(Context context, ArrayList<TVDetailVideosResponse.TVVideo> videos, VideoClickedListener listener) {
        mContext = context;
        mTVVideoArrayList = videos;
        mListener = listener;
    }


    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.trailer_row_layout,parent,false);
        return new VideoViewHolder(rowView,mListener);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        String trailerKey = mTVVideoArrayList.get(position).key;
        Picasso.with(mContext).load("http://img.youtube.com/vi/"+trailerKey+"/hqdefault.jpg").into(holder.videoPoster);
        holder.videoTitle.setText(mTVVideoArrayList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mTVVideoArrayList.size();
    }
    //http://img.youtube.com/vi/trailer_key/hqdefault.jpg
    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView;
        TextView videoTitle;
        ImageView videoPoster;
        VideoClickedListener mListener;
        public VideoViewHolder(View itemView, VideoClickedListener listener) {
            super(itemView);
            this.itemView = itemView;
            mListener = listener;
            videoTitle = itemView.findViewById(R.id.videoCardTitleView);
            videoPoster = itemView.findViewById(R.id.videoCardPosterView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onVideoClicked(position);
        }
    }
    public interface VideoClickedListener {
        void onVideoClicked(int position);
    }
}
