package com.licon.rssfeeds.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by FRAMGIA\khairul.alam.licon on 4/3/16.
 */
public class AppUtil {
    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}