package com.ismael.lolipop20.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ismael.lolipop20.R;
import com.ismael.lolipop20.entidades.Anuncio;
import com.ismael.lolipop20.entidades.Usuario;

public class SecondActivity extends AppCompatActivity {

    private Anuncio anuncio;
    private Usuario usuarioAnuncio;
    private ImageView imagen;
    private TextView tvTitulo;
    private TextView tvPrecio;
    private TextView tvAceptaEnvios;
    private TextView tvEstado;
    private TextView tvNombreVendedor;
    private TextView tvDescripcion;
    private RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imagen = findViewById(R.id.imagenDetalle);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvAceptaEnvios = findViewById(R.id.tvAceptaEnvios);
        tvEstado = findViewById(R.id.tvEstado);
        ratingBar = findViewById(R.id.ratingBar);
        tvNombreVendedor = findViewById(R.id.tvNombreVendedor);
        tvDescripcion = findViewById(R.id.tvDescripcion);


        anuncio = (Anuncio) this.getIntent().getSerializableExtra("AnuncioSeleccionado");
        usuarioAnuncio = (Usuario) this.getIntent().getSerializableExtra("UsuarioAnuncio");
        System.out.println(usuarioAnuncio.getPuntuacion());
        cargar();

    }
    private void cargar(){
        tvTitulo.setText(anuncio.getTitulo());
        tvPrecio.setText(anuncio.getPrecio() + " €");
        Bitmap bitmap = BitmapFactory.decodeByteArray(anuncio.getImagen(), 0, anuncio.getImagen().length);
        if(bitmap != null){
            imagen.setImageBitmap(bitmap);
        }
        tvEstado.setText(anuncio.getEstado());
        if(anuncio.isAceptaEnvios()){
            tvAceptaEnvios.setText("Acepta envios");
        } else {
            tvAceptaEnvios.setText("No acepta envios");
        }
        ratingBar.setRating((float)usuarioAnuncio.getPuntuacion());
        tvDescripcion.setText(anuncio.getDescripcion());
        tvNombreVendedor.setText(usuarioAnuncio.getNombre());
    }

    public void onClickBtnComprar(View view){

        Intent intent = new Intent();
        intent.putExtra("idComprado", anuncio.getId());
        setResult(RESULT_OK, intent);
        Toast.makeText(this, "Se ha comprado el artículo " + anuncio.getTitulo() + " por importe de " + anuncio.getPrecio() + "€", Toast.LENGTH_SHORT).show();
        finish();
    }

}
