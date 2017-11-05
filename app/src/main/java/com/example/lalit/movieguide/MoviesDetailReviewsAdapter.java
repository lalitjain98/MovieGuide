package com.example.lalit.movieguide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jainl on 04-11-2017.
 */

public class MoviesDetailReviewsAdapter extends RecyclerView.Adapter<MoviesDetailReviewsAdapter.ReviewViewHolder>{

    Context mContext;
    ArrayList<Review> mMovieReviewArrayListArrayList;
    MoviesDetailReviewsAdapter.ReviewClickListener mListener;
    public MoviesDetailReviewsAdapter(Context context, ArrayList<Review> reviewsList, MoviesDetailReviewsAdapter.ReviewClickListener listener) {
        mContext = context;
        mMovieReviewArrayListArrayList = reviewsList;
        mListener = listener;
    }


    public MoviesDetailReviewsAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.review_row_layout,parent,false);
        return new MoviesDetailReviewsAdapter.ReviewViewHolder(rowView,mListener);
    }

    @Override
    public void onBindViewHolder(MoviesDetailReviewsAdapter.ReviewViewHolder holder, int position) {
        Review review = mMovieReviewArrayListArrayList.get(position);
        holder.reviewAuthor.setText(review.author);
        holder.reviewContent.setText(review.content);
    }

    @Override
    public int getItemCount() {
        return mMovieReviewArrayListArrayList.size();
    }

    interface ReviewClickListener {
        void onReviewClicked(int position);
    }
    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView;
        TextView reviewAuthor, reviewContent;
        MoviesDetailReviewsAdapter.ReviewClickListener mListener;
        public ReviewViewHolder(View itemView, MoviesDetailReviewsAdapter.ReviewClickListener listener) {
            super(itemView);
            this.itemView = itemView;
            mListener = listener;
            reviewAuthor = itemView.findViewById(R.id.reviewAuthor);
            reviewContent = itemView.findViewById(R.id.reviewContent);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onReviewClicked(position);
        }
    }
}
