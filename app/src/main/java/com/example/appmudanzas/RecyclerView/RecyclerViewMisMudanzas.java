package com.example.appmudanzas.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.List;

public class RecyclerViewMisMudanzas extends RecyclerView.Adapter<RecyclerViewMisMudanzas.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoCamas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoCamas=(ImageView)itemView.findViewById(R.id.imgCamas);
        }
    }

    public List<ModeloMisMudanzas>camasLista;

    public RecyclerViewMisMudanzas(List<ModeloMisMudanzas> camasLista) {
        this.camasLista = camasLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mismudanzas,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.fotoCamas.setImageResource(camasLista.get(position).getImgCamas());

    }

    @Override
    public int getItemCount() {
        return camasLista.size();
    }
}
