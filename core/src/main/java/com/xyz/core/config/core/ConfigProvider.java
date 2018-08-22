package com.xyz.core.config.core;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.xyz.core.config.ConfigSQLiteOpenHelper;
import com.xyz.core.config.ConfigTable;


/**
 * Created by jason on 16/5/12.
 */
public class ConfigProvider extends ContentProvider {

    public static final int VERSION = 1;
    public static final String NAME = "config.db";
    static final String TAG = ConfigProvider.class.getName();
    private static final String RAWQUERY = "rawquery";
    private static final int MATCH_RAW = 0;
    //config
    private static final int MATCH_CONFIG = 1;
    private static final int MATCH_CONFIG_ITEM = 2;
    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        sUriMatcher.addURI(ConfigTable.AUTHORITY, RAWQUERY, MATCH_RAW);
        //config
        sUriMatcher.addURI(ConfigTable.AUTHORITY, ConfigTable.ConfigColumns.TABLE_NAME, MATCH_CONFIG);
        sUriMatcher.addURI(ConfigTable.AUTHORITY, ConfigTable.ConfigColumns.TABLE_NAME + "/#", MATCH_CONFIG_ITEM);

    }

    private ConfigSQLiteOpenHelper mOpenHelper;

    private static String appendBaseColumnsID(Uri uri, String where) {
        if (!TextUtils.isEmpty(where)) {
            where = where + " AND ";
        }
        String append = BaseColumns._ID + " = "
                + uri.getPathSegments().get(1);

        where = where == null ? append : where + append;

        return where;
    }

    @Override
    public boolean onCreate() {
        try {
            mOpenHelper = ConfigSQLiteOpenHelper.getInstance(getContext());
            mOpenHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            if (null != mOpenHelper) {
                mOpenHelper.close();
                mOpenHelper = null;
            }
        }

        return true;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        int count = 0;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {


            // config
            case MATCH_CONFIG_ITEM:
                where = appendBaseColumnsID(uri, where);
                // no break
            case MATCH_CONFIG:
                count = db.delete(ConfigTable.ConfigColumns.TABLE_NAME, where, whereArgs);
                break;

            default:
                throw new UnsupportedOperationException("Cannot delete that URI: "
                        + uri);
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

        }

        return count;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        @SuppressWarnings("unused")
        long rowId = 0;
        Uri result = null;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {


            //config
            case MATCH_CONFIG:
                rowId = db.insert(ConfigTable.ConfigColumns.TABLE_NAME, null, values);
                result = ConfigTable.ConfigColumns.CONTENT_URI;
                break;


            default:
                throw new UnsupportedOperationException("Cannot insert that URI: "
                        + uri);
        }

        if (rowId > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            return ContentUris.withAppendedId(result, rowId);
        } else {
            return null;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        boolean bRaw = false;
        Cursor c = null;
        SQLiteQueryBuilder qb = null;
        qb = new SQLiteQueryBuilder();

        int match = sUriMatcher.match(uri);
        switch (match) {


            //config
            case MATCH_CONFIG_ITEM:
                selection = appendBaseColumnsID(uri, selection);
                //no break
            case MATCH_CONFIG:
                qb.setTables(ConfigTable.ConfigColumns.TABLE_NAME);
                break;


            default:
                throw new UnsupportedOperationException("Cannot query that URI: "
                        + uri);
        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (bRaw) {
            c = db.rawQuery(selection, selectionArgs);
        } else {
            c = qb.query(db, projection, selection, selectionArgs, null, null,
                    sortOrder);
        }

        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {

            //config
            case MATCH_CONFIG_ITEM:
                selection = appendBaseColumnsID(uri, selection);
                //no break
            case MATCH_CONFIG:
                count = db.update(ConfigTable.ConfigColumns.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Cannot update that URI: "
                        + uri);
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }


}
