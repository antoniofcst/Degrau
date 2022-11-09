package com.degrau;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
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

        String email = binding.editEmailProf.getText().toString().trim();
        String senha = binding.editSenhaProf.getText().toString().trim();
        String areaAtua = binding.editAreaDeAtuaProf.getText().toString().trim();
        String nomeCompleto = binding.editNomeCompletoProf.getText().toString().trim();
        String sobre = binding.editSobreProf.getText().toString().trim();
        String numero = binding.editNumeroProf.getText().toString().trim();


      // binding.btnRegistar.setOnClickListener(view -> validarDados());
       binding.btnRegistar.setOnClickListener(view -> enviarDadosFirestore(
                binding.editEmailProf.getText().toString(),
                binding.editSenhaProf.getText().toString(),
                binding.editAreaDeAtuaProf.getText().toString(),
                binding.editNomeCompletoProf.getText().toString(),
                binding.editSobreProf.getText().toString(),
                binding.editNumeroProf.getText().toString()
       ));

        TextView criarContaCalendly = (TextView) findViewById(R.id.editSobreProf);
        criarContaCalendly.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void enviarDadosFirestore(String email,String senha,String areaAtua,String nomeCompleto,String sobre,String numero) {
        Map<String, Object> cadastroProfessor = new HashMap<>();
        cadastroProfessor.put("areaAtua", areaAtua);
        cadastroProfessor.put("senha", senha);
        cadastroProfessor.put("email", email);
        cadastroProfessor.put("sobre", sobre);
        cadastroProfessor.put("nomeCompleto", nomeCompleto);
               db.collection("cadastroProfessor")
                       .add(cadastroProfessor)
                       .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                           @Override
                           public void onSuccess(DocumentReference documentReference) {
                               Log.d(TAG, "Sucesso " + documentReference.getId());
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Log.w(TAG, "Error adding document", e);
                           }
                       });
    }

    private void validarDados(){
        String email = binding.editEmailProf.getText().toString().trim();
        String senha = binding.editSenhaProf.getText().toString().trim();
        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                criarContaFirebase(email, senha);
            }else{
                Toast.makeText(this,"Informe uma senha", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }
    }

    private void criarContaFirebase(String email,String senha){
            mAuth.createUserWithEmailAndPassword(
                    email, senha
            ).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(this, SplashActivity.class));
                }else{
                    Toast.makeText(this,"Ocorreu um erro", Toast.LENGTH_SHORT).show();
                }
            });
    }
}