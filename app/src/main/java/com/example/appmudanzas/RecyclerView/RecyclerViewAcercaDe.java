package com.example.appmudanzas.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.List;

public class RecyclerViewAcercaDe extends RecyclerView.Adapter<RecyclerViewAcercaDe.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoAcercade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoAcercade=(ImageView)itemView.findViewById(R.id.imgMesas);
        }
    }

    public List<ModeloAcercaDe>AcercadeLista;

    public RecyclerViewAcercaDe(List<ModeloAcercaDe> mesasLista) {
        this.AcercadeLista = mesasLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acercade,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.fotoAcercade.setImageResource(AcercadeLista.get(position).getImgMesas());

    }

    @Override
    public int getItemCount() {
        return AcercadeLista.size();
    }
}
