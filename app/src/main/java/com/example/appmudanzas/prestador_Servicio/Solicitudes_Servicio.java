package com.example.appmudanzas.prestador_Servicio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.appmudanzas.R;


import com.example.appmudanzas.prestador_Servicio.solicitudes.cliente;
import com.example.appmudanzas.prestador_Servicio.solicitudes.reservacion;
import com.example.appmudanzas.prestador_Servicio.solicitudes.solicitudAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Solicitudes_Servicio extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Solicitudes_Servicio() {
        // Required empty public constructor
    }

    RecyclerView solicitudesV;
    ArrayList<reservacion> listareservaciones;
    solicitudAdapter solitudAdapter;

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private int id_cliente;
    // TODO: Rename and change types and number of parameters
    public static Solicitudes_Servicio newInstance(String param1, String param2) {
        Solicitudes_Servicio fragment = new Solicitudes_Servicio();
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
        View view=inflater.inflate(R.layout.fragment_solicitudes__servicio, container, false);
        solicitudesV= (RecyclerView) view.findViewById(R.id.recyclerSolicitudes);
        solicitudesV.setLayoutManager(new LinearLayoutManager(getContext()));
        listareservaciones= new ArrayList<>();
        requestQueue= Volley.newRequestQueue(getContext());
        id_cliente=1;

        cargarDatos();



        return view;
    }

    private void cargarDatos(){
        boolean conexion=compruebaConexion(getContext());
       if(conexion) {
           String url = "http://mudanzito.site/api/auth/reservacion/reservaciones/" + id_cliente;
           jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
           requestQueue.add(jsonObjectRequest);
       }else{

           Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
       }

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
    public void onResponse(JSONObject response) {
        reservacion reservacion= null;

        try {
            JSONArray jsonArray = response.getJSONArray("reservaciones");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    reservacion = new reservacion();
                    JSONObject jsonObject = null;
                    jsonObject = jsonArray.getJSONObject(i);
                    reservacion.setId_reservacion(jsonObject.getInt("id_reservacion"));
                    reservacion.setId_cliente(jsonObject.getInt("id_cliente"));
                    reservacion.setId_presentardor(jsonObject.getInt("id_prestador"));
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
                    String fecha = jsonObject.getString("fecha_hora");
                    java.util.Date date = sdf1.parse(fecha);
                    Date sqlStartDate = new Date(date.getTime());
                    reservacion.setFecha(sqlStartDate);
                    reservacion.setOrigen(jsonObject.getString("origen"));
                    reservacion.setDestino(jsonObject.getString("destino"));
                    reservacion.setOrigenLatLong(jsonObject.getString("origenLatLong"));
                    reservacion.setDestinoLatLong(jsonObject.getString("destinoLatLong"));
                    reservacion.setSeguro(jsonObject.getInt("seguro"));
                    reservacion.setNumero_pisos(jsonObject.getInt("numero_pisos"));
                    reservacion.setMonto(jsonObject.getDouble("monto"));
                    reservacion.setStatus(jsonObject.getInt("status"));
                    reservacion.setDistancia(jsonObject.getDouble("distancia"));
                    reservacion.setDistancia(reservacion.getDistancia()/1000);

                    JSONObject client = jsonObject.getJSONObject("cliente");
                    cliente cliente = new cliente();
                    cliente.setId_cliente(client.getInt("id_cliente"));
                    cliente.setNombre(client.getString("nombre"));
                    cliente.setApellidos(client.getString("apellidos"));
                    cliente.setCorreo(client.getString("correo"));
                    cliente.setDireccion(client.getString("direccion"));
                    cliente.setCodigopostal(client.getString("telefono"));
                    cliente.setCodigopostal(client.getString("codigo_postal"));
                    reservacion.setCliente(cliente);
                    listareservaciones.add(reservacion);
                }

                solicitudesV.setAdapter(new solicitudAdapter(listareservaciones, new solicitudAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        reservacion rpreview=listareservaciones.get(solicitudesV.getChildAdapterPosition(itemView));

                        Toast.makeText(getContext(),"ah selecionado un item"+rpreview.getId_reservacion(),Toast.LENGTH_LONG).show();
                       if(rpreview!=null) {
                           Bundle bundle = new Bundle();
                           bundle.putSerializable("reservacion", rpreview);

                           Fragment mapa = new solicitud_preview();
                           mapa.setArguments(bundle);
                           FragmentTransaction transaction = getFragmentManager().beginTransaction();

                           transaction.replace(R.id.contenedor, mapa);
                           transaction.commit();
                       }

                    }
                }));



            }else{
                Toast.makeText(getContext(),"No hay solicitudes nuevas",Toast.LENGTH_LONG).show();

            }
            } catch(JSONException e){
                e.printStackTrace();
            } catch(ParseException e){
                e.printStackTrace();
            }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Tu error",error.getMessage().toString());
        Toast.makeText(getContext(),"Algo salior mal al consultar los datos",Toast.LENGTH_LONG).show();
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
}
