package com.example.appmudanzas.RecyclerView;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.example.appmudanzas.prestador_Servicio.solicitud_preview;
import com.example.appmudanzas.prestador_Servicio.solicitudes.cliente;
import com.example.appmudanzas.prestador_Servicio.solicitudes.reservacion;
import com.example.appmudanzas.prestador_Servicio.solicitudes.solicitudAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private InicioFragment.OnFragmentInteractionListener mListener;
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

    public InicioFragment() {
        // Required empty public constructor
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
        return v;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
            Toast.makeText(getContext(),obtenerHora(),Toast.LENGTH_LONG).show();
            String url = "http://mudanzito.site/api/auth/prestador_servicio/horario_tarifa/" + obtenerHora();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            request.add(jsonObjectRequest);
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

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InicioFragment.OnFragmentInteractionListener) {
            mListener = (InicioFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
