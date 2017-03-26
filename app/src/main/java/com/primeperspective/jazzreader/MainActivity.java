package com.primeperspective.jazzreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.FeedAdapter;
import data.FeedContract;
import data.FeedContract.FeedEntry;
import data.FeedDbHelper;
import detailViews.News;
import models.Artist;
import models.FeedItem;
import models.Genre;
import utilities.FeedGetter;
import utilities.Utilities;

import static android.R.attr.defaultValue;
import static android.R.attr.id;
import static java.security.AccessController.getContext;
import static utilities.FeedGetter.extractFeatureFromJson;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private  ArrayList<FeedItem> feedItems = new ArrayList<>();
    private FeedAdapter feedAdapter;
    RecyclerView feedList;
    private static MainActivity sMainActivty;
    private static int cardWidth;

    public static MainActivity getInstance() {
        return sMainActivty;
    }

    public static int getCardWidth() {
        return cardWidth;
    }

    public static void setCardWidth(int cardWidth) {
        MainActivity.cardWidth = cardWidth;
    }

    public void setFeedItems(ArrayList<FeedItem> items){
        if (items != null) {
            feedAdapter = new FeedAdapter(this, items);
            feedList.setAdapter(feedAdapter);
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sMainActivty = this;
        setCardWidth(0);

        //set up hamburger
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        feedList = (RecyclerView) findViewById(R.id.feedList);
        feedList.setLayoutManager(new LinearLayoutManager(this));

        //TODO get and load last feed
        ArrayList<FeedItem> loadFeed = new ArrayList<>();
        loadFeed = Utilities.getSavedFeed(this);
        if (loadFeed !=null){
           setFeedItems(loadFeed);
       }

        //get new feed items
       FeedGetter getFeed = new FeedGetter(this);
       getFeed.execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_videos) {
            Intent intent = new Intent(this, SubFeed.class);
            intent.putExtra("type", "video");
            startActivity(intent);

        } else if (id == R.id.nav_news) {
            Intent intent = new Intent(this, SubFeed.class);
            intent.putExtra("type", "news");
            startActivity(intent);

        } else if (id == R.id.nav_events) {
            Intent intent = new Intent(this, SubFeed.class);
            intent.putExtra("type", "event");
            startActivity(intent);

        } else if (id == R.id.nav_links) {
            Intent intent = new Intent(this, SubFeed.class);
            intent.putExtra("type", "links");
            startActivity(intent);

        }  else if (id == R.id.release) {
        Intent intent = new Intent(this, SubFeed.class);
        intent.putExtra("type", "release");
        startActivity(intent);

        } else if (id == R.id.nav_artists) {

        } else if (id == R.id.nav_genres) {

        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_channels) {

        } else if (id == R.id.nav_about_us) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToDetail(FeedItem item, View view){
        Intent intent;
        if (item.getType().contains("news")) {
          intent = new Intent(sMainActivty, News.class);
        } else {
            intent = new Intent();
        }
        intent.putExtra("itemId", item.getId());
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition" + item.getId() );
        intent.putExtra("transition", "transition" + item.getId());
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }


}
