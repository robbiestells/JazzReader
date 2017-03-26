package com.primeperspective.jazzreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import adapters.FeedAdapter;
import models.Artist;
import models.FeedItem;
import models.Genre;
import utilities.Utilities;

import static android.R.attr.type;
import static com.primeperspective.jazzreader.R.id.default_activity_button;
import static com.primeperspective.jazzreader.R.id.feedList;

/**
 * Created by rob on 3/7/17.
 */

public class SubFeed extends AppCompatActivity {

    ArrayList<FeedItem> feedItems = new ArrayList<>();

    private FeedAdapter feedAdapter;
    RecyclerView feedList;
    String filterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedList = (RecyclerView) findViewById(R.id.feedList);
        feedList.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        filterType = intent.getStringExtra("type");
        feedItems = Utilities.getSavedType(this, filterType);
        setFeedItems(feedItems);

    }

    public void setFeedItems(ArrayList<FeedItem> items){
        if (items != null) {
            feedAdapter = new FeedAdapter(this, items);
            feedList.setAdapter(feedAdapter);
        }
    }
}
