package ar.edu.unc.famaf.redditreader.backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public List<PostModel> getTopPosts() {
        List<PostModel> result = new ArrayList<>();

        Calendar calendar = Calendar.getInstance(); // Calendar used to get dates

        // POST MODEL 1
        PostModel p1 = new PostModel();
        p1.setAuthor("bengaldude545");
        p1.setThumbnail("https://a.thumbs.redditmedia.com/0e0cKrikx0u2EeCT6Di9LgLXrE2WVICdJpAw1Tj4Ka8.jpg");
        p1.setCommentNumber(1186);

        // Configuring date for this particular post
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);

        p1.setDate(calendar.getTime());
        p1.setTitle("Bird thinks guy is a tree");
        p1.setSubreddit("/r/funny");

        // POST MODEL 2
        PostModel p2 = new PostModel();
        p2.setAuthor("rahul8aggarwal");
        p2.setThumbnail("https://a.thumbs.redditmedia.com/1xJJxofAvAglF0XsQzx0z-kI-NcXf62wLaH5VLkEcc0.jpg");
        p2.setCommentNumber(887);

        // Configuring date for this particular post
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 5);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 13);

        p2.setDate(calendar.getTime());
        p2.setTitle("Henceforth, he lived happily ever after...");
        p2.setSubreddit("/r/aww");

        // POST MODEL 3
        PostModel p3 = new PostModel();
        p3.setAuthor("el_matto");
        p3.setThumbnail("https://b.thumbs.redditmedia.com/L_P-6sg6mPZ36PW0cN00tEx62W6aeyYqjH9hdMTLyos.jpg");
        p3.setCommentNumber(1154);

        // Configuring date for this particular post
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 34);

        p3.setDate(calendar.getTime());
        p3.setTitle("Firefighters are fed up with you");
        p3.setSubreddit("/r/funny");

        // POST MODEL 4
        PostModel p4 = new PostModel();
        p4.setAuthor("SlimJones123");
        p4.setThumbnail("https://b.thumbs.redditmedia.com/_gxcq7uLcFHkAmCvBf9hLQ-wMDMgTYBuXtRGZsixL2w.jpg");
        p4.setCommentNumber(1215);

        // Configuring date for this particular post
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 37);

        p4.setDate(calendar.getTime());
        p4.setTitle("Looks like we got another stow away");
        p4.setSubreddit("/r/gifs");

        // POST MODEL 5
        PostModel p5 = new PostModel();
        p5.setAuthor("lacajadeldiablo");
        p5.setThumbnail("https://b.thumbs.redditmedia.com/fAS3pQnydmpsjIM3MuH9sDNVrF3e4WbwVN9moMaoR3w.jpg");
        p5.setCommentNumber(735);

        // Configuring date for this particular post
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 12);

        p5.setDate(calendar.getTime());
        p5.setTitle("$100 bed");
        p5.setSubreddit("/r/aww");

        // Saving models

        result.add(p1);
        result.add(p2);
        result.add(p3);
        result.add(p4);
        result.add(p5);

        return result;
    }
}
