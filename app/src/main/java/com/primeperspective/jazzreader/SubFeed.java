package com.primeperspective.jazzreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import adapters.FeedAdapter;
import models.FeedItem;

import static com.primeperspective.jazzreader.R.id.feedList;

/**
 * Created by rob on 3/7/17.
 */

public class SubFeed extends AppCompatActivity {

    ArrayList<FeedItem> feedItems = new ArrayList<>();

    private FeedAdapter feedAdapter;
    RecyclerView feedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedList = (RecyclerView) findViewById(R.id.feedList);
        feedList.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        //TODO
        Parcelable[] parcelableArray = intent.getParcelableArrayExtra("feedList");

        FeedItem[] resultArray = null;
        if (parcelableArray != null) {
            resultArray = Arrays.copyOf(parcelableArray, parcelableArray.length, FeedItem[].class);
        }
        for (FeedItem item:resultArray) {
            feedItems.add(item);
        }

        feedAdapter = new FeedAdapter(this, feedItems);
        feedList.setAdapter(feedAdapter);
    }
}
