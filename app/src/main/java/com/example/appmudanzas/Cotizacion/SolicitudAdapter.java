package com.example.appmudanzas.Cotizacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.List;

public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.PosicionSolicitudViewHolder> implements View.OnClickListener {
    List<SolicitudPojo> solicitud;
    private View.OnClickListener listener;

    public SolicitudAdapter(List<SolicitudPojo> choferlist) {
        this.solicitud = choferlist;
    }

    @NonNull
    @Override
    public SolicitudAdapter.PosicionSolicitudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_chofer, parent, false);
        v.setOnClickListener(this);
        SolicitudAdapter.PosicionSolicitudViewHolder holder = new SolicitudAdapter.PosicionSolicitudViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudAdapter.PosicionSolicitudViewHolder holder, int position) {
        SolicitudPojo reservacion = solicitud.get(position);
        //holder.txtnombre.setText("Nombre: " + cliente.getNombre());
        //holder.txtcapacidad.setText("Capacidad de carga: " + cliente.isCapacidad_carga());
        //holder.txtprecio.setText("Precio por km: " + cliente.getPrecio());
        //holder.ranking.setRating(cliente.getValoracion());
    }


    @Override
    public int getItemCount() {
        return solicitud.size();
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
    public static class PosicionSolicitudViewHolder extends RecyclerView.ViewHolder {
        TextView txtnombre;
        TextView txtcapacidad;
        TextView txtprecio;
        AppCompatRatingBar ranking;

        public PosicionSolicitudViewHolder(View itemView) {
            super(itemView);
            txtnombre = itemView.findViewById(R.id.textview_choferes);
            txtcapacidad = itemView.findViewById(R.id.tonelada);
            txtprecio = itemView.findViewById(R.id.pesoskm);
            ranking = itemView.findViewById(R.id.ranking);

        }
    }
}
