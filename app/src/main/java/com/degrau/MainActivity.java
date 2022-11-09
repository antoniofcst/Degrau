package com.degrau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.degrau.databinding.ActivityMainBinding;
import com.degrau.databinding.ActivityMapsBinding;
import com.degrau.databinding.ActivitySplashBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.VerMaps.setOnClickListener(view ->
                startActivity(new Intent(this, MapsActivity.class)));
    }

}