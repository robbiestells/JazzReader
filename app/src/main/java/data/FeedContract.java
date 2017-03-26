package data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;

import models.Artist;
import models.Genre;

/**
 * Created by robbi on 3/26/2017.
 */

public class FeedContract {
    private FeedContract(){}

    public static final String CONTENT_AUTHORITY = "com.primeperspective.jazzreader";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FEED = "feed";

    public static final class FeedEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FEED);

        //string with table uri
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FEED;

        //string with item uri
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FEED;

        // Table name
        public static final String TABLE_NAME = "feed";

        //Column Names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_ITEM_TYPE = "item_type";
        public static final String COLUMN_ITEM_TITLE = "item_title";
        public static final String COLUMN_ITEM_SHORT_DESCRIPTION = "item_short_description";
        public static final String COLUMN_ITEM_IMAGE_LINK = "item_image_link";
        public static final String COLUMN_ITEM_IMAGE_RETINA_LINK = "item_image_retina_link";
        public static final String COLUMN_ITEM_LINK = "item_link";
        public static final String COLUMN_ITEM_RELEASE_DATE = "item_release_date";
        public static final String COLUMN_ITEM_SHARE_LINK = "item_share_link";
        public static final String COLUMN_ITEM_CREATED_AT = "item_create_at";
        public static final String COLUMN_ITEM_UPDATED_AT = "item_updated_at";
        public static final String COLUMN_ITEM_VIDEO_LINK = "item_video_link";
        public static final String COLUMN_ITEM_ARTICLE = "item_article";
        public static final String COLUMN_ITEM_EVENT_DATE = "item_event_date";
        public static final String COLUMN_ITEM_EVENT_LOCATION = "item_event_location";
        public static final String COLUMN_ITEM_PRICE= "item_price";


        //ArrayList<Artist> artists;
        //ArrayList<Genre> genres;

    }

}