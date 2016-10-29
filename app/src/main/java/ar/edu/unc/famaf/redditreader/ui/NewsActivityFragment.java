package ar.edu.unc.famaf.redditreader.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.GetTopPostsListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements GetTopPostsListener {
    ListView listView = null;

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_news, container, false);

        listView = (ListView) rootView.findViewById(R.id.posts_list_view);

        Backend backend = Backend.getInstance();
        backend.getTopPostTask(this);

         return rootView;
    }

    @Override
    public void getPosts(List<PostModel> postModels) {
        PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_model, postModels);
        listView.setAdapter(adapter);
    }
}
