package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.model.Listing;

/**
 * Created by aguilarjp on 27/10/16.
 */

public class GetTopPostsTask extends AsyncTask<URL, Integer, Listing> {

    @Override
    protected Listing doInBackground(URL... params) {
        InputStream inputStream = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
            conn.setRequestMethod("GET");
            inputStream = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Listing listing = null;

        if (inputStream != null) {
            try {
                listing = new Parser().readJsonStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return listing;
    }
}
