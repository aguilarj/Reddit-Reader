package ar.edu.unc.famaf.redditreader.backend;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by aguilarjp on 11/11/16.
 */

public interface PostsIteratorListener {
    void downloadError();
    void setAdapter(List<PostModel> posts);
    void nextPosts(List<PostModel> posts);
}
