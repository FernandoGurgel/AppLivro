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
import java.util.ArrayList;


/**
 * Created by fernando on 10/10/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{

    private final int layout_view;
    private ArrayList<String> nomes;
    private ArrayList<String> mensagens;
    private ArrayList<String> capas;
    private ArrayList<Livro> lista;
    private final Context context;
    private AdapterListener listener;

    public static final int BIBLIOTECA = 0;
    public static final int COMENTARIOS = 1;
    public static final int RECOMENDACOES = 2;

    public MyAdapter(Context context, ArrayList<Livro> list, int layout_view) {
        this.lista = list;
        this.context = context;
        this.layout_view = layout_view;
    }

    public MyAdapter(ArrayList<String> nome,ArrayList<String> mensagens,ArrayList<String> capa, Context context, int layout_view){
        this.context = context;
        this.layout_view = layout_view;
        this.nomes = nome;
        this.mensagens = mensagens;
        this.capas = capa;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public AdapterListener getListener() {
        return listener;
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        int layout = 0;
        switch (layout_view) {
            case BIBLIOTECA: {
                layout = R.layout.livro_lista;
                break;
            }
            case COMENTARIOS: {
                layout = R.layout.lista_comentario;
                break;
            }
            case RECOMENDACOES: {
                layout = R.layout.card_livro_relacionado;
                break;
            }
        }
        view = LayoutInflater.from(parent.getContext()).inflate(layout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (layout_view){
            case BIBLIOTECA:{
                Livro livro = lista.get(position);
                holder.setAno(livro.getAno())
                        .setAutor(livro.getAutor())
                        .setTitulo(livro.getTitulo())
                        .setAno(livro.getAno())
                        .setPagina(livro.getPaginas())
                        .setCapa(livro.getCapa());

                break;
            }
            case RECOMENDACOES:{
                String capa = capas.get(position);
                holder.setCapaRecomendacoes(capa);
                break;
            }
            default: {
                String nome = nomes.get(position);
                String msg = mensagens.get(position);


                holder.setNomeComentario(nome)
                        .setMensagemComentario(msg);
                break;
            }
        }
    }


    @Override
    public int getItemCount() {
        switch (layout_view){
            case BIBLIOTECA:{
                return lista.size();
            }
            case RECOMENDACOES:{
                return capas.size();
            }
            default:{
                return nomes.size();
            }

        }



    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView titulo,autor,ano,pagina,nome_comentario, mensagem_comentario;
        private ImageView capa,capa_recomendacoes;


        public ViewHolder(View itemView) {
            super(itemView);

            switch (layout_view){
                case BIBLIOTECA :{
                    titulo = (TextView) itemView.findViewById(R.id.titulo);
                    autor = (TextView) itemView.findViewById(R.id.autor);
                    ano = (TextView) itemView.findViewById(R.id.ano);
                    pagina = (TextView) itemView.findViewById(R.id.pagina);
                    capa = (ImageView) itemView.findViewById(R.id.capaLivro);
                    break;
                }
                case RECOMENDACOES:{
                    capa_recomendacoes = (ImageView) itemView.findViewById(R.id.imagem_card_livro_relacionado);
                    break;
                }
                default:{
                    nome_comentario = (TextView) itemView.findViewById(R.id.nome_comentario);
                    mensagem_comentario = (TextView) itemView.findViewById(R.id.mensagem_comentario);
                }
            }
            itemView.setOnClickListener(this);
        }

        public ViewHolder setMensagemComentario(String msg){
            if (mensagem_comentario == null)return this;
            this.mensagem_comentario.setText(String.valueOf(msg));
            return this;
        }

        public ViewHolder setNomeComentario(String nome){
            if(nome_comentario == null) return this;
            this.nome_comentario.setText(String.valueOf(nome));
            return this;
        }

        public ViewHolder setCapaRecomendacoes(String cap){
            if (capa_recomendacoes == null) return this;
            Ocean.glide(context).load(cap).build(GlideRequest.BITMAP).resize(150,200).into(capa_recomendacoes);
            return this;
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
