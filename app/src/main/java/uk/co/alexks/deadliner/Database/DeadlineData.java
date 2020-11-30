package uk.co.alexks.deadliner.Database;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.alexks.deadliner.model.ListItem;
import uk.co.alexks.deadliner.ui.MainActivity;

/**
 * Created by ashaw on 08/03/2017.
 */

public class DeadlineData {

    private DeadlineDBHelper DbHelper;
    private SQLiteDatabase db;

    public DeadlineData(DeadlineDBHelper dbh, SQLiteDatabase db){
        DbHelper = dbh;
        this.db = db;
    }


    public List<ListItem> getAllData(){
        List<ListItem> data = new ArrayList<>();

        Cursor curs = db.rawQuery(DeadlineDB.SQL_GET_ALL_ENTRIES, null);

        for(int i=0; i < curs.getCount(); i++){
            ListItem item = new ListItem();
            curs.moveToPosition(i);
            item.setName(curs.getString(curs.getColumnIndex(DeadlineDB.DBSchema.COL_NAME)));
            item.setType(curs.getString(curs.getColumnIndex(DeadlineDB.DBSchema.COL_TYPE)));
            item.setNote(curs.getString(curs.getColumnIndex(DeadlineDB.DBSchema.COL_NOTES)));
            item.setDate(curs.getString(curs.getColumnIndex(DeadlineDB.DBSchema.COL_DATE)));
            item.setTime(curs.getString(curs.getColumnIndex(DeadlineDB.DBSchema.COL_TIME)));
            item.setNotifyTime(curs.getString(curs.getColumnIndex(DeadlineDB.DBSchema.COL_NOTIFY_LENGTH)));
            item.setNotifyUnit(curs.getString(curs.getColumnIndex(DeadlineDB.DBSchema.COL_NOTIFY_UNITS)));
            item.setDbId(curs.getString(curs.getColumnIndex(DeadlineDB.DBSchema._ID)));
            data.add(item);
        }
        curs.close();
        return data;
    }

    public void removeRow(String id){
        String where = DeadlineDB.DBSchema._ID + "=" + id;
        db.delete(DeadlineDB.DBSchema.TABLE_NAME, where, null);
    }

}
