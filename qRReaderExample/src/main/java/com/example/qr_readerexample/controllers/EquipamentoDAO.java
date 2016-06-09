package com.example.qr_readerexample.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.qr_readerexample.R;
import com.example.qr_readerexample.common.util.FileUtil;
import com.example.qr_readerexample.models.EquipamentoBO;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmonte on 26/04/16.
 */
public class EquipamentoDAO {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteFactory dbFactory;
    private SQLiteFactory TABLE_MACHINE;
    private String[] allColumns = {
            SQLiteFactory.COLUMN_ID,
            SQLiteFactory.COLUMN_NAME,
            SQLiteFactory.COLUMN_VIDEO
    };

    public EquipamentoDAO(Context context) {
        this.dbFactory = new SQLiteFactory(context);
    }

    public void open() throws SQLException {
        database = dbFactory.getWritableDatabase();
    }

    public void close() {
        dbFactory.close();
    }

    public EquipamentoBO findById(final Long id) throws SQLException {
        this.open();
        Cursor cursor = this.database.query(SQLiteFactory.TABLE_MACHINE, allColumns, SQLiteFactory.COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        EquipamentoBO object = bind(cursor);
        cursor.close();
        this.close();
        return object;
    }

    public EquipamentoBO create(final String name) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLiteFactory.COLUMN_NAME, name);
        long insertedId = database.insert(SQLiteFactory.TABLE_MACHINE, null, values);
        this.close();
        EquipamentoBO object = findById(insertedId);
        return object;
    }

    public void initData(Context context) throws SQLException, IOException {

        int resourceId = R.raw.schema;
        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));
        this.open();

        // TEST CODE
        this.database.execSQL("DELETE FROM TB_EQUIPAMENTO;");


        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            if (insertStmt != null && !insertStmt.isEmpty()){
                this.database.execSQL(insertStmt);
            }
        }
        insertReader.close();
        this.close();



//        List<EquipamentoBO> lstObjects = this.findAll();
//        if (lstObjects.size() == 0) {
//            ContentValues insertValues = new ContentValues();
//            insertValues.put("_id", "1");
//            insertValues.put("nome", "Supino 1");
//            insertValues.put("_id", "2");
//            insertValues.put("nome", "Supino 2");
//            insertValues.put("_id", "3");
//            insertValues.put("nome", "Supino 3");
//            insertValues.put("_id", "4");
//            insertValues.put("nome", "Supino 4");
//            insertValues.put("_id", "5");
//            insertValues.put("nome", "Supino 5");
//            insertValues.put("_id", "6");
//            insertValues.put("nome", "Supino 6");
//            insertValues.put("_id", "7");
//            insertValues.put("nome", "Supino 7");
//            insertValues.put("video", VIDEO_1);
//            insertValues.put("_id", "8");
//            insertValues.put("nome", "Supino 8");
//            insertValues.put("_id", "9");
//            insertValues.put("nome", "Supino 9");
//            insertValues.put("_id", "10");
//            insertValues.put("nome", "Supino 10");
//            database.insert("tb_equipamento", null, insertValues);
//        }
//        this.close();
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap getVideo(int i){

        String qu = "select video from tb_equipamento where _id=" + i ;
        Cursor cur = database.rawQuery(qu, null);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }

    public void delete(EquipamentoBO equipamento) {
        long id = equipamento.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(SQLiteFactory.TABLE_MACHINE, SQLiteFactory.COLUMN_ID
                + " = " + id, null);
    }

    public List<EquipamentoBO> findAll() {
        List<EquipamentoBO> lstObjects = new ArrayList<EquipamentoBO>();

        this.database.execSQL("select * from tb_equipamento");


        Cursor cursor = database.query(SQLiteFactory.TABLE_MACHINE, allColumns, null, null, null, null, SQLiteFactory.COLUMN_NAME);



        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EquipamentoBO equipamento = bind(cursor);
            lstObjects.add(equipamento);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return lstObjects;
    }


    private EquipamentoBO bind(Cursor cursor) {
        EquipamentoBO object = new EquipamentoBO();
        object.setId(cursor.getLong(0));
        object.setName(cursor.getString(1));
        object.setVideo(cursor.getBlob(2));
        return object;
    }
}
