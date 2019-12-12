package com.example.appmudanzas.prestador_Servicio;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.solicitudes.reservacion;
import com.example.appmudanzas.prestador_Servicio.solicitudes.cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class solicitud_preview extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public solicitud_preview() {
        // Required empty public constructor
    }
    /*
    * variables
    *
    *
    * */
    private TextView idsolicitud,cliente,numero,correo;

    private TextView pago,origencalles,destinocalles,pisos,estadosol,fecha,km;

    private ImageView eliminar,rutamaps,aceptar;

    private reservacion  reservacion;
    private cliente cliente_datos;

    JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;


    // TODO: Rename and change types and number of parameters
    public static solicitud_preview newInstance(String param1, String param2) {
        solicitud_preview fragment = new solicitud_preview();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_solicitud_preview, container, false);

        idsolicitud= view.findViewById(R.id.sol_id);
        cliente=view.findViewById(R.id.cliente);
        numero=view.findViewById(R.id.numero);
        correo=view.findViewById(R.id.correo);

         pago=view.findViewById(R.id.pago);
         origencalles=view.findViewById(R.id.origencalles);
         destinocalles=view.findViewById(R.id.destinocalles);
         pisos=view.findViewById(R.id.numpisos);
         estadosol=view.findViewById(R.id.estadosolicitud);
         fecha=view.findViewById(R.id.fecha);
         km=view.findViewById(R.id.km);

         eliminar=view.findViewById(R.id.eliminar);
         rutamaps=view.findViewById(R.id.rutamaps);
         aceptar=view.findViewById(R.id.aceptar);

        requestQueue= Volley.newRequestQueue(getContext());
        reservacion= (reservacion) getArguments().getSerializable("reservacion");
        cliente_datos= reservacion.getCliente();

         eliminar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 cargarDatos();
                 Toast.makeText(getContext(),"Eliminando solicitud",Toast.LENGTH_LONG).show();

             }
         });
        rutamaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString("destino",reservacion.getDestinoLatLong());
                data.putString("origen",reservacion.getOrigenLatLong());
                Intent intentomaps= new Intent(getActivity(),MapapreviewRuta.class);
                startActivity(intentomaps);
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceptar();
                Toast.makeText(getContext(),"Aceptando y procesando",Toast.LENGTH_LONG).show();
              //cerrrar el fragment no provado quitar si marca error


            }
        });



         if(cliente_datos!=null&&reservacion!=null){

             idsolicitud.setText(String.valueOf(reservacion.getId_reservacion()));
             cliente.setText(cliente_datos.getNombre()+" "+cliente_datos.getApellidos());
             numero.setText(cliente_datos.getTelefono());
             correo.setText(cliente_datos.getCorreo());

             pago.setText("$"+String.valueOf(reservacion.getMonto())+" MXN");
             origencalles.setText(reservacion.getOrigen());
             destinocalles.setText(reservacion.getDestino());
             pisos.setText(String.valueOf(reservacion.getNumero_pisos()));
             //estatus.setText(reservacion.getStatus());
             fecha.setText(String.valueOf(reservacion.getFecha()));
             km.setText(String.valueOf(reservacion.getDistancia())+" km");
         }

        return view;
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

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void cargarDatos(){
        boolean conexion=compruebaConexion(getContext());
        if(conexion) {
            String url = "http://mudanzito.site/api/auth/reservacion/eliminar_reservacion/" +reservacion.getId_reservacion();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
        }else{

            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }

    }

    private void aceptar(){
        boolean conexion=compruebaConexion(getContext());
        if(conexion) {
            String url = "http://mudanzito.site/api/auth/reservacion/aceptar_reservacion/" +reservacion.getId_reservacion();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
        }else{

            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
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


    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("Exito");
          Toast.makeText(getContext(),  jsonArray.getString(1),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
