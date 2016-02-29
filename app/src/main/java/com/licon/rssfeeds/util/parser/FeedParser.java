package com.licon.rssfeeds.util.parser;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */
import com.licon.rssfeeds.data.model.Feed;
import com.licon.rssfeeds.data.model.FeedItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class FeedParser {
	private static final String TEXT_ERROR_STATUS = "This is not a RSS feed.";
	private FeedItemHandler mFeedItemHandler;
	private FeedInfoHandler mFeedInfoHandler;
	private boolean mStopProcessing = false;

	public interface FeedInfoHandler {
		public void OnFeedInfo(FeedParser feedParser, Feed feed);
	}

	public interface FeedItemHandler {
		public void OnFeedItem(FeedParser feedParser, FeedItem item);
	}

	public FeedParser() {

	}

	public class UnknownFeedException extends Exception {
		public UnknownFeedException() {
			super(TEXT_ERROR_STATUS);
		}
		public UnknownFeedException(Throwable throwable) {
			super(TEXT_ERROR_STATUS, throwable);
		}
	}

	FeedInfoHandler getOnFeedInfoHandler() {
		return mFeedInfoHandler;
	}
	public void setOnFeedInfoHandler(FeedInfoHandler handler) {
		this.mFeedInfoHandler = handler;
	}

	FeedItemHandler getOnFeedItemHandler() {
		return mFeedItemHandler;
	}
	public void setOnFeedItemHandler(FeedItemHandler handler) {
		this.mFeedItemHandler = handler;
	}

	public boolean shouldStopProcessing() {
		return mStopProcessing;
	}
	public void stopProcessing() {
		this.mStopProcessing = true;
	}

	public void parseFeed(XmlPullParser parser) throws XmlPullParserException, IOException, UnknownFeedException {
		// make sure this is a RSS document
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.START_TAG)
			eventType = parser.next();
		if (parser.getName().equals("rss")) {
			RSSParser.process(parser, this);
		} else {
			throw new UnknownFeedException();
		}
	}
}