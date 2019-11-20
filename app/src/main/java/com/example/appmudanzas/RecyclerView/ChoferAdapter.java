package com.example.appmudanzas.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        holder.textViewChofer.setText("Nombre: " + cliente.getNombre());

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
        TextView textViewChofer;

        public PosicionChoferViewHolder(View itemView) {
            super(itemView);
            textViewChofer = itemView.findViewById(R.id.textview_clientes);
        }
    }
}