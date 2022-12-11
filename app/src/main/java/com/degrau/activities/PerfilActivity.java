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
        binding.icFeed.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EditarPerfilActivity.class)));
        binding.imgHome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        binding.imgMap.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MapsActivity.class)));
        binding.imgBuscar.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EncontrarMentoresActivity.class)));
    }
}