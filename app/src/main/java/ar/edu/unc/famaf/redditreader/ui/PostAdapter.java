package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDB;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by aguilarjp on 10/10/16.
 */

public class PostAdapter extends ArrayAdapter {
    private static final String TAG = "PostAdapter";

    private List<PostModel> mPostModelList;

    private static class ViewHolder {
        TextView author;
        TextView commentNumber;
        TextView title;
        TextView date;
        TextView subreddit;
        ImageView image;
        ProgressBar progressBar;
    }

    public PostAdapter(Context context, int resource, List<PostModel> postModelList) {
        super(context, resource);
        mPostModelList = postModelList;
    }

    @Override
    public int getCount() {
        return mPostModelList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return mPostModelList.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return mPostModelList.indexOf(item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null)  {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.post_model, null);
        }
        if (convertView.getTag() == null) {
            holder = new ViewHolder();
            holder.author = (TextView) convertView.findViewById(R.id.post_author);
            holder.commentNumber = (TextView) convertView.findViewById(R.id.post_comment_number);
            holder.title = (TextView) convertView.findViewById(R.id.post_title);
            holder.date = (TextView) convertView.findViewById(R.id.post_date);
            holder.subreddit = (TextView) convertView.findViewById(R.id.post_subreddit);
            holder.image = (ImageView) convertView.findViewById(R.id.post_image);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.post_progress_bar);
        } else
            holder = (ViewHolder) convertView.getTag();

        // Getting views
        PostModel postModel = mPostModelList.get(position);

        // Using the post data in the view
        holder.author.setText(getContext().getString(R.string.author, postModel.getAuthor()));
        holder.commentNumber.setText(getContext().getString(R.string.comment_number, postModel.getCommentNumber()));
        holder.title.setText(postModel.getTitle());

        long now = System.currentTimeMillis();
        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(postModel.getCreatedTime(), now, DateUtils.MINUTE_IN_MILLIS);

        holder.date.setText(relativeTime);
        holder.subreddit.setText(postModel.getSubreddit());

        holder.progressBar.setVisibility(View.VISIBLE);
        holder.image.setVisibility(View.GONE);

        RedditDB redditDB = new RedditDB(getContext());
        Bitmap thumbnail = redditDB.getBitmapFromPost(postModel.getId());

        if (thumbnail != null) {
            holder.image.setImageBitmap(thumbnail);
            holder.progressBar.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);

            Log.d(TAG, "Bitmap loaded from database");
        } else {
            ThumbnailDownloader.download(postModel.getThumbnail(),
                    holder.image,
                    holder.progressBar,
                    postModel.getId(),
                    getContext());
        }

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mPostModelList.isEmpty();
    }
}
