package com.alvar.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_SQLite extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "tienda.sqLite";
    private static  final int  DATABASE_VERSION = 1;

    private static  final String SQL_CREATE_ENTRIES = "CREATE TABLE PRODUCTO (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, cantidad INTEGER, seccion TEXT)";
    private static  final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS PRODUCT";


    public DB_SQLite( Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
