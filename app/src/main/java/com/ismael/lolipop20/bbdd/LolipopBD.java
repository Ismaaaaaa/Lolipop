package com.ismael.lolipop20.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ismael.lolipop20.entidades.Anuncio;
import com.ismael.lolipop20.entidades.Usuario;

import java.io.Serializable;
import java.util.ArrayList;

public class LolipopBD extends SQLiteOpenHelper implements Serializable {
    private SQLiteDatabase db;

    public LolipopBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sentenceCrearTablaAnuncios = "CREATE TABLE IF NOT EXISTS anuncios(id INTEGER PRIMARY KEY AUTOINCREMENT,id_usuario INTEGER NOT NULL, titulo TEXT, link TEXT, estado TEXT, descripcion TEXT, precio REAL, aceptaEnvios INTEGER, esExterno INTEGER,imagen BLOB, FOREIGN KEY(id_usuario) references usuarios(id))";
        String sentenceCrearTablaUsuarios = "CREATE TABLE IF NOT EXISTS usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, password TEXT NOT NULL,puntuacion REAL)";

        db.execSQL(sentenceCrearTablaUsuarios);
        db.execSQL(sentenceCrearTablaAnuncios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertarAnuncio(Anuncio anuncio){

        ContentValues valoresUsuarios = new ContentValues();
        valoresUsuarios.put("titulo", anuncio.getTitulo());
        valoresUsuarios.put("link", anuncio.getLink());
        valoresUsuarios.put("estado",anuncio.getEstado());
        valoresUsuarios.put("descripcion",anuncio.getDescripcion());
        valoresUsuarios.put("precio",anuncio.getPrecio());
        valoresUsuarios.put("aceptaEnvios", anuncio.isAceptaEnvios()?1:0);
        valoresUsuarios.put("esExterno", anuncio.isExterno()?1:0);
        valoresUsuarios.put("imagen",anuncio.getImagen());
        valoresUsuarios.put("id_usuario", anuncio.getIdUsuario());

        db.insert("anuncios", null, valoresUsuarios);
    }

    public void insertarUsuario(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("nombre", usuario.getNombre());
        valores.put("password", usuario.getPassword());
        valores.put("puntuacion", usuario.getPuntuacion());

        db.insert("usuarios", null, valores);
    }

    public void borrarAnuncio(int idAnuncio){

        String clausulaWhere = "id = ?";

        String[] whereArgs = {String.valueOf(idAnuncio)};

        db.delete("anuncios", clausulaWhere, whereArgs);
    }

    public ArrayList<Anuncio> consulatarTodo() {
        String consulta = "SELECT id,id_usuario,titulo,link,estado,descripcion,precio,aceptaEnvios,esExterno,imagen FROM anuncios";

        Cursor cursor = db.rawQuery(consulta, null);

        ArrayList<Anuncio> anuncios = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {//CAMBIAR ESTE CONSTRUCTOR
                anuncios.add(new Anuncio(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getDouble(6), cursor.getInt(7) == 1, cursor.getInt(8) == 1, cursor.getBlob(9)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return anuncios;
    }

    public Usuario consulatarUsuario(int id){
        String consulta = "SELECT id,nombre,password,puntuacion FROM usuarios WHERE id = "+ id;

        Cursor cursor = db.rawQuery(consulta,null);

        Usuario usuario = null;

        if(cursor.moveToFirst()){
            usuario = new Usuario(cursor.getInt(0), cursor.getString(1),cursor.getString(2) ,cursor.getDouble(3));
        }

        cursor.close();

        return usuario;
    }

    public Usuario consulatarUsuario(String nombre, String password){
        String consulta = "SELECT id,nombre,password,puntuacion FROM usuarios WHERE nombre = '"+ nombre + "' and password = '" + password+"'";
        Cursor cursor;
        try {
            cursor = db.rawQuery(consulta,null);
        } catch (SQLException ex){
            return null;
        }


        Usuario usuario = null;

        if(cursor.moveToFirst()){
            usuario = new Usuario(cursor.getInt(0), cursor.getString(1),cursor.getString(2) ,cursor.getDouble(2));
        }
        System.out.println(usuario);
        cursor.close();

        return usuario;
    }
}
