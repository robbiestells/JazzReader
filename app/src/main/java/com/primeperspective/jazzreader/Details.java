package com.primeperspective.jazzreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import adapters.FeedAdapter;
import models.FeedItem;
import utilities.Utilities;

import static android.R.attr.mimeType;
import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.primeperspective.jazzreader.R.id.postArticle;
import static com.primeperspective.jazzreader.R.id.postTitle;
import static com.primeperspective.jazzreader.R.id.toolbar;

/**
 * Created by rob on 3/7/17.
 */

public class Details extends AppCompatActivity {
    MainActivity activity;
    FeedItem feedItem;
    String feedItemId;
    ImageView feedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        feedItemId = intent.getStringExtra("itemId");

        feedItem = Utilities.getSavedItem(this, feedItemId);

        TextView postTitle = (TextView) findViewById(R.id.postTitle);
        TextView postLink = (TextView) findViewById(R.id.postLink);
        WebView postArticle = (WebView) findViewById(R.id.postArticle);

        postTitle.setText(feedItem.getTitle());

        postLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(feedItem.getLink()));
                startActivity(browserIntent);
            }
        });

        String html = feedItem.getArticle();
        postArticle.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");

        feedImage = (ImageView) findViewById(R.id.postImage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            feedImage.setTransitionName("transition" + feedItem.getId());
        }
        Glide.with(this).load(feedItem.getImageLinkRetina()).into(feedImage);

      //  this.getSupportActionBar().setTitle(feedItem.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

}
