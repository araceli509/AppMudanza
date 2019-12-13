package com.example.appmudanzas.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.PosicionMensajeViewHolder> implements View.OnClickListener {

        List<PrestadorServicioIDPojo> mensaje;
private View.OnClickListener listener;

public MensajeAdapter(List<PrestadorServicioIDPojo> mensajelist) {
        this.mensaje = mensajelist;
        }

@NonNull
@Override
public PosicionMensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_mensaje, parent, false);
        v.setOnClickListener(this);
        PosicionMensajeViewHolder holder = new PosicionMensajeViewHolder(v);
        return holder;
        }

@Override
public void onBindViewHolder(@NonNull PosicionMensajeViewHolder holder, int position) {
    PrestadorServicioIDPojo men = mensaje.get(position);
        holder.txtid.setText("ID cliente: " + men.getId_cliente());
        holder.txtmensaje.setText("Descripción " + men.getDescripcion());
        holder.txtfecha_comentario.setText("Fecha del comentario " + men.getFecha_comentario());

        }


@Override
public int getItemCount() {
        return mensaje.size();
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
public static class PosicionMensajeViewHolder extends RecyclerView.ViewHolder {
    TextView txtid;
    TextView txtmensaje;
    TextView txtfecha_comentario;


    public PosicionMensajeViewHolder(View itemView) {
        super(itemView);
        txtid = itemView.findViewById(R.id.idcliente);
        txtmensaje = itemView.findViewById(R.id.descripcion);
        txtfecha_comentario = itemView.findViewById(R.id.fecha_comentario);


    }
}
}