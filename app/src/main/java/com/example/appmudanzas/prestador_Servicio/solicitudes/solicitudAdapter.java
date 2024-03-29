package com.example.appmudanzas.prestador_Servicio.solicitudes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;


import java.util.ArrayList;


public class solicitudAdapter extends RecyclerView.Adapter<solicitudAdapter.solicitudHolder>{

   Context c;

   ArrayList<reservacion> reservaciones;

    private OnItemClickListener listener;

    public solicitudAdapter(ArrayList<reservacion> reservaciones ,OnItemClickListener listener) {
        this.reservaciones = reservaciones;
        this.listener=listener;
    }

    @NonNull
    @Override
    public solicitudHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(c);
        View solicitudView = inflater.inflate(R.layout.solicitud_item, parent, false);
        return new solicitudHolder(solicitudView);

    }

    @Override
    public void onBindViewHolder(@NonNull solicitudHolder holder, int position) {
            reservacion solicitudes= reservaciones.get(position);
            holder.distancia.setText(solicitudes.getDistancia()+" KM");
            holder.fecha.setText(solicitudes.getFecha().toString());
            holder.nombrecliente.setText(solicitudes.getCliente().getNombre()+" "+solicitudes.getCliente().getApellidos());
            holder.monto.setText(solicitudes.getMonto()+" MXN");




    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener


    @Override
    public int getItemCount() {
       return reservaciones.size();
    }


    public class solicitudHolder extends RecyclerView.ViewHolder{


        TextView nombrecliente,distancia,monto,fecha;
        ImageView rutapreview;

        public solicitudHolder(@NonNull final View itemView) {
            super(itemView);

            nombrecliente =(TextView) itemView.findViewById(R.id.nombrecliente);
            distancia=(TextView) itemView.findViewById(R.id.distancia);
            monto=(TextView) itemView.findViewById(R.id.monto);
            fecha= (TextView)itemView.findViewById(R.id.fecha);
            rutapreview= (ImageView) itemView.findViewById(R.id.rutapreview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });


        }
    }
}
