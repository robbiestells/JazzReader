package data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import data.FeedContract.ArtistEntry;
import data.FeedContract.FeedEntry;
import data.FeedContract.GenreEntry;

import static android.R.attr.name;

/**
 * Created by robbi on 3/26/2017.
 */

public class FeedProvider extends ContentProvider {
    public static final String LOG_TAG = FeedProvider.class.getSimpleName();

    private FeedDbHelper mHelper;

    private static final int FEED = 100;
    private static final int FEED_ID = 101;
    private static final int ARTIST = 200;
    private static final int ARTIST_ID = 201;
    private static final int GENRE = 300;
    private static final int GENRE_ID = 301;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FeedContract.CONTENT_AUTHORITY, FeedContract.PATH_FEED, FEED);
        sUriMatcher.addURI(FeedContract.CONTENT_AUTHORITY, FeedContract.PATH_FEED + "/#", FEED_ID);
        sUriMatcher.addURI(FeedContract.CONTENT_AUTHORITY, FeedContract.PATH_ARTISTS, ARTIST);
        sUriMatcher.addURI(FeedContract.CONTENT_AUTHORITY, FeedContract.PATH_ARTISTS + "/#", ARTIST_ID);
        sUriMatcher.addURI(FeedContract.CONTENT_AUTHORITY, FeedContract.PATH_GENRES, GENRE);
        sUriMatcher.addURI(FeedContract.CONTENT_AUTHORITY, FeedContract.PATH_GENRES + "/#", GENRE_ID);

    }

    @Override
    public boolean onCreate() {
        mHelper = new FeedDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mHelper.getReadableDatabase();

        Cursor cursor;

        //determine whether a specific feed item is being queried
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FEED:
                cursor = database.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FEED_ID:
                selection = FeedEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ARTIST:
                cursor = database.query(ArtistEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ARTIST_ID:
                selection = ArtistEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ArtistEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GENRE:
                cursor = database.query(GenreEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GENRE_ID:
                selection = GenreEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(GenreEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        //set notification URI on the cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FEED:
                return FeedEntry.CONTENT_LIST_TYPE;
            case FEED_ID:
                return FeedEntry.CONTENT_ITEM_TYPE;
            case ARTIST:
                return ArtistEntry.CONTENT_LIST_TYPE;
            case ARTIST_ID:
                return ArtistEntry.CONTENT_ITEM_TYPE;
            case GENRE:
                return GenreEntry.CONTENT_LIST_TYPE;
            case GENRE_ID:
                return GenreEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FEED:
                return insertFeed(uri, contentValues);
            case ARTIST:
                return insertArtist(uri, contentValues);
            case GENRE:
                return insertGenre(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertFeed(Uri uri, ContentValues values) {
        //Check that episode title is not null
        String id = values.getAsString(FeedEntry.COLUMN_ITEM_ID);
        if (id == null) {
            throw new IllegalArgumentException("Invalid Item Id");
        }

        SQLiteDatabase database = mHelper.getWritableDatabase();

        long tableId = database.insert(FeedEntry.TABLE_NAME, null, values);

        if (tableId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //notify listeners that there has been a change
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, tableId);
    }

    private Uri insertArtist(Uri uri, ContentValues values) {
        //Check that episode title is not null
        String id = values.getAsString(ArtistEntry.COLUMN_ARTIST_ID);
        if (id == null) {
            throw new IllegalArgumentException("Invalid Artist Id");
        }

        SQLiteDatabase database = mHelper.getWritableDatabase();

        long tableId = database.insert(ArtistEntry.TABLE_NAME, null, values);

        if (tableId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //notify listeners that there has been a change
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, tableId);
    }

    private Uri insertGenre(Uri uri, ContentValues values) {
        //Check that episode title is not null
        String id = values.getAsString(GenreEntry.COLUMN_GENRE_ID);
        if (id == null) {
            throw new IllegalArgumentException("Invalid Genre id");
        }

        SQLiteDatabase database = mHelper.getWritableDatabase();

        long tableId = database.insert(GenreEntry.TABLE_NAME, null, values);

        if (tableId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //notify listeners that there has been a change
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, tableId);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}