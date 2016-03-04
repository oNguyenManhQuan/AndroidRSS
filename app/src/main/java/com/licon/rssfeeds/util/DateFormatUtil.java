package com.licon.rssfeeds.util;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */

import com.licon.rssfeeds.data.constants.AppData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtil {

    final static SimpleDateFormat dateFormats[] = new SimpleDateFormat[]{
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US),
            new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yy HH:mm z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm z", Locale.US),
            new SimpleDateFormat("EEE d MMM yy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE d MMM yy HH:mm z", Locale.US),
            new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("EEE d MMM yyyy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yy HH:mm:ss z", Locale.US),
            new SimpleDateFormat("d MMM yyyy HH:mm z", Locale.US),
            new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.US),

            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()),
            new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("EEE, d MMM yy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("EEE d MMM yy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("EEE d MMM yy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("EEE d MMM yyyy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("d MMM yy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("d MMM yy HH:mm:ss z", Locale.getDefault()),
            new SimpleDateFormat("d MMM yyyy HH:mm z", Locale.getDefault()),
            new SimpleDateFormat("d MMM yyyy HH:mm:ss z", Locale.getDefault()),
    };

    public static Date parseDateFromString(String date) {
        for (SimpleDateFormat format : dateFormats) {
            format.setTimeZone(TimeZone.getTimeZone(AppData.APP_TIME_ZONE));
            try {
                return format.parse(date);
            } catch (ParseException e) {
            }

            try {
                SimpleDateFormat enUSFormat = new SimpleDateFormat(format.toPattern(), Locale.US);
                return enUSFormat.parse(date);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static String parseDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(); //called without pattern
        if(date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static Long parseDateToLong(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }
}