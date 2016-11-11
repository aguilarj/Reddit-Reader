package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.backend.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements PostsIteratorListener {
    ListView listView = null;
    PostAdapter adapter = null;
    List<PostModel> posts = null;

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_news, container, false);

        listView = (ListView) rootView.findViewById(R.id.posts_list_view);

        // Fetch the first posts
        Backend.getInstance().getNextPosts(this, getContext());

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

                Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext());

                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        return rootView;
    }

    @Override
    public void nextPosts(List<PostModel> nextPosts) {
        posts.addAll(nextPosts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(List<PostModel> firstPosts) {
        posts = firstPosts;

        adapter = new PostAdapter(getContext(), R.layout.post_model, posts);
        listView.setAdapter(adapter);
    }

    @Override
    public void downloadError() {
        new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage("Network unavailable")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) getContext()).finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
