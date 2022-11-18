package com.degrau.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.degrau.activities.maps.MapsActivity;
import com.degrau.databinding.ActivityEncontrarMentoresBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class EncontrarMentoresActivity extends AppCompatActivity {

    private ActivityEncontrarMentoresBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEncontrarMentoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.btnBuscarMentor.setOnClickListener(view -> buscarMentoresFirestore(
                binding.editBuscarMentor.getText().toString()

        ));
        binding.imageMaps.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MapsActivity.class)));
        binding.imgHome.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        binding.imgPerfil.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PerfilFragment.class)));
        binding.imgBuscar.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EncontrarMentoresActivity.class)));

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
                               // binding.editTextTextMultiLine.setText(document.get("nomeCompleto").toString());
                            }
                        }else {
                           // binding.editTextTextMultiLine.setText("");
                            //Toast.makeText(getApplicationContext(),"Mentor não cadastrado",Toast.LENGTH_SHORT).show();
                        }
                    }
                });







    }

}