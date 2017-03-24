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

        MainActivity activity = MainActivity.getInstance();
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String lastJSON = sharedPref.getString("lastJSON", "first");
        if (lastJSON != "first"){
            feedItems = extractFeatureFromJson(lastJSON, filterType);
            setFeedItems(feedItems);
            //feedItems.clear();
        }


    }
    public void setFeedItems(ArrayList<FeedItem> items){
        ArrayList<FeedItem> displayItems = new ArrayList<>();
        if (items != null) {
            for (int i = 0; i < 30; i++) {
                displayItems.add(items.get(i));
            }

            feedAdapter = new FeedAdapter(this, displayItems);
            feedList.setAdapter(feedAdapter);

//        feedList.getLayoutManager().isSmoothScrolling();
        }
    }

    public static ArrayList<FeedItem> extractFeatureFromJson(String channelJson, String filterType) {
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

                        if (type.contains(filterType)){
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
            }
        } catch (JSONException e) {
            Log.e("SubFeed", "Problem parsing the old JSON", e);
        }
        //return movie list
        return feedItems;
    }
}
