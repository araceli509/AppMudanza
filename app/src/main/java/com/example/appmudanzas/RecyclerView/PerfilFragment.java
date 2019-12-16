package com.example.appmudanzas.RecyclerView;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.Cotizacion.Servicio_ExtraPojo;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfilFragment extends Fragment implements Response.ErrorListener,Response.Listener<JSONObject> {
    private EditText txtnombre;
    private EditText txtapellidos;
    private EditText txtcorreo;
    private EditText txtdireccion;
    private EditText txttelefono;
    private EditText txtcodigo_postal;
    private EditText txtfecha_registro;
    private ProgressDialog progreso2;
    private FirebaseAuth mAuth;
    RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
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
        View vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        txtnombre = vista.findViewById(R.id.txtNombre);
        txtapellidos = vista.findViewById(R.id.txtApellidos);
        txtcorreo = vista.findViewById(R.id.txtcorreo);
        txtdireccion = vista.findViewById(R.id.txtdireccion);
        txttelefono = vista.findViewById(R.id.txttelefono);
        txtcodigo_postal = vista.findViewById(R.id.txtcodigo_postal);
        txtfecha_registro = vista.findViewById(R.id.txtfecha_registro);
        request = Volley.newRequestQueue(getContext());
        mAuth = FirebaseAuth.getInstance();
        obtenerPerfilUsuario();
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

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void getDatos() {

    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new GsonBuilder().create();
        PerfilPojo choferpojo=null;
        try {
            JSONArray json = response.getJSONArray("servicios_extras");
            for(int i = 0; i<json.length(); i++ ) {
                String chofer = json.getString(i);
                choferpojo = gson.fromJson(chofer,PerfilPojo.class);
            }
            txtnombre.setText(choferpojo.getNombre()+"");
            txtapellidos.setText(choferpojo.getApellidos()+"");
            txtcorreo.setText(choferpojo.getCorreo()+"");
            txtdireccion.setText(choferpojo.getDireccion()+"");
            txttelefono.setText(choferpojo.getTelefono()+"");
            txtcodigo_postal.setText(choferpojo.getCodigo_postal()+"");
            txtfecha_registro.setText(choferpojo.getFecha_registro()+"");
            Log.e("hola",choferpojo.getNombre());
            Log.e("hola",choferpojo.getApellidos());
            Log.e("hola",choferpojo.getCorreo());
            Log.e("hola",choferpojo.getDireccion());
            Log.e("hola",choferpojo.getTelefono());
            Log.e("hola",choferpojo.getCodigo_postal());
            Log.e("hola",choferpojo.getFecha_registro());

        } catch (JSONException e) {
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

    public void obtenerPerfilUsuario() {
        progreso2= new ProgressDialog(getContext());
        progreso2.setMessage("Enviando");
        progreso2.show();
        if(compruebaConexion(getContext())){
            String url = "http://mudanzito.site/api/auth/cliente/cliente_correo/" + mAuth.getCurrentUser().getEmail();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, (Response.Listener<JSONObject>) this, this);
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        }else{
            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }
        progreso2.dismiss();
    }
}
