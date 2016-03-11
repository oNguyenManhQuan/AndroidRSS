package com.licon.rssfeeds.util;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by FRAMGIA\khairul.alam.licon on 11/03/16.
 */
public class WebAppInterface {

	Context mContext;

	public WebAppInterface(Context context) {
		mContext = context;
	}

	@JavascriptInterface
	public void openUrl(String url) {
		AppUtil.openBrowser(mContext, url);
	}
}