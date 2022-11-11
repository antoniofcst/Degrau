package com.degrau.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;
import com.degrau.adapters.UserAdapter;
import com.degrau.databinding.ActivityMainBinding;
import com.degrau.maps.MapsActivity;
import com.degrau.models.User;
import com.degrau.utilities.Constrants;
import com.degrau.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;
    private List<User> users;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
        TextView textTitle = binding.textTitle;
        textTitle.setText(String.format(
                "%s",
                preferenceManager.getString(Constrants.KEY_NOMECOMPLETO)
        ));
        binding.VerMaps.setOnClickListener(view -> startActivity(new Intent(this, MapsActivity.class)));
        binding.textSignOut.setOnClickListener(view -> {
            signOut();
        });

        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                sendFCMTokenToFirebase(task.getResult().getToken());
            }
        });

        RecyclerView userRecyclerview = binding.userRecyclerview;

        users = new ArrayList<>();
        userAdapter = new UserAdapter(users);
        userRecyclerview.setAdapter(userAdapter);
        getUsers();
    }

    public void getUsers(){
       binding.userProgressBar.setVisibility(View.VISIBLE);
       FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constrants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    binding.userProgressBar.setVisibility(View.GONE);
                    String myUserid = preferenceManager.getString(Constrants.KEY_USER_ID);
                    if(task.isSuccessful() && task.getResult() != null){
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            if(myUserid.equals(documentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.nomeCompleto = documentSnapshot.getString(Constrants.KEY_NOMECOMPLETO);
                            user.email = documentSnapshot.getString(Constrants.KEY_EMAIL);
                            user.token = documentSnapshot.getString(Constrants.KEY_FCM_TOKEN);
                            users.add(user);
                        }
                        if(users.size() > 0 ){
                            userAdapter.notifyDataSetChanged();
                        }else {
                            binding.textErrorMessage.setText(String.format(String.format("%s", "Nenhum Usuario Disponivel")));
                            binding.textErrorMessage.setVisibility(View.VISIBLE);
                        }
                    }else{
                        binding.textErrorMessage.setText(String.format(String.format("%s", "Nenhum Usuario Disponivel")));
                        binding.textErrorMessage.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void sendFCMTokenToFirebase(String token){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
        db.collection(Constrants.KEY_COLLECTION_USERS).document(
                preferenceManager.getString(Constrants.KEY_USER_ID)
        );
        documentReference.update(Constrants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e ->{
                    Toast.makeText(getApplicationContext(),"Não foi possivel enviar o token" + e.getMessage(),Toast.LENGTH_SHORT).show();
                });
    }
    private void signOut(){
        Toast.makeText(getApplicationContext(),"Sign Out" ,Toast.LENGTH_SHORT).show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                db.collection(Constrants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constrants.KEY_USER_ID)
                );
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constrants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnCompleteListener(aVoid->{
                    preferenceManager.clearPreferences();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();})
                .addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(),"Não foi possivel deslogar",Toast.LENGTH_SHORT).show();
        });
    }
}