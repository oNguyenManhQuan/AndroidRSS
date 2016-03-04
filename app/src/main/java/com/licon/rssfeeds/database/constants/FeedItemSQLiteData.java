package com.licon.rssfeeds.database.constants;

import com.licon.rssfeeds.data.constants.RSSData;

/**
 * Created by FRAMGIA\khairul.alam.licon on 2/3/16.
 */
public class FeedItemSQLiteData {
    /** table name */
    public static final String DATABASE_TABLE_NAME = "TABLE_FEED_ITEM";
    /** column names */
    public static final String DATABASE_TABLE_COLUMN_ID = "_id";
    public static final String DATABASE_TABLE_COLUMN_GUID = RSSData.ATTRIBUTE_FEEDITEM_GUID;
    public static final String DATABASE_TABLE_COLUMN_TITLE = RSSData.ATTRIBUTE_FEEDITEM_TITLE;
    public static final String DATABASE_TABLE_COLUMN_LINK = RSSData.ATTRIBUTE_FEEDITEM_LINK;
    public static final String DATABASE_TABLE_COLUMN_DESCRIPTION = RSSData.ATTRIBUTE_FEEDITEM_DESCRIPTION;
    public static final String DATABASE_TABLE_COLUMN_MEDIA_URL = RSSData.ATTRIBUTE_FEEDITEM_MEDIA_URL;
    public static final String DATABASE_TABLE_COLUMN_CATEGORY = RSSData.ATTRIBUTE_FEEDITEM_CATEGORY;
    public static final String DATABASE_TABLE_COLUMN_AUTHOR = RSSData.ATTRIBUTE_FEEDITEM_AUTHOR;
    public static final String DATABASE_TABLE_COLUMN_PUBLISHED_DATE = RSSData.ATTRIBUTE_FEEDITEM_PUBLISHED_DATE;
    public static final String DATABASE_TABLE_COLUMN_MEDIA_SIZE = RSSData.ATTRIBUTE_FEEDITEM_MEDIA_SIZE;
    /** column positions */
    public static final int POS_COLUMN_ID_0 = 0;
    public static final int POS_COLUMN_GUID_1 = 1;
    public static final int POS_COLUMN_TITLE_2 = 2;
    public static final int POS_COLUMN_LINK_3 = 3;
    public static final int POS_COLUMN_DESCRIPTION_4 = 4;
    public static final int POS_COLUMN_MEDIA_URL_5 = 5;
    public static final int POS_COLUMN_CATEGORY_6 = 6;
    public static final int POS_COLUMN_AUTHOR_7 = 7;
    public static final int POS_COLUMN_PUBLISHED_DATE_8 = 8;
    public static final int POS_COLUMN_MEDIA_SIZE_9 = 9;
    /** sqlite commands */
    public static final String COMMAND_CREATE_RSSFEED_TABLE = "CREATE TABLE IF NOT EXISTS "
            + DATABASE_TABLE_NAME
            + "( "
            + DATABASE_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DATABASE_TABLE_COLUMN_GUID + " TEXT, "
            + DATABASE_TABLE_COLUMN_TITLE + " TEXT, "
            + DATABASE_TABLE_COLUMN_LINK + " TEXT, "
            + DATABASE_TABLE_COLUMN_DESCRIPTION + " TEXT, "
            + DATABASE_TABLE_COLUMN_MEDIA_URL + " TEXT, "
            + DATABASE_TABLE_COLUMN_CATEGORY + " TEXT, "
            + DATABASE_TABLE_COLUMN_AUTHOR + " TEXT, "
            + DATABASE_TABLE_COLUMN_PUBLISHED_DATE + " LONG, "
            + DATABASE_TABLE_COLUMN_MEDIA_SIZE + " LONG "
            + " )";
    public static final String COMMAND_DROP_RSSFEED_TABLE = "DROP TABLE IF EXISTS" + DATABASE_TABLE_NAME;
    public static final String COMMAND_SELECTALL_RSSFEED_TABLE = "SELECT * FROM " + DATABASE_TABLE_NAME;
    public static final String COMMAND_SELECT_WHERE_RSSFEED_TABLE = "SELECT * FROM " + DATABASE_TABLE_NAME + " WHERE ";
    public static final String COMMAND_DELETE_WHERE_RSSFEED_TABLE = "DELETE FROM " + DATABASE_TABLE_NAME + " WHERE ";
}