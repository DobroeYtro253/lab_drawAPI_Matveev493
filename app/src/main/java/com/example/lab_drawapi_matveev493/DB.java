package com.example.lab_drawapi_matveev493;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Save (token TEXT);";
        db.execSQL(sql);
    }

    public void addToken(String token)
    {
        String sql = "INSERT INTO Save (token) VALUES('" + token + "');";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void deleteSave(String token)
    {
        String sql = "DELETE FROM Save WHERE token = '" + token + "';";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public String selectToken()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT token FROM Save;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "null";
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
