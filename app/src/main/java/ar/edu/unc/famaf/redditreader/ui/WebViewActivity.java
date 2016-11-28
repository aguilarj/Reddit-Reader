package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ar.edu.unc.famaf.redditreader.R;

public class WebViewActivity extends AppCompatActivity {
    public static final String POST_LINK = "post_link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);
        setContentView(webView);

        WebSettings webSettings = webView.getSettings();

        // Enable JavaScript
        webSettings.setJavaScriptEnabled(true);

        // Navegate only inside the WebView
        webView.setWebViewClient(new WebViewClient());

        // Enable zoom. controls
        webView.getSettings().setBuiltInZoomControls(true);


        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();

        webView.loadUrl(extras.getString(POST_LINK).replace("&amp;", "&"));

    }
}
