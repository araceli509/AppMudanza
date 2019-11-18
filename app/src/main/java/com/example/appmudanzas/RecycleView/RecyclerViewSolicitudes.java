package com.example.appmudanzas.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.List;

public class RecyclerViewSolicitudes extends RecyclerView.Adapter<RecyclerViewSolicitudes.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoColgadores;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoColgadores=(ImageView)itemView.findViewById(R.id.imgColgadores);
        }
    }

    public List<ModeloSolicitudes>ColgadoresLista;

    public RecyclerViewSolicitudes(List<ModeloSolicitudes> ColgadoresLista) {
        this.ColgadoresLista = ColgadoresLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitudes,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.fotoColgadores.setImageResource(ColgadoresLista.get(position).getImgColgadores());

    }

    @Override
    public int getItemCount() {
        return ColgadoresLista.size();
    }
}
