package ar.edu.unc.famaf.redditreader.model;


import java.util.Date;

public class PostModel {
    private String mTitle;
    private String mAuthor;
    private Date mDate;
    private int mCommentNumber;
    private String mThumbnail; // This is the image URL
    private String mSubreddit;
    private String mId;

    public PostModel() {
    }

    public PostModel(String title, String author, Date date, int commentNumber, String thumbnail, String subreddit, String id) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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
