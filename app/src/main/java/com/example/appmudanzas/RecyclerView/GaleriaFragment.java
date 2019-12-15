package com.example.appmudanzas.RecyclerView;


import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.Cotizacion.MapsClienteFragment;
import com.example.appmudanzas.Cotizacion.SolicitudAdapter;
import com.example.appmudanzas.Cotizacion.SolicitudPojo;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//ara
public class GaleriaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private FirebaseAuth mAuth;
    private String montoPago;

private int id_cliente;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private ProgressDialog progreso;
    RecyclerView recyclerView;
    List<SolicitudPojo> solicitudes;
    SolicitudAdapter adapter;
    DatabaseReference database;
    List<String> keys;


    public GaleriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_galeria, container, false);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //getidcliente();
        request= Volley.newRequestQueue(getContext());
        recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        solicitudes = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new SolicitudAdapter(solicitudes);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(solicitudes.get(recyclerView.getChildAdapterPosition(v)).getStatus().equals("2")){
                montoPago= solicitudes.get(recyclerView.getChildAdapterPosition(v)).getMonto();
                Bundle b= new Bundle();
                b.putString("monto",montoPago);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                PayPalFragment paypal= new PayPalFragment();
                paypal.setArguments(b);
                fragmentManager.beginTransaction().replace(R.id.contenedor, paypal).commit();
            }else{
                    Toast.makeText(getContext(),"Solicitud Pendiente",Toast.LENGTH_LONG).show();
                }
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
            String url = "http://mudanzito.site//api/auth/cliente/busquedacliente_correo_reservacion/" + mAuth.getCurrentUser().getEmail();
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
        try {
            JSONArray json = response.getJSONArray("reservacion");
            if(json.length()>0){
            for(int i = 0; i<json.length(); i++ ) {
                String solicitud = json.getString(i);
                SolicitudPojo solicitudPojo = gson.fromJson(solicitud,SolicitudPojo.class);
                solicitudes.add(solicitudPojo);
            }
            adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
