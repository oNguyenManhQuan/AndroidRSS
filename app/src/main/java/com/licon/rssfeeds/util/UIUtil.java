package com.licon.rssfeeds.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.RssBaseApplication;
import com.licon.rssfeeds.data.constants.AppData;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by FRAMGIA\khairul.alam.licon on 1/3/16.
 */
public class UIUtil {
    private static ImageView mImageView;
    private static Bitmap mBitmap;
    private static ProgressDialog pDialog;
    private static Activity mActivity;

    public static void addTextToTextView(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
    }

    public static void addDrawableToView(View view, Drawable drawable) {
        if(RssBaseApplication.CURRENT_SDK_VERSION < AppData.getJellyBean()) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
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

    public static void loadImageViewFromUrl(ImageView imageView, String image_url, Activity activity) {
        mImageView = imageView;
        mActivity = activity;
        new LoadImage().execute(image_url);
    }

    private static class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mActivity);
            pDialog.setMessage(mActivity.getString(R.string.splash_loading));
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                mBitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mBitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if(image != null){
                mImageView.setImageBitmap(image);
                pDialog.dismiss();
            } else {
                pDialog.dismiss();
                showErrorDialogNotify(mActivity,
                        mActivity.getString(R.string.text_dialog_title_error),
                        mActivity.getString(R.string.text_error_image_load),
                        mActivity.getString(R.string.text_dialog_btn_ok));
            }
        }
    }
}