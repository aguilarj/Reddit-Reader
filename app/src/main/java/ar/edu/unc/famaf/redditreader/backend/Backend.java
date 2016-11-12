package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;

import java.net.MalformedURLException;
import java.net.URL;
import ar.edu.unc.famaf.redditreader.model.Listing;

public class Backend {

    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }


    private static final int MAX_POSTS = 50;

    private static final int POSTS_PER_PAGE = 5;

    private int nextIndex = -1;
    
    private Backend() {
    }

    public void getTopPostTask(final PostsIteratorListener listener, Context context) {
        URL url = null;

        final RedditDB redditDB = new RedditDB(context);

        try {
            url = new URL("https://www.reddit.com/top/.json?limit=" + Integer.toString(MAX_POSTS));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new GetTopPostsTask() {
            @Override
            protected void onPostExecute(Listing listing) {

                if (listing != null) { // InputStream download was successful
                    redditDB.deletePosts();
                    redditDB.addPosts(listing);

                    listener.setAdapter(redditDB.getPosts(0, POSTS_PER_PAGE));

                    nextIndex = POSTS_PER_PAGE;

                } else { // Download error
                    if (redditDB.noPosts()) {
                        listener.downloadError();
                    } else { // Get old posts from database
                        listener.setAdapter(redditDB.getPosts(0, POSTS_PER_PAGE));

                        nextIndex = POSTS_PER_PAGE;
                    }
                }
            }
        }.execute(url);
    }

    public void getNextPosts(final PostsIteratorListener listener, Context context) {
        RedditDB redditDB = new RedditDB(context);

        if (nextIndex == -1) {
            getTopPostTask(listener, context);
        } else {
            listener.nextPosts(redditDB.getPosts(nextIndex, POSTS_PER_PAGE));
            nextIndex += POSTS_PER_PAGE;
        }
    }
}
