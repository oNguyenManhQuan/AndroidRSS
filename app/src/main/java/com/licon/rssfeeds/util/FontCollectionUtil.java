package com.licon.rssfeeds.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontCollectionUtil {
    public static final String FONT_ROBOTO_BOLD = "Roboto-Bold.ttf";
    public static final String FONT_ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static final String FONT_ROBOTO_LIGHT = "Roboto-Light.ttf";
    public static final String FONT_ROBOTO_THIN = "Roboto-Thin.ttf";

    private static Hashtable<String, Typeface> fontCollection = new Hashtable<String, Typeface>();

    public static void init(Context context) {
        get(FONT_ROBOTO_REGULAR, context);
    }

    public static Typeface get(String name, Context context) {
        Typeface typeface = fontCollection.get(name);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), name);
            } catch (Exception e) {
                return null;
            }
            fontCollection.put(name, typeface);
        } else {

        }
        return typeface;
    }
}