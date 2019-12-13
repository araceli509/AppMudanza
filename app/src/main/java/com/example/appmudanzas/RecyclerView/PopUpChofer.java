package com.example.appmudanzas.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.appmudanzas.Cotizacion.MapsClienteFragment;
import com.example.appmudanzas.R;
import com.example.appmudanzas.mCloud.CloudinaryClient;
import com.example.appmudanzas.mCloud.PicassoClient;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PopUpChofer extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    private TextView nombrePrestador;
    private TextView telPrestador;
    private TextView correoPrestador;
    private TextView tarifaPrestador;
    private TextView direccionPrestador;
    private TextView horarioPrestador;
    private AppCompatRatingBar ranking;
    private Button btnmapa;
    private int id_prestador;
    private ImageView imageFotoPerfil;
    private ImageView imageVehiculoPrestador,imageVehiculoLateral,imageVehiculoTrasera;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    public PopUpChofer() {
        // Required empty public constructor
    }

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_prestador = getArguments().getInt("id_prestador");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pop_up_chofer, container, false);
        nombrePrestador = v.findViewById(R.id.nombrePrestador);
        telPrestador = v.findViewById(R.id.telPrestador);
        correoPrestador = v.findViewById(R.id.correoPrestador);
        tarifaPrestador = v.findViewById(R.id.tarifaPrestador);
        direccionPrestador = v.findViewById(R.id.direccionPrestador);
        horarioPrestador = v.findViewById(R.id.horarioPrestador);
        ranking = v.findViewById(R.id.ranking);
        btnmapa = v.findViewById(R.id.btnmapa);
        imageVehiculoPrestador=v.findViewById(R.id.imageVehiculoFrontal);
        imageVehiculoLateral=v.findViewById(R.id.imageVehiculoLateral);
        imageVehiculoTrasera=v.findViewById(R.id.imageVehiculoTrasera);
        imageFotoPerfil=v.findViewById(R.id.imageFotoPerfil);
        btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putInt("id_prestador", id_prestador);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MapsClienteFragment fragmento = new MapsClienteFragment();
                fragmento.setArguments(datosAEnviar);
                fragmentTransaction.replace(R.id.contenedor, fragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        obtenerDatos();
        return v;
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

    public void obtenerDatos() {
        boolean conexion=compruebaConexion(getContext());
        if(conexion) {
            String url = "http://mudanzito.site/api/auth/prestador_servicio/busquedaprestador_id/" + id_prestador;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        }else{
            Toast.makeText(getContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new GsonBuilder().create();
        PrestadorServicioIDPojo choferpojo= null;
        try {
            JSONArray json = response.getJSONArray("prestador");
                String chofer = json.getString(0);
                choferpojo = gson.fromJson(chofer,PrestadorServicioIDPojo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nombrePrestador.setText(choferpojo.getNombre());
        telPrestador.setText(choferpojo.getTelefono());
        correoPrestador.setText(choferpojo.getCorreo());
        tarifaPrestador.setText(choferpojo.getPrecio()+"");
        direccionPrestador.setText(choferpojo.getDireccion());
        horarioPrestador.setText(choferpojo.horario());
        ranking.setRating(choferpojo.getValoracion());
        PicassoClient.downloadImage(getActivity(), CloudinaryClient.getRoundCornerImage("foto_perfil/"+choferpojo.getFoto_perfil()),imageFotoPerfil);
        PicassoClient.downloadImage(getActivity(), CloudinaryClient.getRoundCornerImage("foto_frontal/"+choferpojo.getFoto_frontal()),imageVehiculoPrestador);
        PicassoClient.downloadImage(getActivity(), CloudinaryClient.getRoundCornerImage("foto_lateral/"+choferpojo.getFoto_lateral()),imageVehiculoLateral);
        PicassoClient.downloadImage(getActivity(), CloudinaryClient.getRoundCornerImage("foto_trasera/"+choferpojo.getFoto_trasera()),imageVehiculoTrasera);

        //aqui le agregas los demas datos para el auto
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
