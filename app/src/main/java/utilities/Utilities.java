package utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import data.FeedContract;
import data.FeedContract.FeedEntry;
import data.FeedDbHelper;
import models.FeedItem;

/**
 * Created by robbi on 3/26/2017.
 */

public class Utilities {
    public static ArrayList<FeedItem> getSavedFeed(Context context) {
        FeedDbHelper mDbHelper = new FeedDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        String query = "SELECT * FROM " + FeedEntry.TABLE_NAME;
        //+ " WHERE " + FeedEntry.COLUMN_ITEM_TYPE
        //+ " =?";

//        Cursor cursor = db.rawQuery(query, new String[] {selectedShow.getName()});
        Cursor cursor = db.rawQuery(query, new String[]{});

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

    public static FeedItem getSavedItem(Context context, String ItemId) {
        FeedDbHelper mDbHelper = new FeedDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // ArrayList<FeedItem> feedItems = new ArrayList<>();

        String query = "SELECT * FROM " + FeedEntry.TABLE_NAME + " WHERE " + FeedEntry.COLUMN_ITEM_ID + " =" + ItemId;

//        Cursor cursor = db.rawQuery(query, new String[] {selectedShow.getName()});
        Cursor cursor = db.rawQuery(query, new String[]{});

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
//        while (!cursor.isAfterLast()) {
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
        // feedItems.add(item);
        cursor.moveToNext();
        //}
        cursor.close();
        db.close();
        return item;
    }

    public static ArrayList<FeedItem> getSavedType(Context context, String type) {
        FeedDbHelper mDbHelper = new FeedDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        String query = "SELECT * FROM " + FeedEntry.TABLE_NAME;
                //+ " WHERE " + FeedEntry.COLUMN_ITEM_TYPE + " =" + type;

        Cursor cursor = db.rawQuery(query, new String[]{});

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
            if (cursor.getString(typeColumnIndex).contains(type)) {
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
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return feedItems;
    }
}
