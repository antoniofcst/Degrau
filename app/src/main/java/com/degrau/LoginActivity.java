package com.degrau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.degrau.databinding.ActivityCadastroProfessorBinding;
import com.degrau.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding bindingLogin;
    private ActivityCadastroProfessorBinding bindingProf;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingLogin = ActivityLoginBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();

        setContentView(bindingLogin.getRoot());

        mAuth = FirebaseAuth.getInstance();
        bindingLogin.esqueceuSenha.setOnClickListener(view -> {
            startActivity(new Intent(this, RecuperarContaActivity.class));
        });
        bindingLogin.btnEntrar.setOnClickListener(view -> validarDados());
    }
    private void validarDados(){
        String email = bindingLogin.editEmailProf.getText().toString().trim();
        String senha = bindingLogin.editSenhaProf.getText().toString().trim();
        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                loginFirebase(email, senha);
            }else{
                Toast.makeText(this,"Informe uma senha", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }

    }
    private void loginFirebase(String email,String senha){

        mAuth.signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this,"Login com Sucesso", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else{
                Toast.makeText(this,"Erro no login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}