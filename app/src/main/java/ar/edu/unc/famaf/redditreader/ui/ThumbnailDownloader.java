package ar.edu.unc.famaf.redditreader.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.R;

/**
 * Created by aguilarjp on 15/10/16.
 */

public class ThumbnailDownloader {
    private static final String TAG = "ThumbnailDownloader";
    private LruCache<String, Bitmap> mMemoryCache;

    public ThumbnailDownloader() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    // Cache methods
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    // Class to bind a task with an ImageView
    private class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<Task> taskReference;

        private DownloadedDrawable(Task task) {
            taskReference = new WeakReference<>(task);
        }

        private Task getTask() {
            return taskReference.get();
        }
    }

    // This is the method used to download
    public void download(String url, ImageView imageView, ProgressBar progressBar, String id) {
        if (cancelPotentialDownload(url, imageView)) {
            final Bitmap bitmap = getBitmapFromMemCache(id);

            if (bitmap != null) {
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap); // The bitmap was previously downloaded
                Log.i(TAG, "Bitmap loaded from cache");
            } else {
                Task task = new Task(imageView, progressBar, id);
                DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
                imageView.setImageDrawable(downloadedDrawable); // This drawable actually links
                                                                // the task with the imageView
                task.execute(url);
                Log.i(TAG, "Bitmap downloaded");
            }
        }
    }

    // Returns the task associated with imageView. If there isn't an associated task, returns null.
    private Task getTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
                return downloadedDrawable.getTask();
            }
        }
        return null;
    }

    /*
     * Returns true if there isn't a task for this imageView or if it's necessary to cancel
     * the task associated to imageView because the url was for an older post.
     */
    private boolean cancelPotentialDownload(String url, ImageView imageView) {
        Task task = getTask(imageView);

        if (task != null) {
            String bitmapUrl = task.url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                task.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    // The AsyncTask used actually download the thumbnail.
    public class Task extends AsyncTask<String, Integer, Bitmap> {
        public String url = null;
        private String postId;
        private final WeakReference<ImageView> imageViewReference;
        private final WeakReference<ProgressBar> progressBarReference;


        public Task(ImageView imageView, ProgressBar progressBar, String id) {
            imageViewReference = new WeakReference<>(imageView);
            progressBarReference = new WeakReference<>(progressBar);
            postId = id;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap;
            HttpURLConnection connection;
            URL url;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                Log.e(TAG, "Bad URL: " + e.getMessage());
                return null;
            }

            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            } catch (IOException e) {
                Log.e(TAG, "Connection error: " + e.getMessage());
                return null;
            }
            addBitmapToMemoryCache(postId, bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }
            if (imageViewReference != null && progressBarReference != null) {

                ProgressBar progressBar = progressBarReference.get();
                ImageView imageView = imageViewReference.get();

                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);

                if (bitmap != null) {
                    Task task = getTask(imageView);

                    // Change bitmap only if this process is still associated with it
                    if (this == task) {
                        imageView.setImageBitmap(bitmap);
                    }
                } else {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
        }
    }
}
