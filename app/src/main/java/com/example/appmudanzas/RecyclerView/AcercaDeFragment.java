package com.example.appmudanzas.RecyclerView;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.example.appmudanzas.prestador_Servicio.mudanza.Mudanza;
import com.example.appmudanzas.prestador_Servicio.mudanza.rutaPrestador;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


/**
ara
 */
public class AcercaDeFragment extends Fragment implements OnMapReadyCallback , Response.Listener<JSONObject>, Response.ErrorListener{
    DatabaseReference mDataBase;
    JSONObject jso;
    private GoogleMap map;
    private MarkerOptions origen, destino;
    private ArrayList<Marker> tmprealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> realTimeMarkers = new ArrayList<>();
    String key;
    String emailChofer,nombreChofer;
    TextView txtInfChofer;
    Double latitud;
    Double longitud;
    private LatLng MiUbicacion;
    GeoFire geoFire;
    private Marker MKdestino;
    MarkerOptions mkOp;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference drivers;
    Marker miMarcador;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;

    int id_mudanza,id_cliente,id_prestador;
    String uid;

    rutaPrestador.Localizacion Local;
    Button comenzar;

    public AcercaDeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_acercade, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;

        //mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        id_mudanza=getArguments().getInt("id_mudanza");
        id_cliente=getArguments().getInt("id_cliente");
        id_prestador=getArguments().getInt("id_prestador");
        requestQueue = Volley.newRequestQueue(getContext());
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        drivers = db.getReference("Drivers");
        geoFire = new GeoFire(drivers);
        cargarDatos();
        mkOp = new MarkerOptions()
                .visible(true)
                .position(MiUbicacion).title("Tú");

        miMarcador = map.addMarker(mkOp);

        drivers.child(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                geoFire.getLocation(uid, new LocationCallback() {
                    @Override
                    public void onLocationResult(String key, GeoLocation location) {
                        if (location != null) {


                            if (miMarcador != null) {
                                miMarcador.setPosition(new LatLng(latitud,longitud));
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(MiUbicacion, 15.0f));

                            }

                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //LogDatabase error
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    private void cargarDatos(){
        boolean conexion=compruebaConexion(getContext());
        if(conexion) {
            String url = "http://mudanzito.site/api/auth/mudanzas/buscaruid/"+"?id_prestador="+id_prestador+"&id_cliente="+id_cliente;
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


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject jsonObject= response.getJSONObject("uid");
            uid=jsonObject.getString("uid_prestador");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
