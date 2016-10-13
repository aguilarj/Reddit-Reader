package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by aguilarjp on 10/10/16.
 */

public class PostAdapter extends ArrayAdapter {
    private List<PostModel> mPostModelList;

    private static class ViewHolder {
        TextView author;
        TextView commentNumber;
        TextView title;
        TextView date;
        TextView subreddit;
        ImageView image;
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
        } else
            holder = (ViewHolder) convertView.getTag();

        // Getting views
        PostModel postModel = mPostModelList.get(position);

        // Using the post data in the view
        holder.author.setText(getContext().getString(R.string.author, postModel.getAuthor()));
        holder.commentNumber.setText(getContext().getString(R.string.comment_number, postModel.getCommentNumber()));
        holder.title.setText(postModel.getTitle());
        holder.date.setText(getDateDifference(postModel.getDate(), new Date()));
        holder.subreddit.setText(postModel.getSubreddit());
        holder.image.setImageResource(postModel.getThumbnail());
        return convertView;
    }

    private String getDateDifference(Date startDate, Date endDate){

        // Millisecondsseconds
        long first = endDate.getTime();
        long second = startDate.getTime();

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilliseconds = 1000;
        long minutesInMilliseconds = secondsInMilliseconds * 60;
        long hoursInMilliseconds = minutesInMilliseconds * 60;
        long daysInMilliseconds = hoursInMilliseconds * 24;

        long elapsedDays = different / daysInMilliseconds;

        different = different % daysInMilliseconds;

        long elapsedHours = different / hoursInMilliseconds;
        different = different % hoursInMilliseconds;

        long elapsedMinutes = different / minutesInMilliseconds;
        different = different % minutesInMilliseconds;

        long elapsedSeconds = different / secondsInMilliseconds;

        if (elapsedDays > 0)
            return String.valueOf(elapsedDays) + " days ago";
        if (elapsedHours > 0)
            return String.valueOf(elapsedHours) + " hours ago";
        if (elapsedMinutes > 0)
            return String.valueOf(elapsedHours) + " minutes ago.";
        if (elapsedSeconds > 0)
            return String.valueOf(elapsedSeconds) + " seconds ago";

        return "recently";
    }

    @Override
    public boolean isEmpty() {
        return mPostModelList.isEmpty();
    }
}
