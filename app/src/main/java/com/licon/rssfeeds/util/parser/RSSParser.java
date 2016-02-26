package com.licon.rssfeeds.util.parser;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */
import com.licon.rssfeeds.data.model.Feed;
import com.licon.rssfeeds.data.model.FeedItem;
import com.licon.rssfeeds.util.DateFormatUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Date;

public class RSSParser {
	private static final String NAMESPACE_MEDIA = "http://search.yahoo.com/mrss/";

	private RSSParser() {

	}

	static void process(XmlPullParser parser, FeedParser feedParser) throws XmlPullParserException, IOException {
		Feed feed = new Feed();
		boolean in_image = false;

		// look for subscription details, stop at item tag
		for (int eventType = parser.getEventType(); eventType !=
				XmlPullParser.END_DOCUMENT; eventType = parser.next()) {
			// check for an ending image tag
			if (in_image && eventType == XmlPullParser.END_TAG && parser.getName().equals("image")) {
				in_image = false;
				continue;
			}
			if (eventType != XmlPullParser.START_TAG)
				continue;

			String name = parser.getName();
			// these are elements about the thumbnail
			if (in_image) {
				if (name.equals("url"))
					feed.setThumbnail(parser.nextText());
				continue;
			}

			if (name.equals("item")) {
				break;
			} else if (name.equals("image")) {
				in_image = true;
				continue;
			} else if (parser.getDepth() != 3) {
				continue;
			} else if (name.equalsIgnoreCase("pubDate")) {
				Date date = DateFormatUtil.parseDate(parser.nextText());
				if (date != null)
					feed.setPubDate(date);
			} else if (name.equalsIgnoreCase("lastBuildDate")) {
				Date date = DateFormatUtil.parseDate(parser.nextText());
				if (date != null)
					feed.setLastBuildDate(date);
			} else if (name.equalsIgnoreCase("title") && parser.getNamespace().equals("")) {
				feed.setTitle(parser.nextText());
			} else if (name.equalsIgnoreCase("thumbnail") && parser.getNamespace().equals(NAMESPACE_MEDIA)) {
				feed.setThumbnail(parser.getAttributeValue("", "url"));
			}
		}

		if (feedParser.getOnFeedInfoHandler() != null)
			feedParser.getOnFeedInfoHandler().OnFeedInfo(feedParser, feed);
		if (feedParser.shouldStopProcessing())
			return;

		parseRSSItems(parser, feedParser);
	}

	private static void parseRSSItems(XmlPullParser parser, FeedParser feedParser) throws XmlPullParserException, IOException {
		FeedItem item = null;

		// grab podcasts from item tags
		for (int eventType = parser.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next()) {
			if (eventType == XmlPullParser.START_TAG) {
				String name = parser.getName();
				String namespace = parser.getNamespace();
				if (name.equalsIgnoreCase("item")) {
					item = new FeedItem();
				} else if (name.equalsIgnoreCase("guid")) {
					item.setUniqueId(parser.nextText());
				} else if (name.equalsIgnoreCase("title") && parser.getNamespace().equals("")) {
					item.setTitle(parser.nextText());
				} else if (name.equalsIgnoreCase("link")) {
					String rel = parser.getAttributeValue(null, "rel");
					if (rel != null && rel.equalsIgnoreCase("payment")) {
						item.setPaymentURL(parser.getAttributeValue(null, "href"));
					} else {
						item.setLink(parser.nextText());
					}
				} else if (namespace.equals("") && name.equalsIgnoreCase("description")) {
					item.setDescription(parser.nextText());
				} else if (name.equalsIgnoreCase("pubDate")) {
					item.setPublicationDate(DateFormatUtil.parseDate(parser.nextText()));
				} else if (name.equalsIgnoreCase("enclosure")) {
					item.setMediaURL(parser.getAttributeValue(null, "url"));
					try {
						item.setMediaSize(Long.valueOf(parser.getAttributeValue(null, "length")));
					} catch (Exception e) {
						item.setMediaSize(0L);
					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				String name = parser.getName();
				if (name.equalsIgnoreCase("item")) {
					if (feedParser.getOnFeedItemHandler() != null)
						feedParser.getOnFeedItemHandler().OnFeedItem(feedParser, item);
					if (feedParser.shouldStopProcessing())
						return;
					item = null;
				}
			}
		}
	}
}