package uk.co.alexks.deadliner.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by ashaw on 08/03/2017.
 */

public class DeadlineDB {

    private DeadlineDB(){}

    public static class DBSchema implements BaseColumns{
        public static final String TABLE_NAME = "deadlines";
        public static final String COL_NAME = "name";
        public static final String COL_TYPE = "type";
        public static final String COL_DATE = "date";
        public static final String COL_TIME = "time";
        public static final String COL_NOTIFY_LENGTH = "notifylength";
        public static final String COL_NOTIFY_UNITS = "notifyunits";
        public static final String COL_NOTES = "notes";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBSchema.TABLE_NAME + " (" +
            DBSchema._ID + " INTEGER PRIMARY KEY," +
            DBSchema.COL_NAME + " TEXT," +
            DBSchema.COL_TYPE + " TEXT," +
            DBSchema.COL_DATE + " TEXT," +
            DBSchema.COL_TIME + " TEXT," +
            DBSchema.COL_NOTIFY_LENGTH + " INTEGER," +
            DBSchema.COL_NOTIFY_UNITS + " TEXT," +
            DBSchema.COL_NOTES + " TEXT)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBSchema.TABLE_NAME;

    public static final String SQL_GET_ALL_ENTRIES = "SELECT * FROM " + DBSchema.TABLE_NAME;

    public static final String SQL_REMOVE_ROW = "DELETE FROM " + DBSchema.TABLE_NAME + " WHERE " + DBSchema._ID +"=";

}
