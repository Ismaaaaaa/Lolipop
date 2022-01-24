package com.ismael.lolipop20.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismael.lolipop20.R;
import com.ismael.lolipop20.entidades.Anuncio;

import java.util.ArrayList;

public class Adaptador_RecylerView extends RecyclerView.Adapter {

    private ArrayList<Anuncio> anuncios;

    public Adaptador_RecylerView(ArrayList<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    //Devuelve un objeto ViewHolder que se  empleará para mostrar  el contenido de la lista.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Necesitamos convertir el layout a un View. Para ello se usa la clase LayoutInflater
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card_view_layout, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder_RecyclerView_CardView(view);
        return viewHolder;
    }

    //Recibe el ViewHolder del método anterior
    //Asocia a cada componente gráfico de dicho View, el dato que queremos visualizar
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder_RecyclerView_CardView viewHolder_Anuncios = (ViewHolder_RecyclerView_CardView) holder;
        Bitmap bitmap = BitmapFactory.decodeByteArray(anuncios.get(position).getImagen(), 0, anuncios.get(position).getImagen().length);
        if(bitmap != null){
            viewHolder_Anuncios.iv_imagen_portada.setImageBitmap(bitmap);
        }
        viewHolder_Anuncios.tv_titulo_anuncio.setText(anuncios.get(position).getTitulo());
        viewHolder_Anuncios.tv_precio_anuncio.setText(anuncios.get(position).getPrecio() + " €");

        if(anuncios.get(position).isExterno()){
            viewHolder_Anuncios.tv_anuncio_enun.setVisibility(View.VISIBLE);
            viewHolder_Anuncios.tv_anuncio.setText(anuncios.get(position).getLink());
        } else {
            viewHolder_Anuncios.tv_anuncio_enun.setVisibility(View.INVISIBLE);
            viewHolder_Anuncios.tv_anuncio.setText(null);
        }
    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public ArrayList<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(ArrayList<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

}

class ViewHolder_RecyclerView_CardView extends RecyclerView.ViewHolder {

    public ImageView iv_imagen_portada;
    public TextView tv_titulo_anuncio;
    public TextView tv_precio_anuncio;
    public  TextView tv_anuncio;
    public  TextView tv_anuncio_enun;

    public ViewHolder_RecyclerView_CardView(@NonNull View itemView) {
        super(itemView);

        this.iv_imagen_portada = itemView.findViewById(R.id.imagenCV);
        this.tv_titulo_anuncio = itemView.findViewById(R.id.tituloAnuncioCV);
        this.tv_precio_anuncio = itemView.findViewById(R.id.precioAnuncioCV);
        this.tv_anuncio = itemView.findViewById(R.id.textViewAD);
        this.tv_anuncio_enun = itemView.findViewById(R.id.textViewADEnun);

    }
}
