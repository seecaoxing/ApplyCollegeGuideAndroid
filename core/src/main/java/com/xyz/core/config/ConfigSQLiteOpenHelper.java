package com.xyz.core.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xyz.core.config.core.ConfigProvider;

/**
 * Created by jason on 16/5/12.
 */
public class ConfigSQLiteOpenHelper extends SQLiteOpenHelper {

    static final String TAG = ConfigSQLiteOpenHelper.class.getName();

    private static ConfigSQLiteOpenHelper instance = null;
    Context mContext;

    private ConfigSQLiteOpenHelper(Context context, String name,
                                   SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public ConfigSQLiteOpenHelper(Context context) {
        this(context, ConfigProvider.NAME, null, ConfigProvider.VERSION);

    }

    static public ConfigSQLiteOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ConfigSQLiteOpenHelper(context);
        }

        return instance;
    }

    private static void dropTable(SQLiteDatabase db, String name) {
        db.execSQL(" drop table if exists " + name);
    }

    private static void dropView(SQLiteDatabase db, String name) {
        db.execSQL(" drop view if exists " + name);
    }

    private static void alterTable(SQLiteDatabase db, String tableName,
                                   String column, String des) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ALTER TABLE").append(' ').append(tableName)
                .append(' ').append("ADD COLUMN").append(' ').append(column)
                .append(' ').append(des);

        db.execSQL(buffer.toString());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == newVersion) {
            return;
        }

    }

    private void onCreateNewDB(SQLiteDatabase db) {

        onCreate(db);
    }

    void createTable(SQLiteDatabase db) {

        createTableConfig(db);        //config

    }

    //config
    void createTableConfig(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + ConfigTable.ConfigColumns.TABLE_NAME
                + "("
                + ConfigTable.ConfigColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ConfigTable.ConfigColumns.C_GROUP + " TEXT not null,"
                + ConfigTable.ConfigColumns.C_NAME + " TEXT not null,"
                + ConfigTable.ConfigColumns.C_VALUE + " TEXT,"
                + ConfigTable.ConfigColumns.C_TYPE + " INTEGER default 2 ,"
                + "UNIQUE (" + ConfigTable.ConfigColumns.C_NAME + "," + ConfigTable.ConfigColumns.C_GROUP + " ) ON CONFLICT REPLACE"
                + ")";
        db.execSQL(sql);
    }

}
