package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import data.FeedContract.ArtistEntry;
import data.FeedContract.FeedArtistEntry;
import data.FeedContract.FeedEntry;
import data.FeedContract.FeedGenreEntry;
import data.FeedContract.GenreEntry;

/**
 * Created by robbi on 3/26/2017.
 */

public class FeedDbHelper extends SQLiteOpenHelper {
    //current version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "feed.db";

    public FeedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FEED_TABLE = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FeedEntry.COLUMN_ITEM_ID + " TEXT, " +
                FeedEntry.COLUMN_ITEM_TYPE + " TEXT, " +
                FeedEntry.COLUMN_ITEM_TITLE + " TEXT, " +
                FeedEntry.COLUMN_ITEM_SHORT_DESCRIPTION + " TEXT, " +
                FeedEntry.COLUMN_ITEM_IMAGE_LINK + " TEXT, " +
                FeedEntry.COLUMN_ITEM_IMAGE_RETINA_LINK + " TEXT, " +
                FeedEntry.COLUMN_ITEM_LINK + " TEXT, " +
                FeedEntry.COLUMN_ITEM_RELEASE_DATE + " TEXT, " +
                FeedEntry.COLUMN_ITEM_SHARE_LINK + " TEXT, " +
                FeedEntry.COLUMN_ITEM_CREATED_AT + " TEXT, " +
                FeedEntry.COLUMN_ITEM_UPDATED_AT + " TEXT, " +
                FeedEntry.COLUMN_ITEM_VIDEO_LINK + " TEXT, " +
                FeedEntry.COLUMN_ITEM_ARTICLE + " TEXT, " +
                FeedEntry.COLUMN_ITEM_EVENT_DATE + " TEXT, " +
                FeedEntry.COLUMN_ITEM_EVENT_LOCATION + " TEXT, " +
                FeedEntry.COLUMN_ITEM_PRICE + " TEXT);";

        final String SQL_CREATE_ARTIST_TABLE = "CREATE TABLE " + ArtistEntry.TABLE_NAME + " (" +
                ArtistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArtistEntry.COLUMN_ARTIST_ID + " TEXT, " +
                ArtistEntry.COLUMN_ARTIST_NAME + " TEXT, " +
                ArtistEntry.COLUMN_TOPIC_NAME + " TEXT, " +
                ArtistEntry.COLUMN_ARTIST_IMAGE + " TEXT, " +
                ArtistEntry.COLUMN_ARTIST_IMAGE_RETINA + " TEXT, " +
                ArtistEntry.ARTIST_BIO + " TEXT);";

        final String SQL_CREATE_GENRE_TABLE = "CREATE TABLE " + GenreEntry.TABLE_NAME + " (" +
                GenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GenreEntry.COLUMN_GENRE_ID + " TEXT, " +
                GenreEntry.COLUMN_GENRE_NAME + " TEXT, " +
                GenreEntry.COLUMN_GENRE_TOPIC + " TEXT);";

        final String SQL_CREATE_FEED_ARTIST_TABLE = "CREATE TABLE " + FeedArtistEntry.TABLE_NAME + " (" +
                FeedArtistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FeedArtistEntry.COLUMN_FEED_ARTIST_ID + " TEXT, " +
                FeedArtistEntry.COLUMN_FEED_ID + " TEXT, " +
                FeedArtistEntry.COLUMN_ARTIST_ID + " TEXT);";

        final String SQL_CREATE_FEED_GENRE_TABLE = "CREATE TABLE " + FeedGenreEntry.TABLE_NAME + " (" +
                FeedGenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FeedGenreEntry.COLUMN_FEED_GENRE_ID + " TEXT, " +
                FeedGenreEntry.COLUMN_FEED_ID + " TEXT, " +
                FeedGenreEntry.COLUMN_GENRE_ID + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_FEED_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ARTIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GENRE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FEED_ARTIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FEED_GENRE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArtistEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GenreEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FeedArtistEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FeedGenreEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}