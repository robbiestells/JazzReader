package com.primeperspective.jazzreader;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import adapters.FeedAdapter;
import models.FeedItem;
import utilities.FeedGetter;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private  ArrayList<FeedItem> feedItems = new ArrayList<>();
    private FeedAdapter feedAdapter;
    RecyclerView feedList;

    public void setFeedItems(ArrayList<FeedItem> items){
        for (int i = 0; i < 10; i++){
            feedItems.add(items.get(i));
        }
        feedAdapter = new FeedAdapter(this, feedItems);
        feedList.setAdapter(feedAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedList = (RecyclerView) findViewById(R.id.feedList);

        FeedGetter getFeed = new FeedGetter(this);
        getFeed.execute();

//        feedList.setLayoutManager(new LinearLayoutManager(getContext()));
//        feedList.getLayoutManager().isSmoothScrolling();

    }
}
