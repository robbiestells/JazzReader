package com.primeperspective.jazzreader;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import adapters.FeedAdapter;
import models.FeedItem;

import static android.R.attr.mimeType;
import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.primeperspective.jazzreader.R.id.postArticle;
import static com.primeperspective.jazzreader.R.id.toolbar;

/**
 * Created by rob on 3/7/17.
 */

public class Details extends AppCompatActivity {

    FeedItem feedItem;
    ImageView feedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        feedItem = (FeedItem) intent.getParcelableExtra("item");

        TextView postTitle = (TextView) findViewById(R.id.postTitle);
        TextView postLink = (TextView) findViewById(R.id.postLink);
        WebView postArticle = (WebView) findViewById(R.id.postArticle);

        postTitle.setText(feedItem.getTitle());
        postLink.setText(feedItem.getLink());


        String html = "<p class=\"p3\" style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 22px; vertical-align: baseline;\"><span class=\"s1\" style=\"margin: 0px; padding: 0px; border: 0px; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-caps: inherit; line-height: inherit; vertical-align: baseline; display: inline !important; float: none !important;\">This album is not an effort to break down barriers, or forge into unknown territory, but a clear communicative statement of their own personal musical heritage within the jazz tradition. The music incorporates the blues, swings hard, and contains elements that speak to their music as being a confluence of jazz inroads that become a pathway for musical integration that speaks to itself as one. While many jazz artists these days refute what is undeniably the core root elements of the Afro-Caribbean-New Orleans tradition, Syzygy, blends blues harmonies with swing and latin rhythms, indeed all the classic elements of the jazz language, stating emphatically the vast amount of uncovered....<a style=\"color: #000000; -webkit-transition: all 0.5s ease-in-out; transition: all 0.5s ease-in-out;\" href=\"http://truthrevolutionrecords.com/news/curtis-brothers-quartet-syzygy-reviewed-in-all-about-jazz\"><span class=\"s2\" style=\"margin: 0px; padding: 0px; border: 0px; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-caps: inherit; line-height: inherit; vertical-align: baseline; display: inline !important; float: none !important;\">READ MORE</span></a></span></p>";
        postArticle.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");


        feedImage = (ImageView) findViewById(R.id.postImage);
        feedImage.setTransitionName("transition" + feedItem.getId());
        Glide.with(this).load(feedItem.getImageLink()).into(feedImage);

        toolbar.setTitle(feedItem.getTitle());
    }

}
