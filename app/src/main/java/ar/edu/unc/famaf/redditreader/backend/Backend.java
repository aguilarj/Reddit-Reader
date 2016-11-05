package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.MalformedURLException;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.model.Listing;

import static java.security.AccessController.getContext;

public class Backend {

    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPostTask(final GetTopPostsListener listener, Context context) {
        URL url = null;

        final RedditDB redditDB = new RedditDB(context);

        try {
            url = new URL("https://www.reddit.com/top/.json?limit=50");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new GetTopPostsTask() {
            @Override
            protected void onPostExecute(Listing listing) {
                if (listing != null) { // InputStream download was successful
                    redditDB.deletePosts();
                    redditDB.addPosts(listing);
                    listener.setAdapter(redditDB.getPosts());
                } else { // Download error
                    if (redditDB.noPosts()) {
                        listener.downloadError();
                    } else { // Get old posts from database
                        listener.setAdapter(redditDB.getPosts());
                    }
                }
            }
        }.execute(url);
    }
}
