package com.degrau.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.degrau.R;

public class PostarActivity extends AppCompatActivity {
    private ImageView imageFotoEscolhida;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postar);
        imageFotoEscolhida = findViewById(R.id.imageFotoEscolhida);
        //Configura toolbar
       // Toolbar toolbar = findViewById(R.id.toolbarPrincipal);





        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            byte[] dadosImagem  =  bundle.getByteArray("FotoEscolhida");
             imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
             imageFotoEscolhida.setImageBitmap(imagem);

        }
    }
}