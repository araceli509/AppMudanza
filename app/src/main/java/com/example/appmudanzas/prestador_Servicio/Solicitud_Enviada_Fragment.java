package com.example.appmudanzas.prestador_Servicio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.appmudanzas.R;

import java.util.Hashtable;
import java.util.Map;

public class Solicitud_Enviada_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Button btnSolicitudEnviada;
    private View vista;
    private String UPLOAD_URL_RANKING = "http://mudanzito.site/api/auth/servicios/insertar_servicio_ranking/";
    private String id_prestador="";
    public Solicitud_Enviada_Fragment() {
    }

    public static Solicitud_Enviada_Fragment newInstance(String param1, String param2) {
        Solicitud_Enviada_Fragment fragment = new Solicitud_Enviada_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_solicitud__enviada_, container, false);
        Bundle datos=getArguments();
        id_prestador=datos.getString("id_prestador");
        Toast.makeText(getContext(),id_prestador,Toast.LENGTH_LONG).show();

        btnSolicitudEnviada = vista.findViewById(R.id.btn_finalizar);
        btnSolicitudEnviada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //subirDatosHorario();
                subirDatosRanking();
                Login_Prestador_Servicio_Fragment l = new Login_Prestador_Servicio_Fragment();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.contenedor, l).addToBackStack(null);
                fr.commit();
            }
        });
        return vista;
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
        void onFragmentInteraction(Uri uri);
    }

    private void subirDatosRanking() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL_RANKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),"Correctamente",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<>();
                params.put("id_prestador", id_prestador);
                params.put("valoracion", "0");
                params.put("dias", "lunes,martes,miercoles,jueves,viernes");
                params.put("hora_inicio", "09:00:00");
                params.put("hora_salida", "19:00:00");
                params.put("costoXcargador", "120");
                params.put("costoUnitarioCajaG", "150");
                params.put("costoUnitarioCajaM", "100");
                params.put("costoUnitarioCajaC", "50");
                params.put("precio", "0");
                params.put("descripcion", "Bienvenido");
                params.put("fecha_comentario", "2019-01-01");
                params.put("id_cliente", "1");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
}

    /*public void subirDatosHorario(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL_HORARIO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<>();
                params.put("id_prestador","6");
                params.put("dias","lunes");
                params.put("hora_inicio","09:00:00");
                params.put("hora_salida","19:00:00");
                params.put("precio","200");
                params.put("costoXcargador","120");
                params.put("costoUnitarioCajaG","150");
                params.put("costoUnitarioCajaM","100");
                params.put("costoUnitarioCajaC","50");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }]

}*/
