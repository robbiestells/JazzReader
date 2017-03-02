package utilities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

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
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import models.FeedItem;

import static android.R.attr.rating;

/**
 * Created by rob on 3/2/17.
 */

public class FeedGetter extends AsyncTask<Void, Void, Void> {
    private static final String LOG_TAG = "ASYNC ";
    Context context;
    URL url;
    ProgressDialog progressDialog;
    ArrayList<FeedItem> feedItems;

    public FeedGetter(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //TODO check for internet connection

        //display loading screen
        progressDialog = new ProgressDialog(context);
        //Set the progress dialog to display spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Set the dialog title to 'Looking...'
        progressDialog.setTitle("Getting episodes");
        //This dialog can't be canceled by pressing the back key
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //dismiss loading dialog
        progressDialog.dismiss();
    }

    @Override
    protected Void doInBackground(Void... params) {
        
        String feed = "http://trrc.sliday.com/api/data.json";
        URL url = createUrl(feed);
        String jsonResponse;
        try {
            jsonResponse = makeHttpRequest(url);
            List<FeedItem> feedItems = extractFeatureFromJson(jsonResponse);
        } catch (IOException e) {
            Log.e("ASYNC: ", "Problem making the HTTP request.", e);
        }

        return null;
    }

    public static List<FeedItem> extractFeatureFromJson(String channelJson) {
        if (TextUtils.isEmpty(channelJson)) {
            return null;
        }

        List<FeedItem> feedItems = new ArrayList<>();

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
                        String title = currentArticle.getString("title");
                        String type = currentArticle.getString("type");
                        String createdAt = currentArticle.getString("createdAt");

                        // Create a new Movie object
                        FeedItem feedItem = new FeedItem(type, title, createdAt);

                        //add movies to list
                        feedItems.add(feedItem);
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
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
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

//        private void saveFeedItems(ArrayList<FeedItem> feedItems){
//            FeedDbHelper mDbHelper = new FeedDbHelper(context);
//            SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//            String query = "SELECT * FROM " + FeedContract.FeedEntry.TABLE_NAME + " WHERE " + FeedContract.FeedEntry.COLUMN_EPIOSDE_AUDIO
//                    + " =?";
//
//            for (FeedItem item:feedItems) {
//
//                Cursor cursor = db.rawQuery(query, new String[] {item.getAudioUrl()}) ;
//
//                if (cursor.getCount() <= 0){
//                    //get values
//                    ContentValues values = new ContentValues();
//                    values.put(FeedContract.FeedEntry.COLUMN_SHOW_NAME, item.getShow());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPISODE_TITLE, item.getTitle());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_LINK, item.getLink());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPISODE_DESCRIPTION, item.getDescription());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPISODE_DATE, item.getPubDate());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_LENGTH, item.getLength());
//                    values.put(FeedContract.FeedEntry.COLUMN_EPIOSDE_AUDIO, item.getAudioUrl());
//
//                    //insert a new entry with the data above
//                    long newRowId = db.insert(FeedContract.FeedEntry.TABLE_NAME, null, values);
//                    Log.v("Insert Feed item", "New row ID: " + newRowId);
//                }
//                cursor.close();
//            }
//
//            db.close();
//        }
}
