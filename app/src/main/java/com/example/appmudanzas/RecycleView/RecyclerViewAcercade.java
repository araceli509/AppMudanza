package com.example.appmudanzas.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.List;

public class RecyclerViewAcercade extends RecyclerView.Adapter<RecyclerViewAcercade.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoMesas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoMesas=(ImageView)itemView.findViewById(R.id.imgMesas);
        }
    }

    public List<ModeloAcercade>mesasLista;

    public RecyclerViewAcercade(List<ModeloAcercade> mesasLista) {
        this.mesasLista = mesasLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cercade,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.fotoMesas.setImageResource(mesasLista.get(position).getImgMesas());

    }

    @Override
    public int getItemCount() {
        return mesasLista.size();
    }
}
