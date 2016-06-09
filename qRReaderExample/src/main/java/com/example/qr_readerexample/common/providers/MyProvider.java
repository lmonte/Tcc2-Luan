package com.example.qr_readerexample.common.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.qr_readerexample.controllers.SQLiteFactory;

import java.util.HashMap;

/**
 * Created by lmonte on 09/05/16.
 */
public class MyProvider extends ContentProvider{

    // Authority do nosso provider, a ser usado nas Uris.
    public static final String AUTHORITY = "com.example.qr_readerexample.common.providers.MyProvider";
    // Tag usada para imprimir os logs.
    public static final String TAG = "ProvidersLog";

    // Instância da classe utilitária
    private SQLiteFactory database;

    // Uri matcher - usado para extrair informações das Uris
    private static final UriMatcher mMatcher;


    private static HashMap<String, String> mProjection = new HashMap<String, String>();

    static {
        mProjection.put(SQLiteFactory.COLUMN_ID, SQLiteFactory.COLUMN_ID);
        mProjection.put(SQLiteFactory.COLUMN_NAME,SQLiteFactory.COLUMN_NAME);
        mProjection.put(SQLiteFactory.COLUMN_VIDEO,SQLiteFactory.COLUMN_VIDEO);
    }

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITY, SQLiteFactory.TABLE_MACHINE, SQLiteFactory.DATABASE_VERSION);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
