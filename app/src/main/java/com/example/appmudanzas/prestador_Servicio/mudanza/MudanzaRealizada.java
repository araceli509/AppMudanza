package com.example.appmudanzas.prestador_Servicio.mudanza;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.solicitud_preview;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MudanzaRealizada.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MudanzaRealizada#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MudanzaRealizada extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerMudanzas;
    ArrayList<Mudanza> listaMudanzas;
    mudanzaAdapter mudanzaAdapter;

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private int id_prestador;

    View view;

    public MudanzaRealizada() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MudanzaRealizada.
     */
    // TODO: Rename and change types and number of parameters
    public static MudanzaRealizada newInstance(String param1, String param2) {
        MudanzaRealizada fragment = new MudanzaRealizada();
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
        view=inflater.inflate(R.layout.fragment_solicitudes__servicio, container, false);
        recyclerMudanzas= (RecyclerView) view.findViewById(R.id.recyclermudanzas);
        recyclerMudanzas.setLayoutManager(new LinearLayoutManager(getContext()));
        listaMudanzas= new ArrayList<>();
        requestQueue= Volley.newRequestQueue(getContext());
        //tomar el id del prestador actual
        id_prestador= getArguments().getInt("id_prestador");
        Log.e("id",String.valueOf(id_prestador));
        if(id_prestador>=1){
            try {
                cargarDatos();
            }catch (Exception e){

            }

        }else{
            Toast.makeText(getContext(),"Error al consultar de la base de datos"+ id_prestador,Toast.LENGTH_LONG).show();

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
            Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Mudanza mudanza = null;

        try {
            JSONArray jsonArrays= response.getJSONArray("mudanzas");
            JSONObject jsonOb  = jsonArrays.getJSONObject(0);
            JSONArray jsonArray=jsonOb.getJSONArray("mis_mudanzas");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    mudanza = new Mudanza();
                    JSONObject jsonObjects = null;
                    jsonObjects = jsonArray.getJSONObject(i);
                    mudanza.setId_prestador(jsonObjects.getInt("id_prestador"));
                    mudanza.setId_cliente(jsonObjects.getInt("id_cliente"));
                    mudanza.setId_prestador(jsonObjects.getInt("id_prestador"));
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
                    String fecha = jsonObjects.getString("fecha_mudanza");
                    java.util.Date date = sdf1.parse(fecha);
                    Date sqlStartDate = new Date(date.getTime());
                    mudanza.setFecha(fecha);
                    mudanza.setHora( jsonObjects.getString("hora"));
                    mudanza.setStatus( jsonObjects.getInt("status"));
                    mudanza.setOrigen( jsonObjects.getString("origen"));
                    mudanza.setDestino( jsonObjects.getString("destino"));
                    mudanza.setDistancia(jsonObjects.getDouble("distancia"));

                    listaMudanzas.add(mudanza);
                }

                recyclerMudanzas.setAdapter(new mudanzaAdapter(listaMudanzas, new solicitudAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Mudanza mudanza=listaMudanzas.get(recyclerMudanzas.getChildAdapterPosition(itemView));

                        Toast.makeText(getContext(),"ah selecionado un item"+mudanza.getId_mudanza(),Toast.LENGTH_LONG).show();
                        if(mudanza!=null) {

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

    private void cargarDatos(){
        boolean conexion=compruebaConexion(getContext());
        if(conexion) {
            String url = "http://mudanzito.site/api/auth/mudanzas/listarmismudanzasprestador/" + id_prestador;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
        }else{

            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }

    }


}
