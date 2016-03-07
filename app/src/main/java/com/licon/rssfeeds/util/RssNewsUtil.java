package com.licon.rssfeeds.util;

/**
 * Created by FRAMGIA\khairul.alam.licon on 8/3/16.
 */
public class RssNewsUtil {
    private static final String LINK_NOUN = "Link: ";
    public static StringBuilder newsBuilder = new StringBuilder();

    public static StringBuilder getNewsBuilder() {
        return newsBuilder;
    }

    public static void generateNews(String title, String published_on,
                                    String description, String media_url,
                                    String link) {
        newsBuilder.append(title)
                .append("\n")
                .append(published_on)
                .append("\n\n")
                .append(description)
                .append("\n")
                .append(media_url)
                .append("\n\n")
                .append(LINK_NOUN).append(link)
                .append("\n");
    }
}