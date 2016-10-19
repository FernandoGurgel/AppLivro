package com.ifam.aulalivro;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by fernando on 10/10/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<Livro> lista;
    private final Context context;
    private AdapterListener listener;

    public AdapterListener getListener() {
        return listener;
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    public MyAdapter(Context context, ArrayList<Livro> list) {
        this.lista = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.livro_lista,null);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Livro livro = lista.get(position);

        holder.setAno(livro.getAno())
                .setAutor(livro.getAutor())
                .setTitulo(livro.getTitulo())
                .setAno(livro.getAno())
                .setPagina(livro.getPaginas())
                .setCapa(livro.getCapa());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titulo,autor,ano,pagina;
        private ImageView capa;

        public ViewHolder(View itemView) {
            super(itemView);

            titulo = (TextView) itemView.findViewById(R.id.titulo);
            autor = (TextView) itemView.findViewById(R.id.autor);
            ano = (TextView) itemView.findViewById(R.id.ano);
            pagina = (TextView) itemView.findViewById(R.id.pagina);
            capa = (ImageView) itemView.findViewById(R.id.capaLivro);

            itemView.setOnClickListener(this);
        }

        public ViewHolder setCapa(String cap){
            if (capa == null) return this;
            //Picasso.with(context).load(R.mipmap.ic_launcher).resize(200,200).centerCrop().into(capa);
            Ocean.glide(context).load(cap).build(GlideRequest.BITMAP).resize(200,200).into(capa);
            return this;
        }

        public ViewHolder setPagina(int pag) {
            if(pagina == null) return this;
            this.pagina.setText(String.valueOf(pag));
            return this;
        }

        public ViewHolder setAno(int an) {
            if(ano == null) return this;
            this.ano.setText(String.valueOf(an));
            return this;
        }

        public ViewHolder setAutor(String aut) {
            if(autor== null) return this;
            this.autor.setText(String.valueOf(aut));
            return this;
        }

        public ViewHolder setTitulo(String tit) {
            if(titulo == null) return this;
            this.titulo.setText(String.valueOf(tit));
            return this;
        }

        @Override
        public void onClick(View view) {
            int position = getPosition();
            if(listener != null){
                listener.onItemClick(view,position);
            }
        }
    }

    interface AdapterListener{
        void onItemClick(View view, int posison);
    }
}
