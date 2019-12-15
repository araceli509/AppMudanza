package com.example.appmudanzas.RecyclerView;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.Cotizacion.Servicio_ExtraPojo;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.Foto_Frontal_Vehiculo_Fragment;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.widget.AppCompatTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import static android.view.ViewGroup.*;
import static android.view.ViewGroup.LayoutParams.*;
import static android.widget.Toast.*;

public class ServiciosExtraFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, AdapterView.OnItemSelectedListener,View.OnClickListener,TimePickerDialog.OnTimeSetListener {
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";
    public final Calendar c = Calendar.getInstance();
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    private EditText etHora;
    private EditText etHoraFinal;
    private ImageButton ibObtenerHora;
    private ImageButton ib_obtener_hora_final;


    private OnFragmentInteractionListener mListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String hora_inicio;
    private String hora_salida;
    private double precio;
    private double costoXcargador;
    private double costoUnitarioCajaG;
    private double cosotUnitarioCajaM;
    private double costoUnitarioCajaC;
    String horario = "";
    private CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
    private View vista;
    private ArrayList<String> dias = new ArrayList<String>();
    private Button button;
    private EditText txtCostoempaque;
    private EditText txtCostoempaquemediano;
    private EditText txtCostoempaquepequeño;
    private EditText txtCargadorextra;
    private EditText txtPreciokm;
    private EditText horaInicial;
    private EditText txthorafinalabores;
    private String UPLOAD_URL;
    private String cadena;
    private int id_prestador;
    private ProgressDialog progreso;
    private ProgressDialog progreso2;
    private JsonObjectRequest jsonObjectRequest;
    private FirebaseAuth mAuth;
    RequestQueue request;
    private String diaseleccionados;
    private String diasagregado;
    private String auxHoraInicio;
    private String auxHoraFin;
    private View viewe;

    public ServiciosExtraFragment() {
        // Required empty public constructor
    }

