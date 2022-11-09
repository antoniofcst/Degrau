package com.degrau;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.degrau.databinding.ActivityCadastroProfessorBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivityProfessor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ActivityCadastroProfessorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityCadastroProfessorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
       binding.btnRegistar.setOnClickListener(view -> enviarDadosFirestore(

                binding.editEmailProf.getText().toString(),
                binding.editSenhaProf.getText().toString(),
                binding.editAreaDeAtuaProf.getText().toString(),
                binding.editNomeCompletoProf.getText().toString(),
                binding.editSobreProf.getText().toString(),
                binding.editNumeroProf.getText().toString()
        ));
    }

    private void enviarDadosFirestore(String email,String senha,String areaAtua,String nomeCompleto,String sobre,String numero) {

        Map<String, Object> cadastroProfessor = new HashMap<>();
        cadastroProfessor.put("areaAtua", areaAtua);
        cadastroProfessor.put("senha", senha);
        cadastroProfessor.put("email", email);
        cadastroProfessor.put("sobre", sobre);
        cadastroProfessor.put("nomeCompleto", nomeCompleto);
        cadastroProfessor.put("numero", numero);

        db.collection("cadastroProfessor")
                .add(cadastroProfessor)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Mentor Cadastrado com Sucesso",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}