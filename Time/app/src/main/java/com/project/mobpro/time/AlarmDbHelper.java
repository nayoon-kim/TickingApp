package com.project.mobpro.time;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Alarm.db";

    private static final String TEXT_TYPE="TEXT";
    private static final String COMMA_SEP=",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+AlarmContract.AlarmEntry.TABLE_NAME+"("+
                    AlarmContract.AlarmEntry._ID+" INTERGER PRIMARY KEY,"+
                    AlarmContract.AlarmEntry.COLUMN_NAME_TIME+TEXT_TYPE+COMMA_SEP+
                    AlarmContract.AlarmEntry.COLUMN_NAME_DAY+TEXT_TYPE+COMMA_SEP+
                    AlarmContract.AlarmEntry.COLUMN_NAME_SOUND+TEXT_TYPE+COMMA_SEP+
                    AlarmContract.AlarmEntry.COLUMN_NAME_VIB+TEXT_TYPE+" )";
    private static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS "+ AlarmContract.AlarmEntry.TABLE_NAME;

    public AlarmDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,DATABASE_NAME , factory,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
