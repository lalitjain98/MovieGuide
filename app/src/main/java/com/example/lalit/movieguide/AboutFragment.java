package com.example.lalit.movieguide;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment implements View.OnClickListener {


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        ImageButton feedbackButton, githubLinkButton, shareButton;
        feedbackButton = view.findViewById(R.id.feedbackButton);
        githubLinkButton = view.findViewById(R.id.gitubLinkButton);
        shareButton = view.findViewById(R.id.shareButton);

        feedbackButton.setOnClickListener(this);
        githubLinkButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.feedbackButton :
                Intent feedback = new Intent();
                feedback.setAction(Intent.ACTION_SENDTO);
                feedback.putExtra(Intent.EXTRA_SUBJECT, "Movie Guide Feedback");
                feedback.setData(Uri.parse("mailto:jain.lalit138@gmail.com"));
                startActivity(feedback);
                break;
            case R.id.gitubLinkButton :
                Intent gitHubLink = new Intent();
                gitHubLink.setData(Uri.parse("https://github.com/lalitjain98/MovieGuideApp"));
                startActivity(gitHubLink);
                break;

            case R.id.shareButton :
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,"Download This App Now!");
                startActivity(share);
                break;
        }
    }
}
