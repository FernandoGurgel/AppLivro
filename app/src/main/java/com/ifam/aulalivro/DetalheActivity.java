package com.ifam.aulalivro;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;

import java.util.ArrayList;

public class DetalheActivity extends AppCompatActivity {

    private Livro livro;
    ArrayList<String> nome;
    ArrayList<String> mens;
    ArrayList<String> capas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        capas = getIntent().getStringArrayListExtra("recomendacoes");
        livro = (Livro) getIntent().getSerializableExtra("livro");
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


        carregandoListas(livro);
    }

    private void carregandoListas(Livro livro) {

        nome = new ArrayList<String>();
        mens = new ArrayList<String>();

        for(int i =0; i < livro.getNumComentario(); i++){
            nome.add(livro.getUsuario(i));
            mens.add(livro.getMensagem(i));
        }

        Log.d("fer",capas+" ");

        criaAdpter(nome,mens,capas);

    }

    private void criaAdpter(ArrayList<String> nome, ArrayList<String> mens, ArrayList<String> capas) {

        MyAdapter adapter = new MyAdapter(nome,mens,capas,this,MyAdapter.COMENTARIOS);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lista_comentario);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter adapter1 = new MyAdapter(nome,mens,capas,this,MyAdapter.RECOMENDACOES);
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.livro_relacionado);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }


}
