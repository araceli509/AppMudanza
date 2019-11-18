package com.example.appmudanzas.prestador_Servicio.solicitudes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.appmudanzas.R;
import com.example.appmudanzas.mData.reservacion;
import com.example.appmudanzas.prestador_Servicio.solicitudes.*;

import java.util.ArrayList;

public class solicitudAdapter extends RecyclerView.Adapter<solicitudAdapter.solicitudHolder> {

   Context c;

   ArrayList<reservacion> reservaciones;

    public solicitudAdapter(ArrayList<reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }

    @NonNull
    @Override
    public solicitudHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(c);

        View solicitudView = inflater.inflate(R.layout.solicitud_item, parent, false);

        // Return a new holder instance
        return new solicitudHolder(solicitudView);

    }

    @Override
    public void onBindViewHolder(@NonNull solicitudHolder holder, int position) {
            reservacion solicitudes= reservaciones.get(position);

            holder.distancia.setText("0");
            holder.fecha.setText(solicitudes.getFecha().toString());
            holder.nombrecliente.setText(solicitudes.getCliente().getNombre()+" "+solicitudes.getCliente().getApellidos());
            holder.monto.setText(String.valueOf(solicitudes.getMonto()));


    }

    @Override
    public int getItemCount() {
       return reservaciones.size();
    }


    public class solicitudHolder extends ViewHolder {


        TextView nombrecliente,distancia,monto,fecha;
        ImageView rutapreview;

        public solicitudHolder(@NonNull View itemView) {
            super(itemView);

            nombrecliente = itemView.findViewById(R.id.nombrecliente);
            distancia= itemView.findViewById(R.id.distancia);
            monto= itemView.findViewById(R.id.distancia);
            fecha= itemView.findViewById(R.id.fecha);
            rutapreview= itemView.findViewById(R.id.rutapreview);



        }
    }
}
