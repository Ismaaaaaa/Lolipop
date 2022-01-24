package com.ismael.lolipop20.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismael.lolipop20.R;
import com.ismael.lolipop20.adaptadores.Adaptador_RecylerView;
import com.ismael.lolipop20.bbdd.LolipopBD;
import com.ismael.lolipop20.entidades.Anuncio;
import com.ismael.lolipop20.entidades.Usuario;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LolipopBD lolipopBD;
    private RecyclerView recyclerView;
    private ArrayList<Anuncio> anuncios;
    private Usuario usuario;
    private FloatingActionButton btnFlotante;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lolipopBD = new LolipopBD(this,"LolipopBD",null,1);
        recyclerView = findViewById(R.id.recyclerView);
        anuncios = new ArrayList<>();
        btnFlotante = findViewById(R.id.botonFlotante);
        preferences = getPreferences(MODE_PRIVATE);
        preferencesEditor = preferences.edit();
        usuario = new Usuario();
        btnFlotante.setOnClickListener(v ->{
            onClickBtnAdd();
        });

        cargarDatos();
    }

    private void cargarDatos(){
        anuncios = lolipopBD.consulatarTodo();
        usuario.setId(preferences.getInt("idUsuario", -1));
        usuario.setNombre(preferences.getString("nombreUsuario", ""));
        usuario.setPassword(preferences.getString("passwordUsuario", ""));
        usuario.setPuntuacion(preferences.getFloat("valoracionUsuario", 0));
        if(usuario.getId() == -1){
            Toast.makeText(this, "No está logeado con ningún usuario", Toast.LENGTH_LONG).show();
        }
        gestionarAdaptadorRecyclerView();
    }

    private void gestionarAdaptadorRecyclerView(){
        Adaptador_RecylerView adaptador = new Adaptador_RecylerView(anuncios);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                        int position = recyclerView.getChildAdapterPosition(child);

                        lanzarSegundaActivity(position);

                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        recyclerView.setAdapter(adaptador);
    }

    @Override
    protected void onResume() {
        super.onResume();

        cargarDatos();

        int posicionComprada =  this.getIntent().getIntExtra("posicionComprada", -1);

        if(posicionComprada != -1){
            anuncios.remove(posicionComprada);
        }

    }

    public void ownAnuncio(Anuncio anuncio){
        Intent intento = new Intent(this, SecondActivity.class);
        intento.putExtra("AnuncioSeleccionado",anuncio);
        Usuario usuarioAnuncio = lolipopBD.consulatarUsuario(anuncio.getIdUsuario());
        intento.putExtra("UsuarioAnuncio", usuarioAnuncio);
        startActivityForResult(intento, 88);
    }

    private void lanzarWeb(String link, int position){
        Intent intento = new Intent(Intent.ACTION_VIEW);
        intento.setData(Uri.parse(link));
        intento.putExtra("posicion", position);
        startActivity(intento);
    }

    public void lanzarSegundaActivity(int position){

        Anuncio anuncioSeleccionado = anuncios.get(position);

        if(anuncioSeleccionado.isExterno()){
            lanzarWeb(anuncioSeleccionado.getLink(), position);
        } else {
            ownAnuncio(anuncioSeleccionado);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(88 == requestCode && resultCode == RESULT_OK){
            int idComprado = data.getIntExtra("idComprado", -1);
            lolipopBD.borrarAnuncio(idComprado);

        } else  if(101 == requestCode && resultCode == RESULT_OK){
            Anuncio anuncioInsertado = (Anuncio) data.getSerializableExtra("anuncioInsertado");
            lolipopBD.insertarAnuncio(anuncioInsertado);

        } else if(102 == requestCode && resultCode == RESULT_OK){
            Usuario usuarioInsertado = (Usuario) data.getSerializableExtra("usuarioInsertado");
            lolipopBD.insertarUsuario(usuarioInsertado);
        } else if(103 == requestCode && resultCode == RESULT_OK){
            usuario = (Usuario) data.getSerializableExtra("usuarioLog");
            guardarPreferencias(usuario);
            Toast.makeText(this, "Logeado con el usuario: " + usuario.getNombre(), Toast.LENGTH_SHORT).show();
        }
        cargarDatos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btnAddUser){
            onClickBtnAddUser();
        } else if(item.getItemId() == R.id.btnLog){
            onClickBtnLog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickBtnAdd(){
        if(usuario != null && usuario.getId() != -1){
            Intent intent = new Intent(this, InsertarAnuncio.class);
            intent.putExtra("idUsuario", usuario.getId());
            this.startActivityForResult(intent, 101);
        } else {
            Toast.makeText(this, "Debe de logearse para poder crear un anuncio", Toast.LENGTH_LONG).show();
        }
    }

    private void onClickBtnAddUser(){
        Intent intent = new Intent(this, InsertarUsuario.class);
        this.startActivityForResult(intent, 102);
    }

    private void onClickBtnLog(){
        Intent intent = new Intent(this, LogActivity.class);
        this.startActivityForResult(intent, 103);
    }

    private void guardarPreferencias(@NonNull Usuario usuarioMain){
        preferencesEditor.putInt("idUsuario" ,usuarioMain.getId());
        preferencesEditor.putString("nombreUsuario", usuarioMain.getNombre());
        preferencesEditor.putString("passwordUsuario", usuarioMain.getPassword());
        preferencesEditor.putFloat("valoracionUsuario", ((float) usuarioMain.getPuntuacion()));
        preferencesEditor.commit();
    }
}
