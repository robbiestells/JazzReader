package com.primeperspective.jazzreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.FeedAdapter;
import models.FeedItem;

/**
 * Created by rob on 3/7/17.
 */

public class Details extends AppCompatActivity {

    FeedItem feedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        feedItem = (FeedItem) intent.getParcelableExtra("item");

        TextView postTitle = (TextView) findViewById(R.id.postTitle);
        postTitle.setText(feedItem.getTitle());

    }
}
