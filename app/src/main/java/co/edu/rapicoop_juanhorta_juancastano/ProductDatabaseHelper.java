package co.edu.rapicoop_juanhorta_juancastano;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RapiCoop.db";
    public static final String TABLE_NAME = "Productos_Table";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "DESCRIPTION";
    public static final String COL4 = "PRICE";
    public static final String COL5 = "QUANTITY";
    public static final String COL6 = "EMAIL";

    public ProductDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(" +
                COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " REAL," +
                COL5 + " INTEGER," +
                COL6 + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initData() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1, 2);
    }

    public boolean insertData(Producto product) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, product.getNAME());
        values.put(COL3, product.getDESCRIPTION());
        values.put(COL4, product.getPRICE());
        values.put(COL5, product.getQUANTITY());
        values.put(COL6, product.getEMAIL());

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

}
