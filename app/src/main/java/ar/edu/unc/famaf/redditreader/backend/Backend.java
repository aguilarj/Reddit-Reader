package ar.edu.unc.famaf.redditreader.backend;

import java.net.MalformedURLException;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.model.Listing;

public class Backend {

    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {}

    public void getTopPostTask(final GetTopPostsListener listener) {
        URL url = null;
        try {
            url = new URL("https://www.reddit.com/top/.json?limit=50");
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }

        new GetTopPostsTask() {
            @Override
            protected void onPostExecute(Listing listing) {
                listener.getPosts(listing.getChildren());
            }
        }.execute(url);
    }
}
