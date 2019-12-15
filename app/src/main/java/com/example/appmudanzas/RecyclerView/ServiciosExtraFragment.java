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
import android.widget.CheckBox;
import android.widget.RadioGroup;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.widget.AppCompatTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import static android.view.ViewGroup.*;
import static android.view.ViewGroup.LayoutParams.*;
import static android.widget.Toast.*;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ServiciosExtraFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;

    private View vista;
    private ArrayList<String> dias = new ArrayList<String>();
    private Button button;
    private TextInputEditText costoEmpaque;
    private TextInputEditText txtCostoempaquemediano;
    private TextInputEditText txtCostoempaquepequeño;
    private TextInputEditText txtCargadorextra;
    private TextInputEditText txtPreciokm;
    private TextInputEditText txthorainicial;
    private TextInputEditText txthorafinalabores;
    private String cadena;
    private JsonObjectRequest jsonObjectRequest;
    private FirebaseAuth mAuth;
    RequestQueue request;
    int idPrestador;
    private RequestQueue requestQueue;


    public ServiciosExtraFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_servicios_extra, container, false);
        button = vista.findViewById(R.id.btnEnviar);
        costoEmpaque = vista.findViewById(R.id.txtCostoempaque);
        txtCostoempaquemediano = vista.findViewById(R.id.txtCostoempaquemediano);
        txtCostoempaquepequeño = vista.findViewById(R.id.txtCostoempaquepequeño);
        txtCargadorextra = vista.findViewById(R.id.txtCargadorextra);
        txtPreciokm = vista.findViewById(R.id.txtPreciokm);
        txthorainicial = vista.findViewById(R.id.txthorainicial);
        txthorafinalabores = vista.findViewById(R.id.txthorafinalabores);
        lunes = vista.findViewById(R.id.lunes);
        martes = vista.findViewById(R.id.martes);
        miercoles = vista.findViewById(R.id.miercoles);
        jueves = vista.findViewById(R.id.jueves);
        viernes = vista.findViewById(R.id.viernes);
        sabado = vista.findViewById(R.id.sabado);
        domingo = vista.findViewById(R.id.domingo);

        mAuth = FirebaseAuth.getInstance();
        requestQueue= Volley.newRequestQueue(getContext());
        cargarDatos();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String horario = "";
                if (lunes.isChecked()) {
                    horario += "lunes,";
                }

                if (martes.isChecked()) {
                    horario += "martes,";
                }


                if (miercoles.isChecked()) {
                    horario += "miercoles,";
                }

                if (jueves.isChecked()) {
                    horario += "jueves,";
                }


                if (viernes.isChecked()) {
                    horario += "viernes,";
                }


                if (sabado.isChecked()) {
                    horario += "sabado,";
                }


                if (domingo.isChecked()) {
                    horario += "domingo,";
                }

                horario = horario.substring(0, horario.length() - 1);
                Log.e("", horario);


            }

        });

        recorrer();

        return vista;
    }



    public void recorrer() {
        for (int i = 0; i < dias.size(); i++) {
            Toast.makeText(getContext(), dias.get(i), Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            JSONArray jsonArray = response.getJSONArray("prestador");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            idPrestador= jsonObject.getInt("id_prestador");
            Toast.makeText(getContext(),String.valueOf(idPrestador),Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    private void cargarDatos(){
        boolean conexion=compruebaConexion(getContext());
        if(conexion) {
            FirebaseUser user = mAuth.getCurrentUser();
            String correo=user.getEmail();
            String url = "http://mudanzito.site/api/auth/cliente/busquedaprestador/" +correo;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
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


}
//http://mudanzito.site/api/auth/cliente/busquedaprestador/freddg02@hotmail.com