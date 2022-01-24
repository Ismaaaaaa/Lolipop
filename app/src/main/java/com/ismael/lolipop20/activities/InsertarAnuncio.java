package com.ismael.lolipop20.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ismael.lolipop20.R;
import com.ismael.lolipop20.bbdd.LolipopBD;
import com.ismael.lolipop20.entidades.Anuncio;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class InsertarAnuncio extends AppCompatActivity {

    private EditText etTituloAnuncio,etPrecio,etLink, etDescripcion;
    private Spinner spinnerEstadoAnuncio;
    private CheckBox cbExterno;
    private Switch switchAceptaEnvios;
    private TextView tvLink;
    private Button btnGuardar;
    private final int PHOTO_GALLERY_SELECTED  = 0;
    private final int PHOTO_CAMERA_SELECTED = 1;
    private int idUsuario;
    private Anuncio anuncioInsertado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar_anuncio);

        inicializarComponentes();
    }

    private void inicializarComponentes(){

        idUsuario = getIntent().getIntExtra("idUsuario", -1);
        etTituloAnuncio = findViewById(R.id.etInsertarTitulo);
        etPrecio = findViewById(R.id.editTextNumberDecimal);
        etLink = findViewById(R.id.etInsertarLink);
        etDescripcion = findViewById(R.id.etInsertarDescripcion);
        spinnerEstadoAnuncio = findViewById(R.id.spinnerInsertarEstado);
        cbExterno = findViewById(R.id.checkBoxExterno);
        switchAceptaEnvios = findViewById(R.id.switchAceptaEnvios);
        tvLink = findViewById(R.id.tvInsertarLink);
        btnGuardar = findViewById(R.id.btnGuardar);

        String[] estadosDisponibles = getResources().getStringArray(R.array.valoresEstado);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_insertar_layout, R.id.TextView,estadosDisponibles);

        spinnerEstadoAnuncio.setAdapter(arrayAdapter);

        switchAceptaEnvios.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                switchAceptaEnvios.setSelected(true);
            } else {

                switchAceptaEnvios.setSelected(false);
            }
        });

        cbExterno.setOnClickListener(l ->{
            if(cbExterno.isChecked()){
                tvLink.setVisibility(View.VISIBLE);
                etLink.setVisibility(View.VISIBLE);
            } else {
                tvLink.setVisibility(View.INVISIBLE);
                etLink.setVisibility(View.INVISIBLE);
            }
        });


        btnGuardar.setOnClickListener(onClinListener ->{
            guardarAnuncio();
        });
    }

    private void abrirGaleria(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setMessage(R.string.alertaGalería)
                .setTitle(R.string.elegirProveedor)
                .setPositiveButton(R.string.galeria, (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PHOTO_GALLERY_SELECTED);
                    dialog.cancel();
                })
                .setNegativeButton(R.string.camara, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        abrirCamara();
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void abrirCamara(){
        Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camara.resolveActivity(getPackageManager()) != null){
            startActivityForResult(camara, PHOTO_CAMERA_SELECTED);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_GALLERY_SELECTED && resultCode == RESULT_OK && resultCode != RESULT_CANCELED){
            Uri uriImagen = data.getData();
            Bitmap bitmap = null;
            byte[] bytes = null;

            try{
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriImagen));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(bitmap != null){
                bytes = bitmap_a_byteArray(bitmap);
            }

            if(bytes != null){
                anuncioInsertado= new Anuncio(idUsuario,etTituloAnuncio.getText().toString(), etLink.getText().toString(), spinnerEstadoAnuncio.getSelectedItem().toString(), etDescripcion.getText().toString(), Double.parseDouble(etPrecio.getText().toString()), switchAceptaEnvios.isSelected(), cbExterno.isChecked(), bytes);
                acabarAtivity();
            }
            Toast.makeText(this, "Se ha insertado el anuncio", Toast.LENGTH_SHORT).show();
        } else if(requestCode == PHOTO_CAMERA_SELECTED && resultCode == RESULT_OK && resultCode != RESULT_CANCELED){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            byte[] bytes = null;
            if(bitmap != null){
                bytes = bitmap_a_byteArray(bitmap);
            }
            if(bytes != null){
                anuncioInsertado= new Anuncio(idUsuario,etTituloAnuncio.getText().toString(), etLink.getText().toString(), spinnerEstadoAnuncio.getSelectedItem().toString(), etDescripcion.getText().toString(), Double.parseDouble(etPrecio.getText().toString()), switchAceptaEnvios.isSelected(), cbExterno.isChecked(), bytes);
                acabarAtivity();
            }
        }
    }

    private byte[] bitmap_a_byteArray(Bitmap bitmap){
        byte[] bytes = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,stream);
        bytes = stream.toByteArray();
        bitmap.recycle();

        return bytes;
    }

    public void guardarAnuncio(){

        if(!etTituloAnuncio.getText().toString().isEmpty() && !etPrecio.getText().toString().isEmpty()){
            abrirGaleria();

        } else {
            Toast.makeText(this, "Faltan datos en el formulario", Toast.LENGTH_SHORT).show();
        }
    }

    private void acabarAtivity(){
        Intent intent = new Intent();
        intent.putExtra("anuncioInsertado", anuncioInsertado);
        setResult(RESULT_OK, intent);
        finish();
    }

}
