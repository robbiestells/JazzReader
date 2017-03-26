package data;

import android.content.ContentResolver;
import android.content.ContentUris;
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
    public static final String PATH_ARTISTS = "artist";
    public static final String PATH_GENRES = "genre";
    public static final String PATH_FEED_ARTIST = "feedArtists";
    public static final String PATH_FEED_GENRE = "feedGenres";

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

        public static Uri buildActivityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class ArtistEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ARTISTS);

        //string with table uri
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTISTS;

        //string with item uri
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTISTS;

        // Table name
        public static final String TABLE_NAME = "artist";

        //Column Names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ARTIST_ID = "artist_id";
        public static final String COLUMN_ARTIST_NAME = "artist_name";
        public static final String COLUMN_TOPIC_NAME = "topic_name";
        public static final String COLUMN_ARTIST_IMAGE = "artist_image";
        public static final String COLUMN_ARTIST_IMAGE_RETINA = "artist_retina_image";
        public static final String ARTIST_BIO= "artist_bio";

        public static Uri buildActivityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class GenreEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GENRES);

        //string with table uri
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRES;

        //string with item uri
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRES;

        // Table name
        public static final String TABLE_NAME = "genre";

        //Column Names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_GENRE_ID = "genre_id";
        public static final String COLUMN_GENRE_NAME = "genre_name";
        public static final String COLUMN_GENRE_TOPIC = "genre_topic";

        public static Uri buildActivityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class FeedArtistEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FEED_ARTIST);

        //string with table uri
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FEED_ARTIST;

        //string with item uri
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FEED_ARTIST;

        // Table name
        public static final String TABLE_NAME = "feedArtists";

        //Column Names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FEED_ARTIST_ID = "feedArtist_id";
        public static final String COLUMN_FEED_ID= "feed_id";
        public static final String COLUMN_ARTIST_ID = "artist_id";

        public static Uri buildActivityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class FeedGenreEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FEED_GENRE);

        //string with table uri
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FEED_GENRE;

        //string with item uri
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FEED_GENRE;

        // Table name
        public static final String TABLE_NAME = "feedGenres";

        //Column Names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FEED_GENRE_ID = "feed_genre_id";
        public static final String COLUMN_FEED_ID= "feed_id";
        public static final String COLUMN_GENRE_ID = "genre_id";

        public static Uri buildActivityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}