    public static ServiciosExtraFragment newInstance(String param1, String param2) {
        ServiciosExtraFragment fragment = new ServiciosExtraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        UPLOAD_URL = "http://www.mudanzito.site/api/auth/Servicios_Extras/insertar_Servicios_Extras/";
        vista = inflater.inflate(R.layout.fragment_servicios_extra, container, false);


        etHora = vista.findViewById(R.id.horainicial);
        etHoraFinal = vista.findViewById(R.id.txthorafinalabores);

        ibObtenerHora = vista.findViewById(R.id.ib_obtener_hora);

        ibObtenerHora.setOnClickListener((OnClickListener) this);

        ib_obtener_hora_final = vista.findViewById(R.id.ib_obtener_hora_final);
        ib_obtener_hora_final.setOnClickListener((OnClickListener) this);

        button = vista.findViewById(R.id.btnEnviar);
        txtCostoempaque = vista.findViewById(R.id.txtCostoempaque);
        txtCostoempaquemediano = vista.findViewById(R.id.txtCostoempaquemediano);
        txtCostoempaquepequeño = vista.findViewById(R.id.txtCostoempaquepequeño);
        txtCargadorextra = vista.findViewById(R.id.txtCargadorextra);
        txtPreciokm = vista.findViewById(R.id.txtPreciokm);
        //horaInicial = vista.findViewById(R.id.txthorainicial);
        txthorafinalabores = vista.findViewById(R.id.txthorafinalabores);
        lunes = vista.findViewById(R.id.lunes);
        martes = vista.findViewById(R.id.martes);
        miercoles = vista.findViewById(R.id.miercoles);
        jueves = vista.findViewById(R.id.jueves);
        viernes = vista.findViewById(R.id.viernes);
        sabado = vista.findViewById(R.id.sabado);
        domingo = vista.findViewById(R.id.domingo);

//obtenerServicio();
        //String horario = "";
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//subirDatoservicio();
                diaseleccionados = "";
                if (lunes.isChecked()) {
                    diaseleccionados += "lunes,";
                }

                if (martes.isChecked()) {
                    diaseleccionados += "martes,";
                }


                if (miercoles.isChecked()) {
                    diaseleccionados += "miercoles,";
                }

                if (jueves.isChecked()) {
                    diaseleccionados += "jueves,";
                }


                if (viernes.isChecked()) {
                    diaseleccionados += "viernes,";
                }


                if (sabado.isChecked()) {
                    diaseleccionados += "sabado,";
                }


                if (domingo.isChecked()) {
                    diaseleccionados += "domingo,";
                }

                diaseleccionados = diaseleccionados.substring(0, diaseleccionados.length() - 1);
                horario = diaseleccionados;
                Log.e("", horario);
                optenermetodos();
                obtenerServicio();


            }

        });


        recorrer();

        return vista;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_obtener_hora_final:
                obtenerHoraFinal();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
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

    public void recorrer() {
        for (int i = 0; i < dias.size(); i++) {
            Toast.makeText(getContext(), dias.get(i), Toast.LENGTH_SHORT).show();
        }
    }


    public void optenermetodos() {
        String datos = "";
        Toast.makeText(getContext(), txtCostoempaque.getText().toString(), LENGTH_SHORT).show();
        Toast.makeText(getContext(), txtCostoempaquemediano.getText().toString(), LENGTH_SHORT).show();
        Toast.makeText(getContext(), txtCostoempaquepequeño.getText().toString(), LENGTH_SHORT).show();
        Toast.makeText(getContext(), txtCargadorextra.getText().toString(), LENGTH_SHORT).show();
        Toast.makeText(getContext(), txtPreciokm.getText().toString(), LENGTH_SHORT).show();
        Toast.makeText(getContext(), horario, LENGTH_SHORT).show();
        Toast.makeText(getContext(), horaInicial.getText().toString(), LENGTH_SHORT).show();
        Toast.makeText(getContext(), txthorafinalabores.getText().toString(), LENGTH_SHORT).show();
        //datos += txtCostoempaque.getText().toString() + "" + txtCostoempaquemediano.getText().toString() + "" + txtCostoempaquepequeño.getText().toString() + "" + txtCargadorextra.getText().toString() + "" + txtPreciokm.getText().toString()+ "" + horaInicial.getText().toString() + "" + txthorafinalabores.getText().toString();

        //Toast.makeText(getContext(),datos, LENGTH_LONG).show();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

    }

    private void subirDatoservicio() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Enviando");
        progreso.show();
        if (compruebaConexion(getContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(), "Datos enviados correctamente", Toast.LENGTH_LONG).show();
                            progreso.hide();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    progreso.hide();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<>();
                    params.put("id_prestador", id_prestador + "");
                    params.put("dias", horario);
                    params.put("hora_inicio", auxHoraInicio);
                    params.put("hora_salida", auxHoraFin);
                    params.put("precio", precio + "");
                    params.put("costoXcargador", costoXcargador + "");
                    params.put("costoUnitarioCajaG", costoUnitarioCajaG + "");
                    params.put("costoUnitarioCajaM", cosotUnitarioCajaM + "");
                    params.put("costoUnitarioCajaC", costoUnitarioCajaC + "");

                    return params;
                }
            };
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
            // requestQueue.add(stringRequest);
        } else {
            progreso.dismiss();
            Toast.makeText(getContext(), "Comprueba tu conexion a internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void obtenerServicio() {
        progreso2 = new ProgressDialog(getContext());
        progreso2.setMessage("Enviando");
        progreso2.show();
        if (compruebaConexion(getContext())) {
            String url = "http://mudanzito.site/api/auth/Servicios_Extras/mostrar_Servicios_Extras_Xid_Prestador/" + id_prestador;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(getContext(), "Revise su conexion a internet", Toast.LENGTH_LONG).show();
        }
        progreso2.dismiss();
    }


    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new GsonBuilder().create();
        Servicio_ExtraPojo choferpojo = null;
        try {
            JSONArray json = response.getJSONArray("servicios_extras");
            for (int i = 0; i < json.length(); i++) {
                String chofer = json.getString(i);
                choferpojo = gson.fromJson(chofer, Servicio_ExtraPojo.class);
            }
            txtCostoempaque.setText("" + choferpojo.getCostoUnitarioCajaC());
            txtCostoempaquemediano.setText("" + choferpojo.getCostoUnitarioCajaM());
            txtCostoempaquepequeño.setText("" + choferpojo.getCostoUnitarioCajaG());
            txtCargadorextra.setText("" + choferpojo.getCostoXcargador());
            txtPreciokm.setText("" + choferpojo.getPrecio());
            horaInicial.setText("" + choferpojo.getHora_inicio());
            txthorafinalabores.setText("" + choferpojo.getHora_final());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void obtenerHora(){
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

                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                Log.e("hora",horaFormateada);
                Log.e("minuto",minutoFormateado);
                auxHoraInicio=horaFormateada+":"+minutoFormateado+":00";
                Log.e("aux",auxHoraInicio);

            }

        }, hora, minuto, false);
        recogerHora.show();


    }

    private void obtenerHoraFinal(){
        TimePickerDialog recogerHorafinal = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateadafinal =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateadofinal = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                etHoraFinal.setText(horaFormateadafinal + DOS_PUNTOS + minutoFormateadofinal + " " + AM_PM);
                Log.e("hora",horaFormateadafinal);
                Log.e("minuto",minutoFormateadofinal);
                auxHoraInicio=horaFormateadafinal+":"+minutoFormateadofinal+":00";
                Log.e("aux",auxHoraInicio);

            }

        }, hora, minuto, false);
        recogerHorafinal.show();


    }


}