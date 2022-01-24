package com.ismael.lolipop20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ismael.lolipop20.R;
import com.ismael.lolipop20.bbdd.LolipopBD;
import com.ismael.lolipop20.entidades.Usuario;

public class LogActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etPassword;
    private Button btnAcceder;
    private LolipopBD bd;
    private Usuario usuario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        etUsuario = findViewById(R.id.etUsuarioLogin);
        etPassword = findViewById(R.id.etContraseñaLogin);
        btnAcceder = findViewById(R.id.btnAccederLog);
        bd = new LolipopBD(this, "LolipopBD", null, 1);
        btnAcceder.setOnClickListener(l ->{
            onClickBtnAcceder();
        });
    }

    private void onClickBtnAcceder(){
        if(!etUsuario.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
            usuario = bd.consulatarUsuario(etUsuario.getText().toString(), etPassword.getText().toString());
            if(usuario != null){
                finalizarActivity();
            } else {
                Toast.makeText(this, "Las credenciales no coinciden", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se han introducido todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    private void finalizarActivity(){
        Intent intent = new Intent();
        intent.putExtra("usuarioLog",usuario);
        setResult(RESULT_OK, intent);
        bd.close();
        finish();
    }

}
