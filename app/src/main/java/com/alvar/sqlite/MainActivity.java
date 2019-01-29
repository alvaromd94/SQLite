package com.alvar.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB_SQLite db = new DB_SQLite(this);
        SQLiteDatabase conn = db.getWritableDatabase();

        conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('uno', 123, 'monitor')");
        conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('dos', 234, 'monitor')");
        conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('tres', 345, 'raton')");
        conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('cuatro', 456, 'teclado')");

        conn.close();


    }

    public void guardarProducto(View view) {
    }

    public void eliminarProducto(View view) {
    }

    public void modificarProducto(View view) {
    }

    public void buscarProducto(View view) {
    }

    public void listarProducto(View view) {
    }
}
