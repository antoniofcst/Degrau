package com.degrau.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.degrau.R;
import com.degrau.activities.maps.MapsActivity;
import com.degrau.databinding.ActivityEncontrarMentoresBinding;
import com.degrau.databinding.EditarPerfilActivityBinding;

public class EditarPerfilActivity extends AppCompatActivity {
    private ActivityEncontrarMentoresBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil_activity);

        binding =  ActivityEncontrarMentoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.icFeed.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EditarPerfilActivity.class)));
        binding.imgHome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        binding.imgMap.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MapsActivity.class)));
        binding.imgBuscar.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EncontrarMentoresActivity.class)));
    }


}