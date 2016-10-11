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
        if(convertView == null)  {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.post_model, null);
        }

        // Getting views
        PostModel postModel = mPostModelList.get(position);
        TextView author = (TextView) convertView.findViewById(R.id.post_author);
        TextView commentNumber = (TextView) convertView.findViewById(R.id.post_comment_number);
        TextView title = (TextView) convertView.findViewById(R.id.post_title);
        TextView date = (TextView) convertView.findViewById(R.id.post_date);
        TextView subreddit = (TextView) convertView.findViewById(R.id.post_subreddit);
        ImageView image = (ImageView) convertView.findViewById(R.id.post_image);

        // Using the post data in the view
        author.setText(getContext().getString(R.string.author, postModel.getAuthor()));
        commentNumber.setText(getContext().getString(R.string.comment_number, postModel.getCommentNumber()));
        title.setText(postModel.getTitle());
        date.setText(getDateDifference(postModel.getDate(), new Date()));
        subreddit.setText(postModel.getSubreddit());

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
