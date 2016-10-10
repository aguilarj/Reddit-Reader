package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

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
        return super.getView(position, convertView, parent);
    }

    @Override
    public boolean isEmpty() {
        return mPostModelList.isEmpty();
    }
}
