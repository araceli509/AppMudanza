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

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import static android.view.ViewGroup.*;
import static android.view.ViewGroup.LayoutParams.*;
import static android.widget.Toast.*;

public class ServiciosExtraFragment extends Fragment {

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


    public ServiciosExtraFragment() {
        // Required empty public constructor
    }


    /*
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
                    String deseaseguro = (switchseguro.isChecked())?"1":"0";
                    Map<String, String> params = new Hashtable<>();
                    params.put("costoEmpaque",costoEmpaque+"");
                    params.put("txtCostoempaquemediano",txtCostoempaquemediano+"");
                    params.put("txtCostoempaquepequeño",txtCostoempaquepequeño+"");
                    params.put("txtCargadorextra",txtCargadorextra+"");
                    params.put("txthorainicial",txthorainicial+"");
                    params.put("txthorafinalabores",txthorafinalabores+"");
                    params.put("lunes",lunes.getText().toString());
                    params.put("martes",martes.getText().toString());
                    params.put("miercoles",miercoles.getText().toString());
                    params.put("jueves",jueves.getText().toString());
                    params.put("viernes",viernes.getText().toString());
                    params.put("sabado",sabado.getText().toString());
                    params.put("domingo",domingo.getText().toString());

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

     */
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

    public void recorrer() {
        for (int i = 0; i < dias.size(); i++) {
            Toast.makeText(getContext(), dias.get(i), Toast.LENGTH_SHORT).show();
        }
    }


    public void obtenerId() {

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}
//http://mudanzito.site/api/auth/cliente/busquedaprestador/freddg02@hotmail.com