package com.example.appmudanzas.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.List;

 class Adapter extends RecyclerView.Adapter<Adapter.PosicionChoferViewHolder> implements View.OnClickListener {

    List<ChoferPojo> chofer;
    private View.OnClickListener listener;

    public Adapter(List<ChoferPojo> choferlist) {
        this.chofer = choferlist;
    }

    @NonNull
    @Override
    public PosicionChoferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_chofer, parent, false);
        v.setOnClickListener(this);
        PosicionChoferViewHolder holder = new PosicionChoferViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PosicionChoferViewHolder holder, int position) {
        ChoferPojo cliente = chofer.get(position);
        holder.txtnombre.setText("Nombre: " + cliente.getNombre());
        holder.txtcapacidad.setText("Capacidad de carga: " + cliente.isCapacidad_carga());
        holder.txtprecio.setText("Precio por km: " + cliente.getPrecio());
        holder.ranking.setRating(cliente.getValoracion());
}


    @Override
    public int getItemCount() {
        return chofer.size();
    }
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;

    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }
    public static class PosicionChoferViewHolder extends RecyclerView.ViewHolder {
        TextView txtnombre;
        TextView txtcapacidad;
        TextView txtprecio;
        AppCompatRatingBar ranking;

        public PosicionChoferViewHolder(View itemView) {
            super(itemView);
            txtnombre = itemView.findViewById(R.id.textview_choferes);
            txtcapacidad = itemView.findViewById(R.id.tonelada);
            txtprecio = itemView.findViewById(R.id.pesoskm);
            ranking = itemView.findViewById(R.id.ranking);

        }
    }
}