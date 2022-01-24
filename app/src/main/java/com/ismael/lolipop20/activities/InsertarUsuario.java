package com.ismael.lolipop20.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ismael.lolipop20.R;
import com.ismael.lolipop20.entidades.Usuario;

public class InsertarUsuario extends AppCompatActivity {

    private EditText etInsertarUsuario;
    private EditText etInsertarPassword;
    private RatingBar ratingBarUsuario;
    private Button btnGuardarUsuario;
    private Usuario nuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_usuario);

        etInsertarUsuario = findViewById(R.id.etNombreUsuario);
        etInsertarPassword = findViewById(R.id.etPasswordUsuario);
        ratingBarUsuario = findViewById(R.id.ratingBarIntroducirUsuario);
        btnGuardarUsuario = findViewById(R.id.btnGuardarUsuario);

        btnGuardarUsuario.setOnClickListener(listener ->{
            cargarUsuario();
        });

    }

    private void cargarUsuario(){
        if(!etInsertarUsuario.getText().toString().isEmpty() && !etInsertarPassword.getText().toString().isEmpty()){
            nuevoUsuario = new Usuario(etInsertarUsuario.getText().toString(), etInsertarPassword.getText().toString(), ratingBarUsuario.getRating());
            finalizarActivity();
        } else {
            Toast.makeText(this, "No se han introducido todos los datos", Toast.LENGTH_SHORT);
        }
    }

    private void finalizarActivity(){
        Intent intent = new Intent();
        intent.putExtra("usuarioInsertado", nuevoUsuario);
        setResult(RESULT_OK, intent);
        finish();
    }

}
