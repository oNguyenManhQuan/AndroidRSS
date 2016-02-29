package com.licon.rssfeeds.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by FRAMGIA\khairul.alam.licon on 1/3/16.
 */
public class UIUtil {
    public static void addTextToTextView(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
    }

    public static void showErrorDialogNotify(final Activity activity, final String title,
                                             final String msg, final String strCancel) {
        if (activity == null) {
            return;
        } else if (activity.isFinishing()) {
            return;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        if (!TextUtils.isEmpty(strCancel)) {
            dialog.setNegativeButton(strCancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }
}