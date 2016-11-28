package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
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
            values.put(RedditDBHelper.POST_TABLE_THUMBNAIL_URL, postModel.getThumbnail());
            values.put(RedditDBHelper.POST_TABLE_SUBREDDIT, postModel.getSubreddit());
            values.put(RedditDBHelper.POST_TABLE_POST_ID, postModel.getId());
            values.put(RedditDBHelper.POST_TABLE_POST_HINT, postModel.getPostHint());
            values.put(RedditDBHelper.POST_TABLE_IMAGE_URL, postModel.getImage());
            values.putNull(RedditDBHelper.POST_TABLE_THUMBNAIL_BITMAP);
            values.putNull(RedditDBHelper.POST_TABLE_IMAGE_BITMAP);

            // Insert the new row, returning the primary key value of the new row
            writableDatabase.insert(RedditDBHelper.POST_TABLE, null, values);
        }
    }

    public void deletePosts() {
        writableDatabase.delete(RedditDBHelper.POST_TABLE, null, null);
    }

    public List<PostModel> getPosts(int start, int end) {
        List<PostModel> postModels = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + RedditDBHelper.POST_TABLE +
                             " LIMIT " + Integer.toString(start) + ", " + Integer.toString(end);

        Log.d(TAG, selectQuery);

        Cursor c = readableDatabase.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                PostModel postModel = new PostModel();

                postModel.setTitle(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_TITLE))));
                postModel.setAuthor(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_AUTHOR))));
                postModel.setCreatedTime(c.getInt((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_CREATED_TIME))));
                postModel.setCommentNumber(c.getInt((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_COMMENT_NUMBER))));
                postModel.setThumbnail(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_THUMBNAIL_URL))));
                postModel.setSubreddit(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_SUBREDDIT))));
                postModel.setId(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_POST_ID))));
                postModel.setPostHint(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_POST_HINT))));
                postModel.setImage(c.getString((c.getColumnIndexOrThrow(RedditDBHelper.POST_TABLE_IMAGE_URL))));

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

    private Bitmap getBitmap(String postId, String column) {
        String selectQuery =  "SELECT " + column +
                " FROM " + RedditDBHelper.POST_TABLE +
                " WHERE " + RedditDBHelper.POST_TABLE_POST_ID + "=" + "'" + postId + "'";

        Cursor c = readableDatabase.rawQuery(selectQuery, null);
        c.moveToFirst();

        Bitmap bitmap = null;

        if(!c.isNull(c.getColumnIndexOrThrow(column))) {
            byte[] bitmapInBytes = c.getBlob(c.getColumnIndexOrThrow(column));
            bitmap = getImage(bitmapInBytes);
        }

        c.close();

        return bitmap;
    }

    public Bitmap getThumbnailBitmap (String postId) {
        return getBitmap(postId, RedditDBHelper.POST_TABLE_THUMBNAIL_BITMAP);
    }

    public Bitmap getImageBitmap (String postId) {
        return getBitmap(postId, RedditDBHelper.POST_TABLE_IMAGE_BITMAP);
    }

    private void addBitmap(String postId, String column, Bitmap thumbnail) {
        ContentValues values = new ContentValues();

        values.put(column, getBytes(thumbnail));

        String whereClause = RedditDBHelper.POST_TABLE_POST_ID + "=" + "'" + postId + "'";
        writableDatabase.update(RedditDBHelper.POST_TABLE, values, whereClause, null);
    }

    public void addThumbnailToPost(String postId, Bitmap thumbnail) {
        addBitmap(postId, RedditDBHelper.POST_TABLE_THUMBNAIL_BITMAP, thumbnail);
    }

    public void addImageToPost(String postId, Bitmap image) {
        addBitmap(postId, RedditDBHelper.POST_TABLE_IMAGE_BITMAP, image);
    }

    private static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
