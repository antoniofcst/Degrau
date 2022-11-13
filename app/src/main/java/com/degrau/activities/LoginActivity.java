package com.degrau.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.degrau.databinding.ActivityLoginBinding;
import com.degrau.utilities.Constrants;
import com.degrau.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding bindingLogin;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingLogin = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bindingLogin.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Constrants.KEY_ESTA_LOGADO)){
            startActivity(new Intent(this, MainActivity.class));
        }
        bindingLogin.naotenhoConta.setOnClickListener(view ->
                startActivity(new Intent(this, CadastroActivity.class)));
        bindingLogin.btnCriarConta.setOnClickListener(view ->
                startActivity(new Intent(this, CadastroActivity.class)));
        bindingLogin.esqueceuSenha.setOnClickListener(view -> startActivity(new Intent(this, RecuperarContaActivity.class)));

        bindingLogin.btnEntrar.setOnClickListener(view -> {
            if(bindingLogin.editLoginEmail.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Insira um email", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(bindingLogin.editLoginEmail.getText().toString().trim()).matches()){
                Toast.makeText(getApplicationContext(), "Insira um email válido", Toast.LENGTH_SHORT).show();
            } else if(bindingLogin.editSenhaLogin.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Insira uma senha", Toast.LENGTH_SHORT).show();
            }else {
                login();
            }
        });

    }
    public void login(){
        bindingLogin.btnEntrar.setVisibility(View.INVISIBLE);
        bindingLogin.progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constrants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constrants.KEY_EMAIL, bindingLogin.editLoginEmail.getText().toString())
                .whereEqualTo(Constrants.KEY_SENHA, bindingLogin.editSenhaLogin.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() !=null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constrants.KEY_ESTA_LOGADO,true);
                        preferenceManager.putString(Constrants.KEY_USER_ID,documentSnapshot.getId());
                        preferenceManager.putString(Constrants.KEY_NOMECOMPLETO,documentSnapshot.getString(Constrants.KEY_NOMECOMPLETO));
                        preferenceManager.putString(Constrants.KEY_EMAIL,documentSnapshot.getString(Constrants.KEY_EMAIL));
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        bindingLogin.progressBar.setVisibility(View.INVISIBLE);
                        bindingLogin.btnEntrar.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Senha ou Usuario incorretos" ,Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
