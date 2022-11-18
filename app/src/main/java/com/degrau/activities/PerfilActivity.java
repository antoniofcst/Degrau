package com.degrau.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.degrau.R;

import com.degrau.activities.maps.MapsActivity;
import com.degrau.databinding.ActivityPerfilFragmentBinding;

public class PerfilActivity extends AppCompatActivity {

    private ActivityPerfilFragmentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonEditarPerfil.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EditarPerfilActivity.class)));
        binding.btnVerMaps.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MapsActivity.class)));
    }
}