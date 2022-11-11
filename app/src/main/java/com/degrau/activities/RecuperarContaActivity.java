package com.degrau.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.degrau.databinding.ActivityRecuperarContaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContaActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityRecuperarContaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.btnEnviarEmail.setOnClickListener(view -> validarDados());
    }
    private void validarDados(){
        String email = binding.recuperarTextTitle.getText().toString().trim();

        if(!email.isEmpty()){
            recuperarSenhaFirebase(email);

        }else{
            Toast.makeText(this,"Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }
    }
    private void recuperarSenhaFirebase(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, EncontrarMentoresActivity.class));
                Toast.makeText(this,"Mandou", Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(this,"Já pode verificar seu email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}