package com.degrau.models;

import com.degrau.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Postagem {
    private String id;
    private String idUsuario;
    private String descricao;
    private String caminhoFoto;

    public Postagem() {
        DatabaseReference fireBaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference postagemRef = fireBaseRef.child("postagens");
        String idPostagem = postagemRef.push().getKey();
        setId(idPostagem);
    }

    public boolean salvar() {
        DatabaseReference fireBaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference postagensRef = fireBaseRef.child("postagens")
                .child(getIdUsuario())
                .child(getId());
        postagensRef.setValue(this);
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }
}
