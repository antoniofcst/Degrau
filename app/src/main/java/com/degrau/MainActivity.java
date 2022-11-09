package com.degrau;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.degrau.databinding.ActivityMainBinding;
import com.degrau.databinding.ActivityMapsBinding;
import com.degrau.databinding.ActivitySplashBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        binding.VerMaps.setOnClickListener(view ->
                startActivity(new Intent(this, MapsActivity.class)));

        binding.imageButtonBuscarMentor.setOnClickListener(view -> buscarMentoresFirestore(
                binding.editBuscarMentor.getText().toString()
        ));
    }

    void buscarMentoresFirestore(String mentorBuscado) {

        db.collection("cadastroProfessor")
                .whereEqualTo("nomeCompleto", mentorBuscado)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Toast.makeText(getApplicationContext(),"Mentor encontrado",Toast.LENGTH_SHORT).show();
                                binding.editTextTextMultiLine.setText(document.get("nomeCompleto").toString());
                            }
                        }else {
                            binding.editTextTextMultiLine.setText("");
                            //Toast.makeText(getApplicationContext(),"Mentor não cadastrado",Toast.LENGTH_SHORT).show();
                        }
                    }
                });







    }

}