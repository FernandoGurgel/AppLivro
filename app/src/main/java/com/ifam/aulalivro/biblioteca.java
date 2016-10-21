package com.ifam.aulalivro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.http.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.ifam.aulalivro.MyAdapter.BIBLIOTECA;

public class biblioteca extends AppCompatActivity implements Request.RequestListener, MyAdapter.AdapterListener {

    private Livro livro;
    private ArrayList<Livro> livros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        ArrayList<Livro> lista = new ArrayList<Livro>();

        MyAdapter adapter = new MyAdapter(this,lista,BIBLIOTECA);


        hideLoad(lista);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Ocean.newRequest("https://gist.githubusercontent.com/FernandoGurgel/f12e954b2af7a40f1e81d9b8f57d192d/raw/19144642ec0b9d90b3b3bef0251cab5ee1f9426f/livroDigital", this).get().send();

    }


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

                            ArrayList<String> nome = new ArrayList<String>();
                            ArrayList<String> msg = new ArrayList<String>();
                            JSONArray comentarios =  livro.getJSONArray("comentario");

                            for (int w = 0; w < comentarios.length(); w++){
                                JSONObject comentario = comentarios.getJSONObject(w);
                                nome.add(comentario.getString("usuario"));
                                msg.add(comentario.getString("mensagem"));
                            }

                            Log.d("fer",nome+" ");

                            Livro livro1 = new Livro(item.getString("categoria"),livro.getString("capa"),livro.getString("titulo"),livro.getString("autor"),
                                    livro.getInt("paginas"),livro.getInt("ano"),nome,msg);

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

        MyAdapter adapter = new MyAdapter(this, lista,BIBLIOTECA);

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

        ArrayList<String> relacionado = new ArrayList<String>();

        for(Livro livro1:livros){
            if(livro1.getCategoria().equals(livro.getCategoria()) && livro.getTitulo() != livro1.getTitulo())
            relacionado.add(livro1.getCapa());
        }

        Log.d("fer",relacionado.size()+"");

        Intent intent = new Intent(this,DetalheActivity.class);
        intent.putExtra("livro",livro);
        intent.putStringArrayListExtra("recomendacoes",relacionado);
        startActivity(intent);
    }
}
