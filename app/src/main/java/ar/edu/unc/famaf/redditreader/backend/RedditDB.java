package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by aguilarjp on 03/11/16.
 */

public class RedditDB {
    private static final String TAG = "RedditDB";

    private SQLiteDatabase writableDatabase;
    private SQLiteDatabase readableDatabase;

    private RedditDBHelper mRedditDBHelper = null;

    public RedditDB(Context context) {
        mRedditDBHelper = new RedditDBHelper(context);

        writableDatabase = mRedditDBHelper.getWritableDatabase();
        readableDatabase = mRedditDBHelper.getReadableDatabase();
    }

    public void addPosts(Listing listing) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        for (PostModel postModel: listing.getChildren()) {
            values.put(RedditDBHelper.POST_TABLE_TITLE, postModel.getTitle());
            values.put(RedditDBHelper.POST_TABLE_AUTHOR, postModel.getAuthor());
            values.put(RedditDBHelper.POST_TABLE_CREATED_TIME, postModel.getCreatedTime());
            values.put(RedditDBHelper.POST_TABLE_COMMENT_NUMBER, postModel.getCommentNumber());
            values.put(RedditDBHelper.POST_TABLE_THUMBNAIL, postModel.getThumbnail());
            values.put(RedditDBHelper.POST_TABLE_SUBREDDIT, postModel.getSubreddit());
            values.put(RedditDBHelper.POST_TABLE_POST_ID, postModel.getId());

            // Insert the new row, returning the primary key value of the new row
            writableDatabase.insert(RedditDBHelper.POST_TABLE, null, values);
        }
    }

    public void deletePosts() {
        writableDatabase.delete(RedditDBHelper.POST_TABLE, null, null);
    }

    public List<PostModel> getPosts() {
        List<PostModel> postModels = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + RedditDBHelper.POST_TABLE;
        Log.e(TAG, selectQuery);

        Cursor c = readableDatabase.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                PostModel postModel = new PostModel();

                postModel.setTitle(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_TITLE))));
                postModel.setAuthor(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_AUTHOR))));
                postModel.setCreatedTime(c.getInt((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_CREATED_TIME))));
                postModel.setCommentNumber(c.getInt((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_COMMENT_NUMBER))));
                postModel.setThumbnail(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_THUMBNAIL))));
                postModel.setSubreddit(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_SUBREDDIT))));
                postModel.setId(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_POST_ID))));

                postModels.add(postModel);
            } while(c.moveToNext());
        }

        c.close();

        return postModels;
    }

    public boolean noPosts() {
        String selectQuery = "SELECT * FROM " + RedditDBHelper.POST_TABLE;

        Cursor c = readableDatabase.rawQuery(selectQuery, null);
        boolean result = c.moveToFirst();
        c.close();

        return !result;
    }
}
