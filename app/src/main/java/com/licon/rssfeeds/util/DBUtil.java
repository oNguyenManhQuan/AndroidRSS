package com.licon.rssfeeds.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by FRAMGIA\khairul.alam.licon on 3/3/16.
 */
public class DBUtil {
    public static boolean isDataExist(String TableName, String dbfield, String fieldValue, SQLiteDatabase sqLiteDatabase) {
        String Query = "SELECT * FROM " + TableName + " WHERE " + dbfield + " = " + fieldValue;
        Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}