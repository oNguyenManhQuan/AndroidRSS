package com.licon.rssfeeds.data.constants;

import android.os.Build;

/**
 * Created by FRAMGIA\khairul.alam.licon on 24/2/16.
 */
public class AppData {
    // splash timeout
    public static final int SPLASH_TIME_OUT = 1000;
    // tabs position
    public static final int TAB_USA_HOME = 0;
    public static final int TAB_TECHNOLOGY = 1;
    public static final int TAB_BUSINESS = 2;
    public static final int TAB_HEALTH = 3;
    public static final int TAB_ENTERTAINMENT = 4;
    public static final int TAB_OTHERS = 5;
    // time zone
    public static final String APP_TIME_ZONE = "UTC";
    // get sdk os version
    public static int getJellyBean() {
        return Build.VERSION_CODES.JELLY_BEAN;
    }
    //  Synchronization of rss news
    public static int HISTORY_DELETE_STEP = 10;
}