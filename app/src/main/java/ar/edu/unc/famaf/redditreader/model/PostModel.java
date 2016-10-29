package ar.edu.unc.famaf.redditreader.model;


public class PostModel {
    private String mTitle;
    private String mAuthor;
    private long mCreatedTime;
    private int mCommentNumber;
    private String mThumbnail; // This is the image URL
    private String mSubreddit;
    private String mId;

    public PostModel() {
    }

    public PostModel(String title, String author, long createdTime, int commentNumber, String thumbnail, String subreddit, String id) {
        mTitle = title;
        mAuthor = author;
        mCreatedTime = createdTime;
        mCommentNumber = commentNumber;
        mThumbnail = thumbnail;
        mSubreddit = subreddit;
        mId = id;
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
}
