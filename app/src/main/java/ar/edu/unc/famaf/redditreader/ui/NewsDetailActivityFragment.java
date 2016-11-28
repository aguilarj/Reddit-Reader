package ar.edu.unc.famaf.redditreader.ui;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDB;
import ar.edu.unc.famaf.redditreader.model.PostModel;

import static android.content.ContentValues.TAG;

public class NewsDetailActivityFragment extends Fragment {


    public NewsDetailActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_news_detail_activity, container, false);

        PostModel post = (PostModel) getArguments().getSerializable(NewsDetailActivity.EXTRA_POST);

        TextView author = (TextView) rootview.findViewById(R.id.post_detail_author);
        TextView title = (TextView) rootview.findViewById(R.id.post_detail_title);
        TextView subreddit = (TextView) rootview.findViewById(R.id.post_detail_subreddit);
        TextView created_time = (TextView) rootview.findViewById(R.id.post_detail_created_time);
        ImageView image = (ImageView) rootview.findViewById(R.id.post_detail_image);
        ProgressBar progressBar = (ProgressBar) rootview.findViewById(R.id.post_detail_progress_bar);

        author.setText(post.getAuthor());
        title.setText(post.getTitle());
        subreddit.setText(post.getSubreddit());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        created_time.setText(sdf.format(new Date(post.getCreatedTime())));

        if (post.getPostHint().equals("image")) {
            progressBar.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);

            RedditDB redditDB = new RedditDB(getContext());
            Bitmap thumbnail = redditDB.getImageBitmap(post.getId());

            if (thumbnail != null) {
                image.setImageBitmap(thumbnail);
                progressBar.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);

                Log.d(TAG, "Bitmap loaded from database");
            } else {
                ImageDownloader.download(post.getImage(),
                        image,
                        progressBar,
                        post.getId(),
                        getContext(),
                        ImageDownloader.DOWNLOAD_IMAGE);
            }
        }

        return rootview;
    }

}
