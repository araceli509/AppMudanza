package com.example.appmudanzas.RecyclerView;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.Cotizacion.Cotizacion;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    RecyclerView recyclerView;
    List<ChoferPojo> choferes;
    ChoferAdapter adapter;

    DatabaseReference database;
    List<String> keys;
    String llave;
    private FirebaseAuth mAuth;
    Bundle extras;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fabfiltro;
    public InicioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

        request= Volley.newRequestQueue(getContext());
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        choferes = new ArrayList<>();
        keys = new ArrayList<>();
        obtenerDatos();

        adapter = new ChoferAdapter(choferes);
        recyclerView.setAdapter(adapter);
        fabfiltro = v.findViewById(R.id.fabfiltro);;

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putInt("id_prestador", choferes.get(recyclerView.getChildAdapterPosition(v)).getId());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PopUpChofer fragmento = new PopUpChofer();
                fragmento.setArguments(datosAEnviar);
                fragmentTransaction.replace(R.id.contenedor, fragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
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
            String url = "http://mudanzito.site/api/auth/prestador_servicio/horario_tarifa/" + obtenerHora();
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
            JSONArray json = response.getJSONArray("prestador");
            for(int i = 0; i<json.length(); i++ ) {
                String chofer = json.getString(i);
                ChoferPojo choferpojo = gson.fromJson(chofer,ChoferPojo.class);
                choferes.add(choferpojo);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String obtenerHora (){
        String hora, minutos, segundos, horafull = "";
        final Calendar c  = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY)+"";
        minutos = c.get(Calendar.MINUTE)+"";
        segundos = c.get(Calendar.SECOND)+"";
        horafull = ffh(hora)+":"+ffh(minutos)+":"+ffh(segundos);
        return horafull;
    }

    public String ffh(String dato){
        dato = (dato.length() == 1) ? "0" + dato : dato;
        return dato;
    }

}
