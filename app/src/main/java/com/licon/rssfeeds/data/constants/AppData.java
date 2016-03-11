package com.licon.rssfeeds.data.constants;

import android.os.Build;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

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
    // synchronization of rss news
    public static int HISTORY_DELETE_STEP = 10;
    // pagination
    public static int PAGINATION_TIME_OUT = 1500;
    public static int PAGINATION_DEFAULT_LIMIT = 10;
    public static int PAGINATION_RECYCLER_THRESHOLD = 5;
    // file & folder to print news
    public static String APP_FOLDER_NAME = "VOA";
    public static String FILE_FORMAT_PDF = ".pdf";
    public static Rectangle PDF_PAGE_DEFAULT_SIZE = PageSize.A4;
    // checking internet access
    public static String HOST_URL = "www.voanews.com";
    // webview data
    public static String JS_INTERFACE = "Android";
}