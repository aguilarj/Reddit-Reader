package ar.edu.unc.famaf.redditreader.model;


import java.io.Serializable;

public class PostModel implements Serializable {
    private String mTitle;
    private String mAuthor;
    private long mCreatedTime;
    private int mCommentNumber;
    private String mThumbnail; // This is the image URL
    private String mSubreddit;
    private String mId;
    private String mPostHint;
    private String mURL;

    public PostModel() {}

    public PostModel(String mTitle, String mAuthor, long mCreatedTime, int mCommentNumber,
                     String mThumbnail, String mSubreddit, String mId, String mPostHint,
                     String mURL) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mCreatedTime = mCreatedTime;
        this.mCommentNumber = mCommentNumber;
        this.mThumbnail = mThumbnail;
        this.mSubreddit = mSubreddit;
        this.mId = mId;
        this.mPostHint = mPostHint;
        this.mURL = mURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public long getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(long createdTime) {
        mCreatedTime = createdTime;
    }

    public int getCommentNumber() {
        return mCommentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        mCommentNumber = commentNumber;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getSubreddit() {
        return mSubreddit;
    }

    public void setSubreddit(String subreddit) {
        mSubreddit = subreddit;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPostHint() {
        return mPostHint;
    }

    public void setPostHint(String mPostHint) {
        this.mPostHint = mPostHint;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String mImage) {
        this.mURL = mImage;
    }
}
