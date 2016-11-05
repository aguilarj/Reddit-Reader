package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aguilarjp on 03/11/16.
 */

public class RedditDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "RedditDBHelper";

    public static final String DATABASE_NAME = "redditdb.db";
    public static final int DATABASE_VERSION = 1;

    public static final String POST_TABLE_ID = "_id";
    public static final String POST_TABLE = "post";
    public static final String POST_TABLE_TITLE = "title";
    public static final String POST_TABLE_AUTHOR = "author";
    public static final String POST_TABLE_CREATED_TIME = "created_time";
    public static final String POST_TABLE_COMMENT_NUMBER = "comment_number";
    public static final String POST_TABLE_THUMBNAIL = "thumbnail";
    public static final String POST_TABLE_SUBREDDIT = "subreddit";
    public static final String POST_TABLE_POST_ID = "id";

    public RedditDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = " create table " + POST_TABLE + " ( "
                + POST_TABLE_ID + " integer primary key autoincrement, "
                + POST_TABLE_TITLE + " text not null, "
                + POST_TABLE_AUTHOR + " text not null, "
                + POST_TABLE_CREATED_TIME + " integer not null, "
                + POST_TABLE_COMMENT_NUMBER + " integer not null, "
                + POST_TABLE_THUMBNAIL + " text not null, "
                + POST_TABLE_SUBREDDIT + " text not null, "
                + POST_TABLE_POST_ID + " text not null"
                + " );";

        Log.d(TAG, createSentence);
        db.execSQL(createSentence);
        Log.d(TAG, "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Database updated");
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE);
        this.onCreate(db);
    }
}
