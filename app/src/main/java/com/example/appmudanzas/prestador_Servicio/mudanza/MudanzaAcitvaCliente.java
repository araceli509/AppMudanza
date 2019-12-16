package com.example.appmudanzas.prestador_Servicio.mudanza;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.R;
import com.example.appmudanzas.RecyclerView.AcercaDeFragment;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.example.appmudanzas.prestador_Servicio.solicitudes.solicitudAdapter;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MudanzaAcitvaCliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MudanzaAcitvaCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MudanzaAcitvaCliente extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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
    private int id_cliente;
    ProgressDialog progreso;

    View view;

    public MudanzaAcitvaCliente() {
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
    public static MudanzaAcitvaCliente newInstance(String param1, String param2) {
        MudanzaAcitvaCliente fragment = new MudanzaAcitvaCliente();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mudanza_activa, container, false);
        recyclerMudanzas= view.findViewById(R.id.recycleractivas);
        recyclerMudanzas.setLayoutManager(new LinearLayoutManager(getContext()));
        listaMudanzas= new ArrayList<>();
        requestQueue= Volley.newRequestQueue(getContext());
        //tomar el id del prestador actual
        id_cliente= getArguments().getInt("id_cliente");
        Log.e("id",String.valueOf(id_cliente));
        if(id_cliente>=1){
            try {
                cargarDatos();
            }catch (Exception e){

            }

        }else{
            Toast.makeText(getContext(),"Error al consultar de la base de datos"+ id_cliente,Toast.LENGTH_LONG).show();

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


                    JSONObject client = jsonObjects.getJSONObject("prestador");
                    Prestador_Servicio prestador_servicio = new Prestador_Servicio();
                    prestador_servicio.setId_prestador(client.getInt("id_prestador"));
                    prestador_servicio.setNombre(client.getString("nombre"));
                    prestador_servicio.setApellidos(client.getString("apellidos"));
                    prestador_servicio.setCorreo(client.getString("correo"));
                    prestador_servicio.setDireccion(client.getString("direccion"));
                    prestador_servicio.setTelefono(client.getString("telefono"));
                    prestador_servicio.setCodigo_postal(client.getString("codigo_postal"));
                    prestador_servicio.setStatus(client.getInt("status"));

                    mudanza.setPrestador(prestador_servicio);

                    listaMudanzas.add(mudanza);
                }

                recyclerMudanzas.setAdapter(new mudanzaAdapter(listaMudanzas, new solicitudAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                Mudanza mudanza=listaMudanzas.get(recyclerMudanzas.getChildAdapterPosition(itemView));

                        Toast.makeText(getContext(),"ah selecionado un item"+mudanza.getId_mudanza(),Toast.LENGTH_LONG).show();
                        if(mudanza!=null) {
                            terminarMudanza(mudanza);

                        }

                    }
                }));
                recyclerMudanzas.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerMudanzas.setItemAnimator(new SlideInUpAnimator());



            }else{
                Toast.makeText(getContext(),"No hay solicitudes nuevas",Toast.LENGTH_LONG).show();

            }
        } catch(JSONException e){
            e.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        }
    }


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
            String url = "http://mudanzito.site/api/auth/mudanzas/mismudanzasactivascliente/" + id_cliente;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
        }else{

            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }

    }

    private void insertarComentario(final Mudanza mudanza){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        dialog.setTitle("Califica tu Mudanza");
        dialog.setMessage("Cuentanos, ¿como estuvo?");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View register_layout = inflater.inflate(R.layout.dialogo_comentariomudanza,null);

        final EditText texto = register_layout.findViewById(R.id.textocomentario);
        final RatingBar barrita = register_layout.findViewById(R.id.barraratingcomentario);
        java.util.Date date = new java.util.Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.format(date);
        final String fecha=dateFormat.format(date);

        dialog.setView(register_layout);
        dialog.setPositiveButton("Enviar Comentario", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progreso = new ProgressDialog(getContext());
                progreso.setMessage("Enviando..");
                progreso.show();
                dialog.dismiss();

                if (compruebaConexion(getContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mudanzito.site/api/auth/comentario/agregar_comentario",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progreso.hide();
                            Snackbar.make(getView(),"Enviando comentario",Snackbar.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Algo salio mal", Toast.LENGTH_LONG).show();
                    progreso.hide();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<>();
                    params.put("descripcion", texto.getText().toString());
                    params.put("id_cliente", String.valueOf(mudanza.getId_cliente()));
                    params.put("fecha_comentario",fecha);
                    params.put("id_prestador",String.valueOf(mudanza.getId_prestador()));
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
        dialog.setNeutralButton("Mejor despues", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void terminarMudanza(final Mudanza mudanza) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        dialog.setTitle("Tu Mudanza esta activa");
        dialog.setMessage("Selecciona una opcion");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View register_layout = inflater.inflate(R.layout.dialogo_comenzarmudanza,null);

        final ImageView mapa = register_layout.findViewById(R.id.map);


        dialog.setView(register_layout);
        dialog.setPositiveButton("Ubicacion Prestador", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progreso = new ProgressDialog(getContext());
                progreso.setMessage("Activando Mudanza");
                progreso.show();
                dialog.dismiss();

                terminarmimudanza(mudanza);
                Bundle data= new Bundle();
                data.putInt("id_cliente",mudanza.getId_cliente());
                data.putInt("id_prestador",mudanza.getId_prestador());
                data.putInt("id_mudanza",mudanza.getId_mudanza());

                Fragment AcercaDeFragment= new AcercaDeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor,AcercaDeFragment)
                        .addToBackStack(null)
                        .commit();



            }
        });
        dialog.setNeutralButton("Terminar y Evaluar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                terminarmimudanza(mudanza);
                insertarComentario(mudanza);
            }
        });
        dialog.show();




    }
    private void terminarmimudanza(final Mudanza mudanza){
        if (compruebaConexion(getContext())) {
            progreso = new ProgressDialog(getContext());
            progreso.setMessage("Activando Mudanza");
            progreso.show();

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mudanzito.site/api/auth/mudanzas/cambiarestadomudazas",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progreso.hide();
                            Snackbar.make(getView(),"Mudanza Completada..",Snackbar.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Algo salio mal", Toast.LENGTH_LONG).show();
                    progreso.hide();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<>();
                    params.put("id_mudanza", String.valueOf(mudanza.getId_mudanza()));
                    params.put("status", String.valueOf(2));
                    return params;
                }
            };
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);

        } else {
            progreso.dismiss();
            Snackbar.make(getView(),"upps algo salio mal :(",Snackbar.LENGTH_LONG).show();
        }
    }

}
