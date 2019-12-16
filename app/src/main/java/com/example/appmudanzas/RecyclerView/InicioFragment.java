package com.example.appmudanzas.RecyclerView;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {


    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";
    public final Calendar c = Calendar.getInstance();

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);


    RecyclerView recyclerView;
    List<ChoferPojo> choferes;
    EditText horadisponible;
    ChoferAdapter adapter;
    DatabaseReference database;
    List<String> keys;
    String llave;
    Button inicio;
    private FirebaseAuth mAuth;
    Bundle extras;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    FloatingActionButton fabfiltro;
    Dialog dialogo;
    private String UPLOAD_URL="http://mudanzito.site/api/auth/cliente/agregar_cliente/";
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
        inicio = v.findViewById(R.id.btncerrarinicio);
        recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogo= new Dialog(getContext());

        inicio.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showPopup(view);
    }
});
        choferes = new ArrayList<>();
        keys = new ArrayList<>();
        obtenerDatos();
        adapter = new ChoferAdapter(choferes);
        recyclerView.setAdapter(adapter);

       // fabfiltro = v.findViewById(R.id.fabfiltro);
        /*fabfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
*/
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putInt("id_prestador", choferes.get(recyclerView.getChildAdapterPosition(v)).getId_prestador());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PopUpChofer fragmento = new PopUpChofer();
                fragmento.setArguments(datosAEnviar);
                fragmentTransaction.replace(R.id.contenedor, fragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        subirDatosCliente();
        return v;
    }

    public void showPopup(View v) {


        final RatingBar ranking;
        ImageButton ib_horadisponibleButton;

        TextView txtclose;
        Button buttonEnviar;
        dialogo.setContentView(R.layout.fragment_ventana_emergente);
        txtclose = dialogo.findViewById(R.id.txtcerrar);

        horadisponible = dialogo.findViewById(R.id.horadisponible);
        ib_horadisponibleButton = dialogo.findViewById(R.id.ib_horadisponibleButton);
        ranking=dialogo.findViewById(R.id.ranking);
        buttonEnviar=dialogo.findViewById(R.id.buttonEnviar);
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating =String.valueOf(ranking.getRating());
                Log.e("error",rating);
            }
        });


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
               ib_horadisponibleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.ib_horadisponibleButton:
                        obtenerHora1();
                        break;
                }
            }
        });
    }

        private void obtenerHora1(){
            TimePickerDialog recogerHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                    String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                    String AM_PM;
                    if(hourOfDay < 12) {
                        AM_PM = "a.m.";
                    } else {
                        AM_PM = "p.m.";
                    }

                    horadisponible.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                    Log.e("hora",horaFormateada);
                    Log.e("minuto",minutoFormateado);
                    String aux=horaFormateada+":"+minutoFormateado+":00";
                    Log.e("aux",aux);

                }

            }, hora, minuto, false);

            recogerHora.show();

        }











    /*public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        VentanaEmergente newFragment = new VentanaEmergente();

            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();

    }*/

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

    public void subirDatosCliente(){
        Log.e("Error",mAuth.getCurrentUser().getDisplayName());
        Log.e("Error",obtenerFechaRegistro());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<>();
                params.put("nombre", mAuth.getCurrentUser().getDisplayName());
                params.put("apellidos",mAuth.getCurrentUser().getDisplayName());
                params.put("correo",mAuth.getCurrentUser().getEmail());
                params.put("direccion", "Ninguna");
                params.put("telefono","0000000000");
                params.put("codigo_postal","00000");
                params.put("fecha_registro",obtenerFechaRegistro());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    public String obtenerFechaRegistro(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }

}
