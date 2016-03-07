package com.licon.rssfeeds.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.IntentData;

import java.util.ArrayList;
import java.util.List;

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

    public static void shareDataUsingIntent(String data, String url, Activity activity) {
        List<Intent> targetShareIntents = new ArrayList<Intent>();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);

        List<ResolveInfo> resInfos = activity.getPackageManager().queryIntentActivities(shareIntent, 0);
        if(!resInfos.isEmpty()) {
            for(ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;

                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                intent.setPackage(packageName);

                /** filtering & customizing selected applications */
                if(packageName.contains(IntentData.SHARE_FILTER_KEY_TWITTER)
                        || packageName.contains(IntentData.SHARE_FILTER_KEY_LINKEDIN)
                        || packageName.contains(IntentData.SHARE_FILTER_KEY_GOOGLE_PLUS)) {
                    intent.putExtra(Intent.EXTRA_TEXT, data);
                    targetShareIntents.add(intent);
                } else if (packageName.contains(IntentData.SHARE_FILTER_KEY_FACEBOOK)) {
                    intent.putExtra(Intent.EXTRA_TEXT, url); // hint: http://goo.gl/bDqvQ9
                    targetShareIntents.add(intent);
                }
            }

            if(!targetShareIntents.isEmpty()) {
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0),
                        activity.getString(R.string.share_via));
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(chooserIntent);
            } else {
                UIUtil.showErrorDialogNotify(activity,
                        activity.getString(R.string.text_dialog_title_error),
                        activity.getString(R.string.share_no_application_found),
                        activity.getString(R.string.text_dialog_btn_ok));
            }
        }
    }
}