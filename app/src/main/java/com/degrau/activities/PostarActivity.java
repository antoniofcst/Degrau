package com.degrau.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.degrau.R;
import com.degrau.helper.ConfiguracaoFirebase;
import com.degrau.models.Postagem;
import com.degrau.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import io.grpc.Context;


public class PostarActivity extends AppCompatActivity {

    private ImageView imageFotoEscolhida;
    private Bitmap imagem;
    private User user;
    private String idUsuarioLogado;
    private TextInputEditText textDescricaoFiltro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postar);
        idUsuarioLogado = user.getToken();
        imageFotoEscolhida = findViewById(R.id.imageFotoEscolhida);
        textDescricaoFiltro = findViewById(R.id.textDescricaoFiltro);



        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            byte[] dadosImagem  =  bundle.getByteArray("FotoEscolhida");
             imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
             imageFotoEscolhida.setImageBitmap(imagem);

        }
    }

    private void publicarPostagem() {
        Postagem postagem = new Postagem();
        postagem.setIdUsuario(idUsuarioLogado);
        postagem.setDescricao(textDescricaoFiltro.getText().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] dadosImagem = baos.toByteArray();
        StorageReference storageRef = ConfiguracaoFirebase.getFirebaseStorage();
        StorageReference imagemRef = storageRef
                .child("imagens")
                .child("postagens")
                .child(postagem.getId() + ".jpeg");

        UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostarActivity.this,
                        "Erro ao salvar imagem",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //Recuperar local da foto
               imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                   @Override
                   public void onComplete(@NonNull Task<Uri> task) {
                       Uri url = task.getResult();
                       postagem.setCaminhoFoto(url.toString());
                       if(postagem.salvar()) {
                           // bicho eu preciso passar como parametro no salvar a lista do usuario do app para mostrar no feed deles todas as fotos do banco
                           Toast.makeText(PostarActivity.this,
                                   "Sucesso ao salvar imagem",
                                   Toast.LENGTH_SHORT).show();
                           finish();
                       }
                   }

               });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_postagem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_salvar_postagem:
                publicarPostagem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}