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
import android.widget.Button;
import android.widget.EditText;
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
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class PerfilFragment extends Fragment implements Response.ErrorListener,Response.Listener<JSONObject> {
    private String UPLOAD_URL;
    private Button btnPagar;
    private EditText txtnombre;
    private EditText txtapellidos;
    private EditText txtcorreo;
    private EditText txtdireccion;
    private EditText txttelefono;
    private EditText txtcodigo_postal;
    private EditText txtfecha_registro;
    private ProgressDialog progreso2;
    private FirebaseAuth mAuth;
    private int id_cliente;
    private ProgressDialog progreso;


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
        UPLOAD_URL="http://mudanzito.site/api/auth/cliente/actualizar_cliente/";
        txtnombre = vista.findViewById(R.id.txtNombre);
        txtapellidos = vista.findViewById(R.id.txtapellido);
        txtcorreo = vista.findViewById(R.id.txtcorreo);
        txtdireccion = vista.findViewById(R.id.txtdireccion);
        txttelefono = vista.findViewById(R.id.txttelefono);
        txtcodigo_postal = vista.findViewById(R.id.txtcodigo_postal);
        txtfecha_registro = vista.findViewById(R.id.txtfecha_registro);
        request = Volley.newRequestQueue(getContext());
        mAuth = FirebaseAuth.getInstance();
        txtnombre.setEnabled(false);
        txtapellidos.setEnabled(false);
        txtcorreo.setEnabled(false);
        txtdireccion.setEnabled(false);
        txttelefono.setEnabled(false);
        txtcodigo_postal.setEnabled(false);
        txtfecha_registro.setEnabled(false);
        btnPagar = vista.findViewById(R.id.btnEnviar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtfecha_registro.isEnabled()){
                    btnPagar.setText("ENVIAR");
                    editable(true);
                }else{
                    btnPagar.setText("EDITAR");
                    editable(false);
                    subirDatos();
                }
            }
        });
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
        Log.e("hola",response.toString());

        Gson gson = new GsonBuilder().create();
        PerfilPojo choferpojo=null;
        try {
            JSONArray json = response.getJSONArray("cliente");
            for(int i = 0; i<json.length(); i++ ) {
                String chofer = json.getString(i);
                choferpojo = gson.fromJson(chofer,PerfilPojo.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtnombre.setText(choferpojo.getNombre());
        txtapellidos.setText(choferpojo.getApellidos());
        txtcorreo.setText(choferpojo.getCorreo());
        txtdireccion.setText(choferpojo.getDireccion());
        txttelefono.setText(choferpojo.getTelefono());
        txtcodigo_postal.setText(choferpojo.getCodigo_postal());
        txtfecha_registro.setText(choferpojo.getFecha_registro());
        id_cliente = choferpojo.getId_cliente();
    }

    public void editable ( boolean b){
        txtnombre.setEnabled(b);
        txtapellidos.setEnabled(b);
        txtcorreo.setEnabled(b);
        txtdireccion.setEnabled(b);
        txttelefono.setEnabled(b);
        txtcodigo_postal.setEnabled(b);
        txtfecha_registro.setEnabled(b);
    }

    private void subirDatos() {
        progreso= new ProgressDialog(getContext());
        progreso.setMessage("Enviando");
        progreso.show();
        if(compruebaConexion(getContext())){
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL+id_cliente,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(),"Datos enviados correctamente",Toast.LENGTH_LONG).show();
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
                    params.put("id_cliente",id_cliente+"");
                    params.put("nombre",txtnombre.getText().toString());
                    params.put("apellidos",txtapellidos.getText().toString());
                    params.put("correo",txtcorreo.getText().toString());
                    params.put("direccion",txtdireccion.getText().toString());
                    params.put("telefono",txttelefono.getText().toString());
                    params.put("codigo_postal",txtcodigo_postal.getText().toString());
                    params.put("fecha_registro",txtfecha_registro.getText().toString());
                    return params;
                }
            };
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
            // requestQueue.add(stringRequest);
        }else{
            progreso.dismiss();
            Toast.makeText(getContext(),"Comprueba tu conexion a internet",Toast.LENGTH_SHORT).show();
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
            Log.e("hola",mAuth.getCurrentUser().getEmail());

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, (Response.Listener<JSONObject>) this, this);
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        }else{
            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }
        progreso2.dismiss();
    }
}
