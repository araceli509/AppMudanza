package com.example.appmudanzas.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.List;

public class RecyclerViewDarDeAlta extends RecyclerView.Adapter<RecyclerViewDarDeAlta.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoEstantes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoEstantes=(ImageView)itemView.findViewById(R.id.imgEstantes);
        }
    }

    public List<Modelo_Dar_De_Alta>EstantesLista;

    public RecyclerViewDarDeAlta(List<Modelo_Dar_De_Alta> EstantesLista) {
        this.EstantesLista = EstantesLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dar_de_alta,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.fotoEstantes.setImageResource(EstantesLista.get(position).getImgEstantes());

    }

    @Override
    public int getItemCount() {
        return EstantesLista.size();
    }
}
