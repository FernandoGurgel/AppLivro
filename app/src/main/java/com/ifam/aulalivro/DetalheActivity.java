package com.ifam.aulalivro;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;

public class DetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        recuperaDados();
    }

    private void recuperaDados() {
//        String titulo = getIntent().getStringExtra("titulo");
//        Log.d("infor",titulo);

        Livro livro = (Livro) getIntent().getSerializableExtra("livro");

        TextView titulo = (TextView) findViewById(R.id.titulo_descricao);
        TextView ano = (TextView) findViewById(R.id.ano_descricao);
        TextView autor = (TextView) findViewById(R.id.autor_descricao);
        TextView pagina = (TextView) findViewById(R.id.pagina_descricao);
        ImageView capa = (ImageView) findViewById(R.id.imagem_descricao);

        titulo.setText(livro.getTitulo());
        ano.setText(livro.getAno()+" ");
        autor.setText(livro.getAutor());
        pagina.setText(livro.getPaginas()+", ");

        Ocean.glide(this)
                .load(livro.getCapa())
                .build(GlideRequest.BITMAP)
                .into(capa);
    }
}
