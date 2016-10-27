package ar.edu.unc.famaf.redditreader.model;

import java.util.List;

/**
 * Created by aguilarjp on 27/10/16.
 */

public class Listing {
    private List<PostModel> mChildren;
    private String mAfter;
    private String mBefore;

    public Listing() {
    }

    public Listing(List<PostModel> children, String after, String before) {
        mChildren = children;
        mAfter = after;
        mBefore = before;
    }

    public List<PostModel> getChildren() {
        return mChildren;
    }

    public void setChildren(List<PostModel> children) {
        mChildren = children;
    }

    public String getAfter() {
        return mAfter;
    }

    public void setAfter(String after) {
        mAfter = after;
    }

    public String getBefore() {
        return mBefore;
    }

    public void setBefore(String before) {
        mBefore = before;
    }
}