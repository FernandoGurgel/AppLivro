package com.ifam.aulalivro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.id.content;
import static android.R.id.list;

public class biblioteca extends AppCompatActivity implements Request.RequestListener, MyAdapter.AdapterListener {

    private Livro livro;
    private ArrayList<Livro> livros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        ArrayList<Livro> lista = new ArrayList<Livro>();

        //for(Livro livro : lista);

        MyAdapter adapter = new MyAdapter(this,lista);


        hideLoad(lista);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Ocean.newRequest("http://gitlab.oceanmanaus.com/snippets/1/raw", this).get().send();

    }

    /*public ArrayList<Livro> iniciarLista(){
        ArrayList<Livro> lista = new ArrayList<>();
        // implementa
        livro = new Livro();
        livro.setTitulo("MOODLE 2 para Autores e Tutores - 3ª Edição");
        livro.setAutor("Robson Santos da Silva");
        livro.setAno(2013);
        livro.setPaginas(168);
        livro.setCapa("http://172.25.1.17/oceanbook/moodle2.jpg");
        lista.add(livro);

        livro = new Livro();
        livro.setTitulo("NoSQL Essencial");
        livro.setAutor("Pramod J. Sadalage / Martin Fowler");
        livro.setAno(2013);
        livro.setPaginas(216);
        livro.setCapa("http://172.25.1.17/oceanbook/NoSQLEssencial.png");
        lista.add(livro);

        livro = new Livro();
        livro.setTitulo("Fundamentos de Bancos de Dados com C#");
        livro.setAutor("Michael Schmalz");
        livro.setAno(2012);
        livro.setPaginas(120);
        livro.setCapa("http://172.25.1.17/oceanbook/BancoDeDadosComC.jpg");
        lista.add(livro);

        livro = new Livro();
        livro.setTitulo("Jovem e Bem-Sucedido");
        livro.setAutor("Juliano Niederauer");
        livro.setAno(2013);
        livro.setPaginas(192);
        livro.setCapa("http://172.25.1.17/oceanbook/JovemeBem-Sucedido.jpg");
        lista.add(livro);

        return lista;
    }*/

    private void hideLoad (ArrayList <Livro> lista){
        if (lista.size() > 0){
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.carregando);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestOk(String s, JSONObject jsonObject, int i) {
        if(i == Request.NENHUM_ERROR){
            ArrayList<Livro> lista = new ArrayList<>();
            if(s != null){
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray ocean = object.getJSONArray("ocean");

                    for(int x = 0; x < ocean.length(); x++){
                        JSONObject item = ocean.getJSONObject(x);
                        JSONArray livros = item.getJSONArray("livros");
                        for(int z =0; z < livros.length(); z++){
                            JSONObject livro = livros.getJSONObject(z);

                            Log.d("Debug",livro.getString("titulo")+","+livro.getString("autor"));

                            ArrayList<String> comet;
                            

                            for (int w = 0; w )
                            Livro livro1 = new Livro(item.getString("categoria"),livro.getString("capa"),livro.getString("titulo"),livro.getString("autor"),
                                    livro.getInt("paginas"),livro.getInt("ano"));

                            lista.add(livro1);
                        }
                    }

                    criaAdapter(lista);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error","Erro no jsond");
                }
            }
        }
    }

    private void criaAdapter(ArrayList<Livro> lista) {

        livros = lista;

        MyAdapter adapter = new MyAdapter(this, lista);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setListener(this);
        hideLoad(lista);
    }

    @Override
    public void onItemClick(View view, int posison) {
        Log.d("infor", posison+" ");
        Livro livro= livros.get(posison);

        Toast.makeText(this,posison + " "+livro.getTitulo(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,DetalheActivity.class);
        intent.putExtra("livro",livro);
        startActivity(intent);
    }
}
