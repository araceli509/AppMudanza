package com.example.appmudanzas.prestador_Servicio.mudanza;

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

public class mudanzaAdapter extends RecyclerView.Adapter<mudanzaAdapter.mudanzaHolder>{

        Context c;

        ArrayList<Mudanza> mudanzas;

        private com.example.appmudanzas.prestador_Servicio.solicitudes.solicitudAdapter.OnItemClickListener listener;

        public mudanzaAdapter(ArrayList<Mudanza> mudanzas , com.example.appmudanzas.prestador_Servicio.solicitudes.solicitudAdapter.OnItemClickListener listener) {
            this.mudanzas = mudanzas;
            this.listener=listener;
        }

        @NonNull
        @Override
        public mudanzaAdapter.mudanzaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            c= parent.getContext();
            LayoutInflater inflater= LayoutInflater.from(c);
            View solicitudView = inflater.inflate(R.layout.mudanzaitem, parent, false);
            return new mudanzaAdapter.mudanzaHolder(solicitudView);

        }


    @Override
        public void onBindViewHolder(@NonNull mudanzaAdapter.mudanzaHolder holder, int position) {
            Mudanza mudanza= mudanzas.get(position);
            if(mudanza!=null) {

               holder.distancia.setText(mudanza.getDistancia()+"KM");
                holder.fecha.setText(mudanza.getFecha());
                holder.nombrecliente.setText( mudanza.getCliente().getNombre() + " " + mudanza.getCliente().getApellidos());
                holder.hora.setText(mudanza.getHora());
                if(mudanza.getStatus()==1){

                    holder.carrito.setImageResource(R.drawable.cadespera);
                }

                if(mudanza.getStatus()==3){

                    holder.carrito.setImageResource(R.drawable.vehicle);
                }
            }
        }

        public interface OnItemClickListener {
            void onItemClick(View itemView, int position);
        }
        // Define the method that allows the parent activity or fragment to define the listener


        @Override
        public int getItemCount() {
            return mudanzas.size();
        }


        public class mudanzaHolder extends RecyclerView.ViewHolder{


            TextView nombrecliente,distancia,fecha,hora;
            ImageView carrito;

            public mudanzaHolder(@NonNull final View itemView) {
                super(itemView);

                nombrecliente = itemView.findViewById(R.id.clientemudanza);
                distancia=itemView.findViewById(R.id.mdistancia);
                hora=(TextView) itemView.findViewById(R.id.hora);
                fecha= (TextView)itemView.findViewById(R.id.mfecha);
                carrito= (ImageView) itemView.findViewById(R.id.carito);
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
