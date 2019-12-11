package com.example.appmudanzas.RecyclerView;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.appmudanzas.Cotizacion.MapsClienteFragment;
import com.example.appmudanzas.Cotizacion.SolicitudPojo;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//ara
public class GaleriaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private TextView fecha_hora;
    private TextView origen;
    private TextView destino;
    private TextView monto;
    private TextView status;
    private Button btnpagar;
    private int id_usuario,estadop;
    private FirebaseAuth mAuth;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;


    public GaleriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_galeria, container, false);
        fecha_hora = v.findViewById(R.id.fecha_hora);
        origen = v.findViewById(R.id.origen);
        destino = v.findViewById(R.id.destino);
        monto = v.findViewById(R.id.monto);
        status = v.findViewById(R.id.status);
        btnpagar = v.findViewById(R.id.btnmapa);
        mAuth = FirebaseAuth.getInstance();
        btnpagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //escribir donde ir
            }
        });
        obtenerDatos();
        return v;
    }

    public static boolean compruebaConexion(Context context) {
        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }

    public void obtenerDatos() {
        boolean conexion=compruebaConexion(getContext());
        if(conexion) {
            String url = "http://mudanzito.site/api/auth/reservacion/reservaciones_usuario/" + 3;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        }else{
            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }
    }

    public void obtenerID() {
        boolean conexion=compruebaConexion(getContext());
        if(conexion) {
            String url = "http://mudanzito.site/api/auth/cliente/busquedacliente_correo/" + mAuth.getCurrentUser().getEmail();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        }else{
            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new GsonBuilder().create();
        SolicitudPojo solicitudpojo= null;

        try {
            JSONArray json = response.getJSONArray("reservacion");
            if (json.length() > 0) {
            String solicitud = json.getString(0);
            solicitudpojo = gson.fromJson(solicitud,SolicitudPojo.class);
                fecha_hora.setText(solicitudpojo.getFecha_hora());
                origen.setText(solicitudpojo.getOrigen());
                destino.setText(solicitudpojo.getDestino());
                monto.setText(solicitudpojo.getMonto()+"");
                status.setText(solicitudpojo.getStatus());
                estadop=Integer.parseInt(solicitudpojo.getStatus());
                if(estadop==2){
                    btnpagar.setEnabled(true);
                }else{
                    btnpagar.setEnabled(false);
                }
        }else{
                Toast.makeText(getContext(),"No hay solicitudes pendientes",Toast.LENGTH_LONG).show();

            }
    } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
