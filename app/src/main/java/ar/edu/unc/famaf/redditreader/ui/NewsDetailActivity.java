package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.edu.unc.famaf.redditreader.R;

public class NewsDetailActivity extends AppCompatActivity {
    public final static String EXTRA_POST = "ar.edu.unc.famaf.redditreader.ui.post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent intent = this.getIntent();

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_POST, intent.getSerializableExtra(EXTRA_POST));

        NewsDetailActivityFragment newsDetailActivityFragment = new NewsDetailActivityFragment();
        newsDetailActivityFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.post_detail_container, newsDetailActivityFragment)
                                   .commit();
    }
}
