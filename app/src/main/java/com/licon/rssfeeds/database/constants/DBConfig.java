package com.licon.rssfeeds.database.constants;

import com.licon.rssfeeds.database.helper.FeedItemSQLiteHelper;

/**
 * Created by FRAMGIA\khairul.alam.licon on 2/3/16.
 */
public class DBConfig {
    private static DBConfig dbConfig;
    public int DATABASE_VERSION;
    public String DATABASE_NAME;
    public FeedItemSQLiteHelper feedItemSQLiteHelper;

    public DBConfig() {
        super();
    }

    public static DBConfig getConfig() {
        if (dbConfig == null)
            dbConfig = new DBConfig();
        return dbConfig;
    }
}