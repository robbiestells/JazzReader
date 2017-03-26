package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import data.FeedContract.FeedEntry;

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
                FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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

        sqLiteDatabase.execSQL(SQL_CREATE_FEED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}