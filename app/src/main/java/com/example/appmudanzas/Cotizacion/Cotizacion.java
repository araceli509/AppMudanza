package com.example.appmudanzas.Cotizacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.R;
import java.util.Hashtable;
import java.util.Map;

public class Cotizacion extends Fragment{
    private OnFragmentInteractionListener mListener;
    private ProgressDialog progreso;
    TextView txtOrigen;
    TextView txtDestino;
    TextView txtKilometro;
    TextView txtTotal;
    Button btnPagar;
    String origenLatLong = "";
    String destinoLatLong = "";
    private String UPLOAD_URL="http://www.mudanzito.site/api/auth/reservacion/agregar_reservacion";
    private Button cerrar;

    RequestQueue request;
    public Cotizacion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private void getDatos() {
        Bundle datosRecuperados = getArguments();
        float km = datosRecuperados.getFloat("kilometros");
        origenLatLong = datosRecuperados.getString("origenLatLong");
        destinoLatLong = datosRecuperados.getString("destinoLatLong");
        txtOrigen.setText("Direccion de origen: " + datosRecuperados.getString("origen"));
        txtDestino.setText("Direccion de destino: " + datosRecuperados.getString("destino"));
        txtKilometro.setText(km + " Km");
        txtTotal.setText("" + km * 6);
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
                            Toast.makeText(getContext(),"Error al enviar los datos",Toast.LENGTH_LONG).show();
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
                    params.put("id_cliente","2");
                    params.put("id_prestador","2");
                    params.put("fecha_hora","2019-11-01 23:59:59");
                    params.put("origen",txtOrigen.getText().toString());
                    params.put("destino",txtDestino.getText().toString());
                    params.put("origenLatLong",origenLatLong);
                    params.put("destinoLatLong",destinoLatLong);
                    params.put("seguro","1");
                    params.put("numero_pisos", "1");
                    params.put("monto",txtTotal.getText().toString());
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }else{
            progreso.dismiss();
            Toast.makeText(getContext(),"Comprueba tu conexion a internet",Toast.LENGTH_SHORT).show();
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
        getDatos();
        request = Volley.newRequestQueue(getContext());
        btnPagar = v.findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirDatos();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}