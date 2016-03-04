package com.licon.rssfeeds.database.helper;

/**
 * Created by FRAMGIA\khairul.alam.licon on 2/3/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.database.constants.DBConfig;
import com.licon.rssfeeds.database.constants.FeedItemSQLiteData;
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.util.DateFormatUtil;

import java.util.ArrayList;
import java.util.List;

public class FeedItemSQLiteHelper extends SQLiteOpenHelper {

    private static FeedItemSQLiteHelper sInstance;
    private Context mContext;

    public static synchronized FeedItemSQLiteHelper getInstance(Context context) {

        /** Use the application context, which will ensure that you
            don't accidentally leak an Activity's context.
            See this article for more information: http://bit.ly/6LRzfx */
        if (sInstance == null) {
            sInstance = new FeedItemSQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private FeedItemSQLiteHelper(Context context) {
        super(context, DBConfig.getConfig().DATABASE_NAME, null, DBConfig.getConfig().DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create rss feed table
        db.execSQL(FeedItemSQLiteData.COMMAND_CREATE_RSSFEED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop older able if existed
        db.execSQL(FeedItemSQLiteData.COMMAND_DROP_RSSFEED_TABLE);
        // create new table
        this.onCreate(db);
    }

    public void addRssFeed(FeedItem feedItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_GUID, getStringToStore(feedItem.getGuid()));
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_TITLE, getStringToStore(feedItem.getTitle()));
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_LINK, getStringToStore(feedItem.getLink()));
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_DESCRIPTION, getStringToStore(feedItem.getDescription()));
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_MEDIA_URL, getStringToStore(feedItem.getMediaURL()));
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_CATEGORY, getStringToStore(feedItem.getCategory()));
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_AUTHOR, getStringToStore(feedItem.getAuthor()));
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_PUBLISHED_DATE,
                getStringToStore(DateFormatUtil.parseDateToString(feedItem.getPublicationDate())));
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_MEDIA_SIZE, feedItem.getMediaSize());
        values.put(FeedItemSQLiteData.DATABASE_TABLE_COLUMN_VIEW_STATUS, mContext.getString(R.string.text_viewed_status_new));

        db.insertWithOnConflict(FeedItemSQLiteData.DATABASE_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public String getStringToStore(String value) {
        return TextUtils.isEmpty(value) ? "" : value;
    }

    public List<FeedItem> getAllFeeds() {
        List<FeedItem> feedItemList = new ArrayList<FeedItem>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(FeedItemSQLiteData.COMMAND_SELECTALL_RSSFEED_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                feedItemList.add(getFeedItemByCursor(cursor));
            } while (cursor.moveToNext());
        }
        return feedItemList;
    }

    public FeedItem getSingleFeedItem(String title, String category, String pubDate) {
        FeedItem feedItem = new FeedItem();
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = FeedItemSQLiteData.COMMAND_SELECT_WHERE_RSSFEED_TABLE
                + FeedItemSQLiteData.DATABASE_TABLE_COLUMN_TITLE
                + " LIKE '%" + title.replace("'", "''") + "%'" + " and "
                + FeedItemSQLiteData.DATABASE_TABLE_COLUMN_CATEGORY
                + " = '" + category.replace("'", "''") + "'" + " and "
                + FeedItemSQLiteData.DATABASE_TABLE_COLUMN_PUBLISHED_DATE
                + " = '" + pubDate + "'";

        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.getCount() != 1) {
            cursor.close();
            return null;
        }
        return getFeedItemByCursor(cursor);
    }

    private FeedItem getFeedItemByCursor(Cursor cursor) {
        FeedItem feedItem = new FeedItem();
        if (cursor == null){
            return null;
        }
        else if(cursor != null) {
            cursor.moveToFirst();
            feedItem.setId(Integer.parseInt(cursor.getString(FeedItemSQLiteData.POS_COLUMN_ID_0)));
            feedItem.setGuid(cursor.getString(FeedItemSQLiteData.POS_COLUMN_GUID_1));
            feedItem.setTitle(cursor.getString(FeedItemSQLiteData.POS_COLUMN_TITLE_2));
            feedItem.setLink(cursor.getString(FeedItemSQLiteData.POS_COLUMN_LINK_3));
            feedItem.setDescription(cursor.getString(FeedItemSQLiteData.POS_COLUMN_DESCRIPTION_4));
            feedItem.setMediaURL(cursor.getString(FeedItemSQLiteData.POS_COLUMN_MEDIA_URL_5));
            feedItem.setCategory(cursor.getString(FeedItemSQLiteData.POS_COLUMN_CATEGORY_6));
            feedItem.setAuthor(cursor.getString(FeedItemSQLiteData.POS_COLUMN_AUTHOR_7));
            feedItem.setPublicationDate(DateFormatUtil.parseDate(cursor.getString(FeedItemSQLiteData.POS_COLUMN_PUBLISHED_DATE_8)));
            feedItem.setMediaSize(cursor.getLong(FeedItemSQLiteData.POS_COLUMN_MEDIA_SIZE_9));
            feedItem.setViewStatus(cursor.getString(FeedItemSQLiteData.POS_COLUMN_VIEW_STATUS_10));
        }
        return feedItem;
    }
}