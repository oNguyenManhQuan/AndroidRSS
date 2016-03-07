package com.licon.rssfeeds.util;

/**
 * Created by FRAMGIA\khairul.alam.licon on 26/2/16.
 */

import android.content.Context;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.AppData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtil {

    private static final int CLOCK_UNIT_1000 = 1000;
    private static final int CLOCK_UNIT_365 = 365;
    private static final int CLOCK_UNIT_90 = 90;
    private static final int CLOCK_UNIT_60 = 60;
    private static final int CLOCK_UNIT_45 = 45;
    private static final int CLOCK_UNIT_48 = 48;
    private static final int CLOCK_UNIT_31 = 31;
    private static final int CLOCK_UNIT_30 = 30;
    private static final int CLOCK_UNIT_24 = 24;
    private static final int CLOCK_UNIT_12 = 12;
    private static final int CLOCK_UNIT_1 = 1;
    private static final int CLOCK_UNIT_0 = 0;

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

    public static Long getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        if(calendar.getTime() != null) {
            parseDateToLong(calendar.getTime());
        }
        return null;
    }

    public static String getTimeDifferenceUnit(long delta, Context context)
    {
        long difference = 0;
        Long mDate = System.currentTimeMillis();

        if(mDate > delta)
        {
            difference= mDate - delta;
            final long seconds = difference/CLOCK_UNIT_1000;
            final long minutes = seconds/CLOCK_UNIT_60;
            final long hours = minutes/CLOCK_UNIT_60;
            final long days = hours/CLOCK_UNIT_24;
            final long months = days/CLOCK_UNIT_31;
            final long years = days/CLOCK_UNIT_365;

            if (seconds < CLOCK_UNIT_0) {
                return context.getString(R.string.history_not_yet);
            } else if (seconds < CLOCK_UNIT_60) {
                return seconds == CLOCK_UNIT_1 ? context.getString(R.string.history_one_second_ago) :
                        String.format(context.getString(R.string.history_seconds_ago), seconds);
            } else if (seconds < (CLOCK_UNIT_60 * CLOCK_UNIT_60)) {
                return context.getString(R.string.history_one_minute_ago);
            } else if (seconds < (CLOCK_UNIT_45 * CLOCK_UNIT_60)) {
                // 45 * 60
                return String.format(context.getString(R.string.history_minutes_ago), minutes);
            } else if (seconds < (CLOCK_UNIT_60 * CLOCK_UNIT_90)) {
                // 90 * 60
                return context.getString(R.string.history_one_hour_ago);
            } else if (seconds < (CLOCK_UNIT_24 * CLOCK_UNIT_60 * CLOCK_UNIT_60)) {
                // 24 * 60 * 60
                return String.format(context.getString(R.string.history_hours_ago), hours);
            } else if (seconds < CLOCK_UNIT_48 * CLOCK_UNIT_60 * CLOCK_UNIT_60) {
                // 48 * 60 * 60
                return context.getString(R.string.history_yesterday);
            } else if (seconds < CLOCK_UNIT_30 * CLOCK_UNIT_24 * CLOCK_UNIT_60 * CLOCK_UNIT_60) {
                // 30 * 24 * 60 * 60
                return String.format(context.getString(R.string.history_hours_ago), days);
            } else if (seconds < CLOCK_UNIT_12 * CLOCK_UNIT_30 * CLOCK_UNIT_24 * CLOCK_UNIT_60 * CLOCK_UNIT_60) {
                // 12 * 30 * 24 * 60 * 60
                return months == CLOCK_UNIT_1 ? context.getString(R.string.history_one_month_ago) :
                        String.format(context.getString(R.string.history_months_ago), months);
            } else {
                return years == CLOCK_UNIT_1 ? context.getString(R.string.history_one_year_ago) :
                        String.format(context.getString(R.string.history_years_ago), years);
            }
        }
        return null;
    }
}