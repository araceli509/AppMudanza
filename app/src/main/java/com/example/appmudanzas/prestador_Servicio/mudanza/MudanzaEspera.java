package com.example.appmudanzas.prestador_Servicio.mudanza;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.example.appmudanzas.prestador_Servicio.solicitudes.cliente;
import com.example.appmudanzas.prestador_Servicio.solicitudes.solicitudAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MudanzaEspera.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MudanzaEspera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MudanzaEspera extends Fragment  implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;
    RecyclerView recyclerMudanzaespera;
    ArrayList<Mudanza> listaMudanzas;
    mudanzaAdapter mudanzaAdapter;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private int id_prestador;
    ProgressDialog progreso;


    public MudanzaEspera() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MudanzaEspera.
     */
    // TODO: Rename and change types and number of parameters
    public static MudanzaEspera newInstance(String param1, String param2) {
        MudanzaEspera fragment = new MudanzaEspera();
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
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_mudanza_espera, container, false);
        recyclerMudanzaespera= view.findViewById(R.id.recyclerMudanzaespera);
        recyclerMudanzaespera.setLayoutManager(new LinearLayoutManager(getContext()));
        listaMudanzas= new ArrayList<>();
        requestQueue= Volley.newRequestQueue(getContext());
        id_prestador= getArguments().getInt("id_prestador");
        Log.e("id_prestador",String.valueOf(id_prestador));
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
            Toast.makeText(getContext(),"Error"+error.getMessage(),Toast.LENGTH_LONG);
    }

    @Override
    public void onResponse(JSONObject response) {
        Mudanza mudanza = null;

        try {
            JSONArray jsonArray= response.getJSONArray("mudanzas");


            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    mudanza = new Mudanza();
                    JSONObject jsonObjects = null;
                    jsonObjects = jsonArray.getJSONObject(i);
                    mudanza.setId_mudanza(jsonObjects.getInt("id_mudanza"));
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


                    JSONObject client = jsonObjects.getJSONObject("cliente");
                    cliente cliente = new cliente();
                    cliente.setId_cliente(client.getInt("id_cliente"));
                    cliente.setNombre(client.getString("nombre"));
                    cliente.setApellidos(client.getString("apellidos"));
                    cliente.setCorreo(client.getString("correo"));
                    cliente.setDireccion(client.getString("direccion"));
                    cliente.setTelefono(client.getString("telefono"));
                    cliente.setCodigopostal(client.getString("codigo_postal"));

                    mudanza.setCliente(cliente);

                    listaMudanzas.add(mudanza);
                }

                recyclerMudanzaespera.setAdapter(new mudanzaAdapter(listaMudanzas, new solicitudAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        final Mudanza mudanza=listaMudanzas.get(recyclerMudanzaespera.getChildAdapterPosition(itemView));

                        Toast.makeText(getContext(),"ah selecionado un item"+mudanza.getId_mudanza(),Toast.LENGTH_LONG).show();
                        if(mudanza!=null) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                            dialog.setTitle("Comenzar Mudanza ");
                            dialog.setMessage("desea comenzar con esta mudanza");

                            LayoutInflater inflater = LayoutInflater.from(getContext());
                            View register_layout = inflater.inflate(R.layout.dialogo_comenzarmudanza,null);

                            final ImageView mapa = register_layout.findViewById(R.id.map);


                            dialog.setView(register_layout);

                            dialog.setPositiveButton("Comenzar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progreso = new ProgressDialog(getContext());
                                    progreso.setMessage("Activando Mudanza");
                                    progreso.show();
                                    dialog.dismiss();
                                    if (compruebaConexion(getContext())) {
                                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mudanzito.site/api/auth/mudanzas/cambiarestadomudazas",
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {

                                                        progreso.hide();
                                                        Snackbar.make(getView(),"Comenzando Viaje..",Snackbar.LENGTH_LONG).show();
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
                                                params.put("id_mudanza", String.valueOf(mudanza.getId_mudanza()));
                                                params.put("status", String.valueOf(3));
                                                return params;
                                            }
                                        };
                                        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);

                                    } else {
                                        progreso.dismiss();
                                        Snackbar.make(getView(),"upps algo salio mal :(",Snackbar.LENGTH_LONG).show();
                                    }



                                }
                            });
                            dialog.show();


                            Log.d("entre",mudanza.toString());
                        }

                    }
                }));
                recyclerMudanzaespera.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerMudanzaespera.setItemAnimator(new SlideInUpAnimator());
                Log.d("Informacion",String.valueOf(recyclerMudanzaespera.getAdapter().getItemCount()));


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
            String url = "http://mudanzito.site/api/auth/mudanzas/listarmismudanzaspendientes/" + id_prestador;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
            Toast.makeText(getContext(),"cargando datos",Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }

    }

    private void acvitarMudanza(final Mudanza mudanza) {




    }
}
