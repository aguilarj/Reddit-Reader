package ar.edu.unc.famaf.redditreader.backend;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by aguilarjp on 28/10/16.
 */

public interface GetTopPostsListener {
    void downloadError();
    void setAdapter(List<PostModel> postModels);
}
