package com.primeperspective.jazzreader;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import utilities.FeedGetter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;

        FeedGetter getFeed = new FeedGetter(context);
        getFeed.execute();
    }
}
