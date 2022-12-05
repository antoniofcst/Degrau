package com.degrau.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.degrau.R;
import com.degrau.models.Postagem;
import com.degrau.models.User;


public class PostarActivity extends AppCompatActivity {

    private ImageView imageFotoEscolhida;
    private Bitmap imagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postar);

        imageFotoEscolhida = findViewById(R.id.imageFotoEscolhida);



        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            byte[] dadosImagem  =  bundle.getByteArray("FotoEscolhida");
             imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
             imageFotoEscolhida.setImageBitmap(imagem);

        }
    }

    private void publicarPostagem(User user) {

        if (user.token == null || user.token.trim().isEmpty()) {
            Toast.makeText(this,user.nomeCompleto + " " +"Usuário não disponivel ",Toast.LENGTH_SHORT).show();
        } else {

        }
        Postagem postagem = new Postagem();

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

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}