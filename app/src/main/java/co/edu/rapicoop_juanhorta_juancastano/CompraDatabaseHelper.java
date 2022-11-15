package co.edu.rapicoop_juanhorta_juancastano;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CompraDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RapiCoop.db";
    public static final String TABLE_NAME = "Compra_Table";
    public static final String COL1 = "ID";
    public static final String COL2 = "PRODUCTS_ID";
    public static final String COL3 = "CLIENT_EMAIL";
    public static final String COL4 = "ADDRESS";
    public static final String COL5 = "STATUS";
    public static final String COL6 = "DATE";
    public static final String COL7 = "QUANTITIES";
    public static final String COL8 = "DEALER";
    public static final String COL9 = "PRICE";

    public CompraDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(" +
                COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " TEXT," +
                COL5 + " TEXT," +
                COL6 + " TEXT," +
                COL7 + " TEXT," +
                COL8 + " TEXT," +
                COL9 + " REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initData() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1, 2);
    }

    public boolean insertData(Compra compra) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, compra.getPRODUCTS_ID());
        values.put(COL3, compra.getCLIENT_EMAIL());
        values.put(COL4, compra.getADDRESS());
        values.put(COL5, compra.getSTATUS());
        values.put(COL6, compra.getDATE());
        values.put(COL7, compra.getQUANTITIES());
        values.put(COL8, compra.getDEALER());
        values.put(COL9, compra.getPRICE());

        long resultado = db.insert(TABLE_NAME, null, values);

        if (resultado == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME, null);
        return cursor;
    }

    Cursor getDataByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " WHERE ID = " + id, null);
        return cursor;
    }

    Cursor getDataByStatus(String Status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " WHERE STATUS = '" + Status + "'", null);
        return cursor;
    }

    boolean updateData(Compra compra){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL5, compra.getSTATUS());

        long resultado = db.update(TABLE_NAME, values, "ID=?", new String[]{compra.getID().toString()});

        if (resultado == -1)
            return false;
        else
            return true;
    }
}
