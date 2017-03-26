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
        Intent intent = new Intent(sMainActivty, Details.class);
        intent.putExtra("item", item);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition" + item.getId() );
        intent.putExtra("transition", "transition" + item.getId());
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    public static ArrayList<FeedItem> extractFeatureFromJson(String channelJson) {
        if (TextUtils.isEmpty(channelJson)) {
            return null;
        }

        ArrayList<FeedItem> feedItems = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(channelJson);

            JSONArray resultArray = baseJsonResponse.getJSONArray("channels");

            // For each movie in the array, create a Movie object and add it to the ArrayList
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject currentChannel = resultArray.getJSONObject(i);

                //get channel info


                for (int c = 0; c < currentChannel.length(); c++){
                    JSONArray articleArray = currentChannel.getJSONArray("articles");
                    for (int a = 0; a < articleArray.length(); a++) {

                        //get article info
                        JSONObject currentArticle = articleArray.getJSONObject(a);

                        // Extract out data
                        String id = currentArticle.getString("id");
                        String title = currentArticle.getString("title");
                        String type = currentArticle.getString("type");
                        String shareLink = currentArticle.getString("shareLink");
                        String createdAt = currentArticle.getString("createdAt");
                        String updatedAt = currentArticle.getString("updatedAt");
                        String shortDescription = "";
                        String imageLink = "";
                        String imageLinkRetina = "";
                        String link = "";
                        String price = "";
                        String releaseDate = "";
                        String videoLink = "";
                        String article = "";
                        String eventDate = "";
                        String eventLocation = "";

                        if (type.contains("release") || type.contains("event") || type.contains("link") || type.contains("news")){
                            shortDescription = currentArticle.getString("shortDescription");
                            imageLink = currentArticle.getString("imageLink");
                            imageLinkRetina = currentArticle.getString("imageLinkRetina");
                            link = currentArticle.getString("link");
                        }

                        if (type.contains("release") || type.contains("event")) {
                            price = currentArticle.getString("price");
                        }

                        if (type.contains("release")) {
                            releaseDate = currentArticle.getString("releaseDate");
                        }
                        if (type.contains("video") || type.contains("news")) {
                            videoLink = currentArticle.getString("videoLink");
                        }
                        if (type.contains("event") || type.contains("news")) {
                            article = currentArticle.getString("article");
                        }
                        if (type.contains("event")) {
                            eventDate = currentArticle.getString("eventDate");
                            eventLocation = currentArticle.getString("eventLocation");
                        }

                        ArrayList<Artist> artists = new ArrayList<>();
                        JSONArray artistArray = currentArticle.getJSONArray("artists");
                        for (int ar = 0; ar < artistArray.length(); ar++) {
                            JSONObject currentArtist = artistArray.getJSONObject(ar);

                            //extract artist data
                            String artistId = currentArtist.getString("id");
                            String artistName = currentArtist.getString("artistName");
                            String topicName = currentArtist.getString("topicName");
                            String artistImage = currentArtist.getString("artistImage");
                            String artistImageRetina = currentArtist.getString("artistImageRetina");
                            String artistBio = currentArtist.getString("artistBio");

                            Artist artist = new Artist(artistId, artistName, topicName, artistImage, artistImageRetina, artistBio);
                            artists.add(artist);

                        }

                        ArrayList<Genre> genres = new ArrayList<>();
                        JSONArray genreArray = currentArticle.getJSONArray("genres");
                        for (int g = 0; g < genreArray.length(); g++) {
                            JSONObject currentGenre = genreArray.getJSONObject(g);

                            //extract artist data
                            String genreId = currentGenre.getString("id");
                            String genreName = currentGenre.getString("genreName");
                            String topicName = currentGenre.getString("topicName");

                            Genre genre = new Genre(genreId, genreName, topicName);
                            genres.add(genre);

                        }

                        // Create a new FeedItem object
                        FeedItem feedItem = new FeedItem(id, type, title, shortDescription, imageLink, imageLinkRetina,
                                link, releaseDate, shareLink, createdAt, updatedAt, artists, genres, videoLink, article,
                                eventDate, eventLocation, price);

                        //add feed item to list
                        feedItems.add(feedItem);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("MainActivity", "Problem parsing the old JSON", e);
        }
        //return movie list
        return feedItems;
    }

    public ArrayList<FeedItem> getSavedFeed() {
        FeedDbHelper mDbHelper = new FeedDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        String query = "SELECT * FROM " + FeedEntry.TABLE_NAME;
                //+ " WHERE " + FeedEntry.COLUMN_ITEM_TYPE
                //+ " =?";

//        Cursor cursor = db.rawQuery(query, new String[] {selectedShow.getName()});
        Cursor cursor = db.rawQuery(query, new String[] {});

        int idColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_ID);
        int typeColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_TYPE);
        int titleColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_TITLE);
        int shortDescriptionColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_SHORT_DESCRIPTION);
        int imageLinkColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_IMAGE_LINK);
        int retinaLinkColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_IMAGE_RETINA_LINK);
        int linkColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_LINK);
        int releaseDateColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_RELEASE_DATE);
        int shareLinkColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_SHARE_LINK);
        int createdColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_CREATED_AT);
        int updatedColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_UPDATED_AT);
        int videoLinkColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_VIDEO_LINK);
        int articleColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_ARTICLE);
        int eventDateColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_EVENT_DATE);
        int eventLocationColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_EVENT_LOCATION);
        int priceColumnIndex = cursor.getColumnIndex(FeedEntry.COLUMN_ITEM_PRICE);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FeedItem item = new FeedItem();
            item.setId(cursor.getString(idColumnIndex));
            item.setType(cursor.getString(typeColumnIndex));
            item.setTitle(cursor.getString(titleColumnIndex));
            item.setShortDescription(cursor.getString(shortDescriptionColumnIndex));
            item.setImageLink(cursor.getString(imageLinkColumnIndex));
            item.setImageLinkRetina(cursor.getString(retinaLinkColumnIndex));
            item.setLink(cursor.getString(linkColumnIndex));
            item.setReleaseDate(cursor.getString(releaseDateColumnIndex));
            item.setShareLink(cursor.getString(shareLinkColumnIndex));
            item.setCreatedAt(cursor.getString(createdColumnIndex));
            item.setUpdatedAt(cursor.getString(updatedColumnIndex));
            item.setVideoLink(cursor.getString(videoLinkColumnIndex));
            item.setArticle(cursor.getString(articleColumnIndex));
            item.setEventDate(cursor.getString(eventDateColumnIndex));
            item.setEventLocation(cursor.getString(eventLocationColumnIndex));
            item.setPrice(cursor.getString(priceColumnIndex));
            feedItems.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return feedItems;
    }
}
