package utilities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.primeperspective.jazzreader.MainActivity;
import com.primeperspective.jazzreader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import adapters.FeedAdapter;
import data.FeedContract;
import data.FeedDbHelper;
import models.Artist;
import models.FeedItem;
import models.Genre;

import static android.R.attr.rating;
import static android.R.attr.type;
import static android.provider.Settings.Global.getString;

/**
 * Created by rob on 3/2/17.
 */

public class FeedGetter extends AsyncTask<Void, Void, ArrayList<FeedItem>> {
    private static final String LOG_TAG = "ASYNC ";
    MainActivity activity;
    URL url;
    ProgressDialog progressDialog;
    ArrayList<FeedItem> feedItems;
    FeedAdapter feedAdapter;

    public FeedGetter(MainActivity mainActivity) {
        this.activity = mainActivity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //TODO check for internet connection

        Toast toast = Toast.makeText(activity.getBaseContext(), "Getting Feed", Toast.LENGTH_SHORT);
        toast.show();
        //display loading screen
//        progressDialog = new ProgressDialog(activity);
//        //Set the progress dialog to display spinner
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        //Set the dialog title to 'Looking...'
//        progressDialog.setTitle("Getting Feed");
//        //This dialog can't be canceled by pressing the back key
//        progressDialog.setCancelable(false);
//        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<FeedItem> feedItems) {
        super.onPostExecute(feedItems);
        saveFeedItems(feedItems);
        activity.setFeedItems(feedItems);
        //feedAdapter = new FeedAdapter(context, feedItems);
        //dismiss loading dialog
        //   progressDialog.dismiss();
        Toast toast = Toast.makeText(activity.getBaseContext(), "Feed updated", Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    protected ArrayList<FeedItem> doInBackground(Void... params) {

        String feed = "http://trrc.sliday.com/api/data.json";
        URL url = createUrl(feed);
        String jsonResponse;
        try {
            jsonResponse = makeHttpRequest(url);

            //save json to settings
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("lastJSON", jsonResponse);
            editor.commit();

            feedItems = extractFeatureFromJson(jsonResponse);
        } catch (IOException e) {
            Log.e("ASYNC: ", "Problem making the HTTP request.", e);
        }

        return feedItems;
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


                for (int c = 0; c < currentChannel.length(); c++) {
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

                        if (type.contains("release") || type.contains("event") || type.contains("link") || type.contains("news")) {
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

                        saveGenres(genres);
                        saveArtists(artists);

                    }
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }
        //return movie list
        return feedItems;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(20000 /* milliseconds */);
            urlConnection.setConnectTimeout(30000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Http response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem getting json");

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //create URL out of string
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("ASYNC: ", "ERROR with creating URL", exception);
            return null;
        }
        return url;
    }

    private void saveFeedItems(ArrayList<FeedItem> feedItems) {
        FeedDbHelper mDbHelper = new FeedDbHelper(activity.getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String query = "SELECT * FROM " + FeedContract.FeedEntry.TABLE_NAME + " WHERE " + FeedContract.FeedEntry.COLUMN_ITEM_ID
                + " =?";

        for (FeedItem item : feedItems) {

            Cursor cursor = db.rawQuery(query, new String[]{item.getId()});

            if (cursor.getCount() <= 0) {
                //get values
                ContentValues values = new ContentValues();
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_ID, item.getId());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_TYPE, item.getType());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_TITLE, item.getTitle());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_SHORT_DESCRIPTION, item.getShortDescription());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_IMAGE_LINK, item.getImageLink());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_IMAGE_RETINA_LINK, item.getImageLinkRetina());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_LINK, item.getLink());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_RELEASE_DATE, item.getReleaseDate());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_SHARE_LINK, item.getShareLink());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_CREATED_AT, item.getCreatedAt());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_UPDATED_AT, item.getUpdatedAt());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_VIDEO_LINK, item.getVideoLink());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_ARTICLE, item.getArticle());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_EVENT_DATE, item.getEventDate());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_EVENT_LOCATION, item.getEventLocation());
                values.put(FeedContract.FeedEntry.COLUMN_ITEM_PRICE, item.getPrice());

                //insert a new entry with the data above
                long newRowId = db.insert(FeedContract.FeedEntry.TABLE_NAME, null, values);
                Log.v("Insert Feed item", "New row ID: " + newRowId);
            }
            cursor.close();
        }

        db.close();
    }

    private static void saveArtists(ArrayList<Artist> artists) {
        MainActivity mainActivity = MainActivity.getInstance();
        FeedDbHelper mDbHelper = new FeedDbHelper(mainActivity.getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String query = "SELECT * FROM " + FeedContract.ArtistEntry.TABLE_NAME + " WHERE " + FeedContract.ArtistEntry.COLUMN_ARTIST_ID
                + " =?";

        for (Artist artist : artists) {

            Cursor cursor = db.rawQuery(query, new String[]{artist.getId()});

            if (cursor.getCount() <= 0) {
                //get values
                ContentValues values = new ContentValues();
                values.put(FeedContract.ArtistEntry.COLUMN_ARTIST_ID, artist.getId());
                values.put(FeedContract.ArtistEntry.COLUMN_ARTIST_IMAGE, artist.getArtistImage());
                values.put(FeedContract.ArtistEntry.COLUMN_ARTIST_IMAGE_RETINA, artist.getArtistImageRetina());
                values.put(FeedContract.ArtistEntry.COLUMN_ARTIST_NAME, artist.getArtistName());
                values.put(FeedContract.ArtistEntry.COLUMN_TOPIC_NAME, artist.getTopicName());

                //insert a new entry with the data above
                long newRowId = db.insert(FeedContract.ArtistEntry.TABLE_NAME, null, values);
                Log.v("Insert Artist item", "New row ID: " + newRowId);
            }
            cursor.close();
        }

        db.close();
    }

    private static void saveGenres(ArrayList<Genre> genres) {
        MainActivity mainActivity = MainActivity.getInstance();
        FeedDbHelper mDbHelper = new FeedDbHelper(mainActivity.getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String query = "SELECT * FROM " + FeedContract.GenreEntry.TABLE_NAME + " WHERE " + FeedContract.GenreEntry.COLUMN_GENRE_ID
                + " =?";

        for (Genre genre : genres) {

            Cursor cursor = db.rawQuery(query, new String[]{genre.getId()});

            if (cursor.getCount() <= 0) {
                //get values
                ContentValues values = new ContentValues();
                values.put(FeedContract.GenreEntry.COLUMN_GENRE_ID, genre.getId());
                values.put(FeedContract.GenreEntry.COLUMN_GENRE_NAME, genre.getGenreName());
                values.put(FeedContract.GenreEntry.COLUMN_GENRE_TOPIC, genre.getTopicName());

                //insert a new entry with the data above
                long newRowId = db.insert(FeedContract.GenreEntry.TABLE_NAME, null, values);
                Log.v("Insert Genre item", "New row ID: " + newRowId);
            }
            cursor.close();
        }

        db.close();
    }
}
