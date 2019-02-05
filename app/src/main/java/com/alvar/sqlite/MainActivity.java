package com.alvar.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre;
    private  EditText txtCantidad;
    private Spinner lstSeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtNombre=findViewById(R.id.txtNombre);
        txtCantidad=findViewById(R.id.txtCantidad);
        lstSeccion=findViewById(R.id.lstSeccion);

        String[] secciones = {"Monitor", "Disco Duro", "Memoria", "Teclado", "Ratón", "Impresora"};
        lstSeccion.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,secciones));

/*
        DB_SQLite db = new DB_SQLite(this);
        SQLiteDatabase conn = db.getWritableDatabase();
        for(long i=1;i<=200000;i++) {
            conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('uno', 123, 'monitor')");
        }
        conn.close();
*/

       // conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('uno', 123, 'monitor')");
      // conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('dos', 234, 'monitor')");
        //conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('tres', 345, 'raton')");
       // conn.execSQL("INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ('cuatro', 456, 'teclado')");

    }

    public void insertarProducto(View view) {
        String strNombre = txtNombre.getText().toString();
        String strCantidad = txtCantidad.getText().toString();
        String strSeccion = lstSeccion.getSelectedItem().toString();
        //mostrarMensaje("CONTENIDO: "+ strNombre + "" + strCantidad + "" + strSeccion);

        if(strNombre.equals("")||strCantidad.equals("")){
            mostrarMensaje("Todos los campos son obligatorios");
        }else{
            String sql = "INSERT INTO PRODUCTO (nombre, cantidad, seccion) VALUES ( '"+strNombre + "',"+ Integer.parseInt(strCantidad) + ",'" + strSeccion + "')";

            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            conn.execSQL(sql);
            conn.close();

            mostrarMensaje("EL producto" + strNombre + " ha sido insertado");
            limpiarCuadros();
        }
    }

    public void eliminarProducto(View view) {
        String strNombre = txtNombre.getText().toString();

        if(strNombre.equals("")){
            mostrarMensaje("Tiene que indicar el nombre del producto a eliminar");
        }else{
            String sql = "DELETE FROM PRODUCTO WHERE nombre LIKE '"+ strNombre + "'";

            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            conn.execSQL(sql);
            conn.close();

            mostrarMensaje("EL producto" + strNombre + " ha sido eliminado");
            limpiarCuadros();
        }
    }

    public void modificarProducto(View view) {
        String strNombre = txtNombre.getText().toString();
        String strCantidad = txtCantidad.getText().toString();
        String strSeccion = lstSeccion.getSelectedItem().toString();

        if (strCantidad.equals("")) {
            mostrarMensaje("La cantidad es obligatoria.");
        } else {
            String sql = "UPDATE PRODUCTO SET cantidad=" + Integer.parseInt(strCantidad) + ", seccion='" + strSeccion + "' WHERE nombre LIKE '" + strNombre + "'";

            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            conn.execSQL(sql);
            conn.close();

            mostrarMensaje("El producto " + strNombre + " ha sido modificado.");
            limpiarCuadros();
        }
    }



    public void buscarProducto(View view) {
        String strNombre = txtNombre.getText().toString();
        if(strNombre.equals("")){
            mostrarMensaje("Debe indicar el producto a buscar");
        }else{
            String sql = "SELECT _id, nombre, cantidad, seccion FROM PRODUCTO WHERE nombre LIKE  '"+strNombre + "'";
            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getReadableDatabase();
            Cursor cursor = conn.rawQuery(sql,null);

            if(cursor.getCount()==0){
                mostrarMensaje("El producto" + strNombre + " no existe.");
            }else{
                cursor.moveToFirst();
                Long dataProducto = cursor.getLong(cursor.getColumnIndex("_id"));
                String dataNombre = cursor.getString(cursor.getColumnIndex("nombre"));
                Integer dataCantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                String dataSeccion = cursor.getString(cursor.getColumnIndex("seccion"));

                mostrarMensaje("RECUPERADO: id" + dataProducto + "nombre" + dataNombre + "cantidad" + dataCantidad + "seccion" + dataSeccion);
                limpiarCuadros();
            }
            cursor.close();
            conn.close();
        }
    }

    public void listarProductos(View view) {
        EditText txtResultados = findViewById(R.id.txtResultados);
        txtResultados.setText("");

        String sql = "SELECT _id, nombre, cantidad, seccion FROM PRODUCTO ORDER BY nombre ASC";
        DB_SQLite db = new DB_SQLite(this);
        SQLiteDatabase conn = db.getReadableDatabase();
        Cursor cursor = conn.rawQuery(sql,null);
        if(cursor.getCount()==0){
            mostrarMensaje("La base de datos está vacía");
        }else {
            cursor.moveToFirst();
            do{
                Long dataProducto = cursor.getLong(cursor.getColumnIndex("_id"));
                String dataNombre = cursor.getString(cursor.getColumnIndex("nombre"));
                Integer dataCantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                String dataSeccion = cursor.getString(cursor.getColumnIndex("seccion"));

                String msg = "id: " + dataProducto + ", nombre: " + dataNombre + ", cantidad: " + dataCantidad + ", seccion: " + dataSeccion;
                        txtResultados.append(msg + "\n")  ;
            }while (cursor.moveToNext());
        }
        cursor.close();
        conn.close();
    }

    private void mostrarMensaje (String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void limpiarCuadros(){
        txtNombre.setText("");
        txtCantidad.setText("");
    }
}
