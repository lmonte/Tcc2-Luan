package com.example.qr_readerexample.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lmonte on 26/04/16.
 */
public class SQLiteFactory extends SQLiteOpenHelper {

    public static final String TABLE_MACHINE = "tb_equipamento";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "nome";
    public static final String COLUMN_VIDEO = "video";
    private static final String DATABASE_NAME = "tcc.db";
    public static final int DATABASE_VERSION = 1;


    // Database creation sql statement
    private static final String CREATE_DATABASE_QUERY;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append(" CREATE TABLE ");
        builder.append(TABLE_MACHINE).append(" ( ");
        builder.append(COLUMN_ID).append(" integer primary key, ");
        builder.append(COLUMN_NAME).append(" text not null ,");
        builder.append(COLUMN_VIDEO).append(" BLOB");
        builder.append(" ) ");

        CREATE_DATABASE_QUERY = builder.toString();
    }

    public SQLiteFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteFactory.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MACHINE);
        onCreate(db);
    }
}
