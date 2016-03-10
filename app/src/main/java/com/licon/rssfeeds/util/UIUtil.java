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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
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

    public static DialogInterface.OnClickListener getDefaultDismissListener() {
        DialogInterface.OnClickListener onCancelClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        };
        return onCancelClickListener;
    }

    public static void showDialogNotify(final Activity activity, final String title, final String msg,
                                              final String strOk, final String strCancel,
                                              DialogInterface.OnClickListener onOkClickListener,
                                              DialogInterface.OnClickListener onCancelClickListener) {
        if (activity == null) {
            return;
        } else if (activity.isFinishing()) {
            return;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        if (!TextUtils.isEmpty(strCancel)) {
            dialog.setNegativeButton(strCancel, onCancelClickListener);
        }
        if (!TextUtils.isEmpty(strOk)) {
            dialog.setPositiveButton(strOk, onOkClickListener);
        }
        dialog.show();
    }

    public static void showDialogNotifyOnUIThread(final Activity activity, final String title, final String msg,
                                                  final String strOk, final String strCancel,
                                                  final DialogInterface.OnClickListener onOkClickListener,
                                                  final DialogInterface.OnClickListener onCancelClickListener) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialogNotify(activity, title, msg, strOk, strCancel,
                        onOkClickListener, onCancelClickListener);
            }
        });
    }

    public static void loadImageViewFromUrl(ImageView imageView, String image_url, Drawable errorDrawable) {
        mImageView = imageView;
        new LoadImage(imageView, errorDrawable).execute(image_url);
    }

    private static class LoadImage extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private Drawable placeholer;

        public LoadImage(ImageView imageView, Drawable errorImage) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            placeholer = errorImage;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageDrawable(placeholer);
                    }
                }
            }
        }
    }

    private static Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}