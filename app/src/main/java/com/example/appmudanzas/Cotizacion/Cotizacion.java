package com.example.appmudanzas.Cotizacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.R;
import com.example.appmudanzas.RecyclerView.InicioFragment;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class Cotizacion extends Fragment  implements Response.Listener<JSONObject>, Response.ErrorListener, AdapterView.OnItemSelectedListener{
    private ProgressDialog progreso;
    private TextView txtOrigen;
    private TextView txtDestino;
    private TextView txtKilometro;
    private TextView txtTotal;
    private Button btnPagar;
    private String origenLatLong;
    private String destinoLatLong;
    private String UPLOAD_URL;
    private Switch switchseguro;
    private Spinner spinnerpisos;
    private String origen;
    private String destino;
    private float km;
    private int id_prestador;
    private int id_cliente;
    private FirebaseAuth mAuth;
    private float total;


    RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    public Cotizacion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_prestador = getArguments().getInt("id_prestador");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cotizacion, container, false);
        txtOrigen = v.findViewById(R.id.textOrigen);
        txtDestino = v.findViewById(R.id.textDestino);
        txtKilometro = v.findViewById(R.id.textKilometros);
        txtTotal = v.findViewById(R.id.textTotal);
        mAuth = FirebaseAuth.getInstance();
        origenLatLong = "";
        destinoLatLong = "";
        UPLOAD_URL="http://www.mudanzito.site/api/auth/reservacion/agregar_reservacion";
        getDatos();
        obtenerID();
        request = Volley.newRequestQueue(getContext());
        btnPagar = v.findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirDatos();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                InicioFragment fragmento = new InicioFragment();
                fragmentTransaction.replace(R.id.contenedor, fragmento);
                  fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        spinnerpisos = v.findViewById(R.id.spinnerpisos);
        final ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(v.getContext(),R.array.opcionpisos,android.R.layout.simple_spinner_item);
        spinnerpisos.setAdapter(adapter);
        spinnerpisos.setOnItemSelectedListener(this);
        // Inflate the layout for this fragment
        return v;
    }

    private void getDatos() {
        Bundle datosRecuperados = getArguments();
        km = datosRecuperados.getFloat("kilometros");
        total=km*400;
        origenLatLong = datosRecuperados.getString("origenLatLong");
        destinoLatLong = datosRecuperados.getString("destinoLatLong");
        origen = datosRecuperados.getString("origen");
        destino = datosRecuperados.getString("destino");
        txtOrigen.setText("Direccion de origen: " + origen);
        txtDestino.setText("Direccion de destino: " + destino);
        txtKilometro.setText(km + " Km");
        txtTotal.setText(total+"");
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

    private void subirDatos() {
        progreso= new ProgressDialog(getContext());
        progreso.setMessage("Enviando");
        progreso.show();
        if(compruebaConexion(getContext())){
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(),"Datos enviados correctamente",Toast.LENGTH_LONG).show();
                            progreso.hide();
                        }
                    },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    progreso.hide();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<>();
                    params.put("id_cliente",id_cliente+"");
                    params.put("id_prestador",id_prestador+"");
                    params.put("fecha_hora",obtenerFechaHora());
                    params.put("origen",origen);
                    params.put("destino",destino);
                    params.put("origenLatLong",origenLatLong);
                    params.put("destinoLatLong",destinoLatLong);
                    params.put("distancia",km+"");
                    params.put("numero_pisos", (String)spinnerpisos.getSelectedItem());
                    params.put("monto",txtTotal.getText().toString());
                    return params;
                }
            };
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
           // requestQueue.add(stringRequest);
        }else{
            progreso.dismiss();
            Toast.makeText(getContext(),"Comprueba tu conexion a internet",Toast.LENGTH_SHORT).show();
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

    private String obtenerFechaHora (){
        String dia, mes, anio, hora, minutos, segundos, fecha_hora = "";
        final Calendar c  = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH)+"";
        mes = (c.get(Calendar.MONTH)+1)+"";
        anio = c.get(Calendar.YEAR)+"";
        hora = c.get(Calendar.HOUR_OF_DAY)+"";
        minutos = c.get(Calendar.MINUTE)+"";
        segundos = c.get(Calendar.SECOND)+"";
        fecha_hora = ffh(anio)+"-"+ffh(mes)+"-"+ffh(dia)+" "+ffh(hora)+":"+ffh(minutos)+":"+ffh(segundos);
        return fecha_hora;
    }

    public String ffh(String dato){
        dato = (dato.length() == 1) ? "0" + dato : dato;
        return dato;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("cliente");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            id_cliente = jsonObject.getInt("id_cliente");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        float var = total+(Integer.parseInt(item)*100);
        txtTotal.setText(var+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
