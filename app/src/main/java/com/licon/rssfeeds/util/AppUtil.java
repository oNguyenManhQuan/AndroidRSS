package com.licon.rssfeeds.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.IntentData;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import android.os.Environment;

import com.licon.rssfeeds.data.constants.AppData;

import java.io.File;
import java.io.FileNotFoundException;

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

                Intent intent = new Intent();
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
                UIUtil.showDialogNotify(activity,
                        activity.getString(R.string.text_dialog_title_error),
                        activity.getString(R.string.text_dialog_msg_no_application),
                        null,
                        activity.getString(R.string.text_dialog_btn_ok),
                        null,
                        UIUtil.getDefaultDismissListener());
            }
        }
    }

    public static boolean createAppFolderIfNotExists(Activity activity) throws FileNotFoundException {
        File pdfFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), AppData.APP_FOLDER_NAME);
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
            return true;
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isHostAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName(AppData.HOST_URL);
            return (ipAddr.equals("")) ? false : true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void showInternetWarning(Activity activity) {
        if (!isNetworkConnected(activity)) {
            UIUtil.showDialogNotifyOnUIThread(activity,
                    activity.getString(R.string.text_dialog_title_warning),
                    activity.getString(R.string.text_dialog_msg_no_internet),
                    null,
                    activity.getString(R.string.text_dialog_btn_ok),
                    null,
                    UIUtil.getDefaultDismissListener());
        }
    }
}