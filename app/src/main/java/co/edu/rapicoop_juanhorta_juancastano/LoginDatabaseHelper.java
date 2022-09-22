package co.edu.rapicoop_juanhorta_juancastano;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LoginDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RapiCoop.db";
    public static final String TABLE_NAME = "Usuarios_Table";
    public static final String COL1 = "ID";
    public static final String COL2 = "FULLNAME";
    public static final String COL3 = "USERNAME";
    public static final String COL4 = "EMAIL";
    public static final String COL5 = "PASSWORD";
    public static final String COL6 = "GENDER";

    public LoginDatabaseHelper(@Nullable Context context){
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
                COL6 + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initData(){
        SQLiteDatabase db = this. getWritableDatabase();
        onUpgrade(db, 1,2);
    }

    public boolean insertData(Usuario user){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, user.getFULLNAME());
        values.put(COL3, user.getUSERNAME());
        values.put(COL4, user.getEMAIL());
        values.put(COL5, user.getPASSWORD());
        values.put(COL6, user.getGENDER());

        long resultado = db.insert(TABLE_NAME, null, values);

        if(resultado == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME, null);
        return cursor;
    }

    Cursor getDataByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " WHERE ID = " + id, null);
        return cursor;
    }

    Cursor getDataByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " WHERE EMAIL = '" + email + "'", null);
        return cursor;
    }
}
