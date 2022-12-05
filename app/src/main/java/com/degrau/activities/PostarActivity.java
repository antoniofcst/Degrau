package com.degrau.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.degrau.R;
import com.degrau.models.Postagem;
import com.degrau.models.User;

public class PostarActivity extends AppCompatActivity {
    private ImageView imageFotoEscolhida;
    private Bitmap imagem;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postar);
        idUsuarioLogado = User.getToken();
        imageFotoEscolhida = findViewById(R.id.imageFotoEscolhida);



        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            byte[] dadosImagem  =  bundle.getByteArray("FotoEscolhida");
             imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
             imageFotoEscolhida.setImageBitmap(imagem);

        }
    }

    private void publicarPostagem() {
        Postagem postagem = new Postagem();
        postagem.setIdUsuario();
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