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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_solicitud, parent, false);
        v.setOnClickListener(this);
        SolicitudAdapter.PosicionSolicitudViewHolder holder = new SolicitudAdapter.PosicionSolicitudViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudAdapter.PosicionSolicitudViewHolder holder, int position) {
        SolicitudPojo reservacion = solicitud.get(position);
        holder.fecha_hora.setText("Fecha: " + reservacion.getFecha_hora());
        holder.origen.setText("Origen: " + reservacion.getOrigen());
        holder.destino.setText("Destino: " + reservacion.getDestino());
        holder.monto.setText("Monto: " + reservacion.getMonto());
        if(reservacion.getStatus().equals("2")){
            holder.estado.setText("Estado: Aceptada");
        }else{
            holder.estado.setText("Estado: Pendiente");
        }
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

        TextView fecha_hora;
        TextView origen;
        TextView destino;
        TextView monto;
        TextView estado;
        public PosicionSolicitudViewHolder(View itemView) {
            super(itemView);
            fecha_hora = itemView.findViewById(R.id.fecha_hora);
            origen = itemView.findViewById(R.id.origen);
            destino = itemView.findViewById(R.id.destino);
            monto = itemView.findViewById(R.id.monto);
            estado = itemView.findViewById(R.id.estado);

        }
    }
}
