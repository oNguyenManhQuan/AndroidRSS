package com.licon.rssfeeds.util.parser;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */

import com.licon.rssfeeds.data.constants.RSSData;
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
            if (in_image && eventType == XmlPullParser.END_TAG && parser.getName().equals(RSSData.ATTRIBUTE_FEEDITEM_IMAGE)) {
                in_image = false;
                continue;
            }
            if (eventType != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            // these are elements about the thumbnail
            if (in_image) {
                if (name.equals(RSSData.ATTRIBUTE_FEEDITEM_MEDIA_URL))
                    feed.setThumbnail(parser.nextText());
                continue;
            }

            if (name.equals(RSSData.ATTRIBUTE_FEEDITEM_ITEM)) {
                break;
            } else if (name.equals(RSSData.ATTRIBUTE_FEEDITEM_IMAGE)) {
                in_image = true;
                continue;
            } else if (parser.getDepth() != 3) {
                continue;
            } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_PUBLISHED_DATE)) {
                Date date = DateFormatUtil.parseDate(parser.nextText());
                if (date != null)
                    feed.setPubDate(date);
            } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_LASTBUILT_DATE)) {
                Date date = DateFormatUtil.parseDate(parser.nextText());
                if (date != null)
                    feed.setLastBuildDate(date);
            } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_TITLE) && parser.getNamespace().equals("")) {
                feed.setTitle(parser.nextText());
            } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_THUMBNAIL) && parser.getNamespace().equals(NAMESPACE_MEDIA)) {
                feed.setThumbnail(parser.getAttributeValue("", RSSData.ATTRIBUTE_FEEDITEM_MEDIA_URL));
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
                if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_ITEM)) {
                    item = new FeedItem();
                } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_GUID)) {
                    item.setGuid(parser.nextText());
                } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_TITLE) && parser.getNamespace().equals("")) {
                    item.setTitle(parser.nextText());
                } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_LINK)) {
                    String rel = parser.getAttributeValue(null, RSSData.ATTRIBUTE_FEEDITEM_REL);
                    if (rel != null && rel.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_PAYMENT)) {
                        item.setPaymentURL(parser.getAttributeValue(null, RSSData.ATTRIBUTE_FEEDITEM_HREF));
                    } else {
                        item.setLink(parser.nextText());
                    }
                } else if (namespace.equals("") && name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_DESCRIPTION)) {
                    item.setDescription(parser.nextText());
                } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_CATEGORY)) {
                    item.setCategory(parser.nextText());
                } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_AUTHOR)) {
                    item.setAuthor(parser.nextText());
                } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_PUBLISHED_DATE)) {
                    item.setPublicationDate(DateFormatUtil.parseDate(parser.nextText()));
                } else if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_ENCLOSURE)) {
                    item.setMediaURL(parser.getAttributeValue(null, RSSData.ATTRIBUTE_FEEDITEM_MEDIA_URL));
                    try {
                        item.setMediaSize(Long.valueOf(parser.getAttributeValue(null, RSSData.ATTRIBUTE_FEEDITEM_MEDIA_SIZE)));
                    } catch (Exception e) {
                        item.setMediaSize(0L);
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                String name = parser.getName();
                if (name.equalsIgnoreCase(RSSData.ATTRIBUTE_FEEDITEM_ITEM)) {
                    if (feedParser.getOnFeedItemHandler() != null)
                        feedParser.getOnFeedItemHandler().OnFeedItem(feedParser, item);
                    if (feedParser.shouldStopProcessing())
                        return;
                }
            }
        }
    }
}