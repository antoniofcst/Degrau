package com.degrau;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.degrau.databinding.ActivityLoginBinding;
import com.degrau.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view ->
              startActivity(new Intent(this, LoginActivity.class)));
        binding.btnCriarConta.setOnClickListener(view ->
                startActivity(new Intent(this, CadastroActivityProfessor.class)));

       /*new Handler(getMainLooper()).postDelayed(() -> {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }, 3000);
        */
    }
}