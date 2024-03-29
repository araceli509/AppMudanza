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
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.appmudanzas.RecyclerView.ChoferPojo;
import com.example.appmudanzas.RecyclerView.InicioFragment;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class Cotizacion<cosotUnitarioCajaM> extends Fragment  implements Response.Listener<JSONObject>, Response.ErrorListener{
    private ProgressDialog progreso;
    private ProgressDialog progreso2;
    private TextView txtOrigen,txtverificar;
    private TextView txtDestino;
    private TextView txtKilometro,numpisos;
    private TextView txtTotal;
    private Button btnPagar;
    private String origenLatLong;
    private String destinoLatLong;
    private String UPLOAD_URL;
    private Spinner spinnerpisos;
    private String origen;
    private String destino;
    private float km;
    private int id_prestador;
    private int id_cliente;
    private FirebaseAuth mAuth;
    private float total;
    private TextView tvtarifaprecio;
    private double cajac,cajam,cajag,numtra,precio;
    private EditText numcc;
    private EditText numcm;
    private EditText numcg;
    private EditText numt;

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
        numcc = v.findViewById(R.id.numcc);
        numcm = v.findViewById(R.id.numcm);
        numcg = v.findViewById(R.id.numcg);
        numt = v.findViewById(R.id.numt);
        numpisos = v.findViewById(R.id.numpisos);
        txtverificar = v.findViewById(R.id.txtverificar);
        txtOrigen = v.findViewById(R.id.textOrigen);
        txtDestino = v.findViewById(R.id.textDestino);
        txtKilometro = v.findViewById(R.id.textKilometros);
        txtTotal = v.findViewById(R.id.textTotal);
        tvtarifaprecio=v.findViewById(R.id.tvtarifaprecio);
        mAuth = FirebaseAuth.getInstance();
        origenLatLong = "";
        destinoLatLong = "";
        UPLOAD_URL="http://www.mudanzito.site/api/auth/reservacion/agregar_reservacion";
        getDatos();
        //obtenerID();
        request = Volley.newRequestQueue(getContext());
        btnPagar = v.findViewById(R.id.btnPagar);
        getidcliente();
        obtenerPrecios();
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
        //spinnerpisos = v.findViewById(R.id.spinnerpisos);
        final ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(v.getContext(),R.array.opcionpisos,android.R.layout.simple_spinner_item);
       // spinnerpisos.setAdapter(adapter);
       // spinnerpisos.setOnItemSelectedListener(this);
        txtverificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ca = "0";
                try {
                    double cc = Double.parseDouble(numcc.getText().toString())*cajac;
                    double cm = Double.parseDouble(numcm.getText().toString())*cajam;
                    double cg = Double.parseDouble(numcg.getText().toString())*cajag;
                    double nt = Double.parseDouble(numt.getText().toString())*numtra;
                    double np = Double.parseDouble(numpisos.getText().toString())*100;
                    double tot = cc+cm+cg+nt+np+total;
                    ca =""+tot;

                }catch (NumberFormatException n){

                }

                txtTotal.setText(ca );
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void getidcliente(){
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://mudanzito.site/api/auth/cliente/busquedacliente_correo/" + mAuth.getCurrentUser().getEmail(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("cliente");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            id_cliente = jsonObject.getInt("id_cliente");
                        } catch (JSONException e) {
                            e.printStackTrace(); }}},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "Error Respuesta en JSON: " + error.getMessage());

                    }
                }
        );
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsArrayRequest);
    }

    private void getDatos() {
        Bundle datosRecuperados = getArguments();
        km = datosRecuperados.getFloat("kilometros");
        origenLatLong = datosRecuperados.getString("origenLatLong");
        destinoLatLong = datosRecuperados.getString("destinoLatLong");
        origen = datosRecuperados.getString("origen");
        destino = datosRecuperados.getString("destino");
        txtOrigen.setText("Direccion de origen: " + origen);
        txtDestino.setText("Direccion de destino: " + destino);
        total= (float) (km*precio);
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
                    params.put("seguro","0");
                    params.put("numero_pisos", (String)spinnerpisos.getSelectedItem());
                    params.put("monto",txtTotal.getText().toString());
                    params.put("numeroCajas","0");
                    params.put("numTrabajadores","0");
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

    public void obtenerPrecios() {
        progreso2= new ProgressDialog(getContext());
        progreso2.setMessage("Enviando");
        progreso2.show();
        if(compruebaConexion(getContext())){
            String url = "http://mudanzito.site/api/auth/servicios/mostrar_servicios/" + id_prestador;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        }else{
            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }
        progreso2.dismiss();
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
        Gson gson = new GsonBuilder().create();
        Servicio_ExtraPojo choferpojo=null;
        try {
            JSONArray json = response.getJSONArray("servicios_extras");
            for(int i = 0; i<json.length(); i++ ) {
                String chofer = json.getString(i);
                choferpojo = gson.fromJson(chofer,Servicio_ExtraPojo.class);
            }
            precio=choferpojo.getPrecio();
            tvtarifaprecio.setText("$ "+precio+"/Km");
            cajac = choferpojo.getCostoUnitarioCajaC();
            cajam = choferpojo.getCostoUnitarioCajaM();
            cajag = choferpojo.getCostoUnitarioCajaG();
            numtra = choferpojo.getCostoXcargador();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        float var = total+(Integer.parseInt(item)*100);
        txtTotal.setText(var+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/


}