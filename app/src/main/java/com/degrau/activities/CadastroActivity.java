package com.degrau.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.degrau.databinding.ActivityCadastroBinding;
import com.degrau.utilities.Constrants;
import com.degrau.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;


public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        binding.imageBack.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));

        binding.btnRegistar.setOnClickListener(view -> {
            if (binding.editEmailCadastro.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Insira o email", Toast.LENGTH_SHORT).show();
            } else if (binding.editSenhaCadastro.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Insira a senha", Toast.LENGTH_SHORT).show();
            } else if (binding.editConfirmarSenha.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Confirme a senha", Toast.LENGTH_SHORT).show();
            } else if (binding.editAreaDeAtua.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Insira a Area de Atuação", Toast.LENGTH_SHORT).show();
            } else if (binding.editNomeCompleto.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Insira o Nome Completo", Toast.LENGTH_SHORT).show();
            } else if (binding.editNumero.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Insira o Numero de Telefone", Toast.LENGTH_SHORT).show();
            } else if (binding.editSobre.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Insira os dados Sobre Você", Toast.LENGTH_SHORT).show();
            } else {
                enviarDadosFirestore();
            }
        });

    }

    private void enviarDadosFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        binding.btnRegistar.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        HashMap<String, Object> users = new HashMap<>();
        users.put(Constrants.KEY_AREAT, binding.editAreaDeAtua.getText().toString());
        users.put(Constrants.KEY_SENHA, binding.editSenhaCadastro.getText().toString());
        users.put(Constrants.KEY_EMAIL, binding.editEmailCadastro.getText().toString());
        users.put(Constrants.KEY_SOBRE, binding.editSobre.getText().toString());
        users.put(Constrants.KEY_NOMECOMPLETO, binding.editNomeCompleto.getText().toString());
        users.put(Constrants.KEY_NUMERO, binding.editNumero.getText().toString());

        db.collection(Constrants.KEY_COLLECTION_USERS)
                .add(users)
                .addOnSuccessListener(documentReference -> {
                    preferenceManager.putBoolean(Constrants.KEY_ESTA_LOGADO,true);
                    preferenceManager.putString(Constrants.KEY_NOMECOMPLETO,binding.editNomeCompleto.getText().toString());
                    preferenceManager.putString(Constrants.KEY_USER_ID,documentReference.getId());
                    preferenceManager.putString(Constrants.KEY_NUMERO,binding.editNumero.getText().toString());
                    preferenceManager.putString(Constrants.KEY_SENHA,binding.editSenhaCadastro.getText().toString());
                    preferenceManager.putString(Constrants.KEY_EMAIL,binding.editEmailCadastro.getText().toString());
                    preferenceManager.putString(Constrants.KEY_AREAT,binding.editAreaDeAtua.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.btnRegistar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Não foi possivel realizar o cadastro check os dados" + e.getMessage(),Toast.LENGTH_SHORT).show();
                });

        }
}