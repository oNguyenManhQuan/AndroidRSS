package com.licon.rssfeeds;

import android.app.Application;
import android.content.res.Configuration;

import com.licon.rssfeeds.database.constants.DBConfig;
import com.licon.rssfeeds.database.helper.FeedItemSQLiteHelper;

/**
 * Created by FRAMGIA\khairul.alam.licon on 3/3/16.
 */
public class RssBaseApplication extends Application {
    public static final String DB_NAME = "RSS_FEED";
    public static final int CURRENT_SDK_VERSION = android.os.Build.VERSION.SDK_INT;
    // tab names
    public static String TAB_USA_HOME;
    public static String TAB_TECHNOLOGY;
    public static String TAB_BUSINESS;
    public static String TAB_HEALTH;
    public static String TAB_ENTERTAINMENT;
    public static String TAB_OTHERS;

    @Override
    public void onCreate() {
        super.onCreate();
        DBConfig.getConfig().DATABASE_NAME = DB_NAME;
        DBConfig.getConfig().DATABASE_VERSION = 1;
        DBConfig.getConfig().feedItemSQLiteHelper = FeedItemSQLiteHelper.getInstance(getApplicationContext());
        // set tab names
        TAB_USA_HOME = getResources().getString(R.string.tab_usa_home);
        TAB_TECHNOLOGY = getResources().getString(R.string.tab_tech);
        TAB_BUSINESS = getResources().getString(R.string.tab_business);
        TAB_HEALTH = getResources().getString(R.string.tab_health);
        TAB_ENTERTAINMENT = getResources().getString(R.string.tab_entertainment);
        TAB_OTHERS = getResources().getString(R.string.tab_others);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}