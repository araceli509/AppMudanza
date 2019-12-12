package com.example.appmudanzas.Cotizacion;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TiempoReal extends Fragment implements OnMapReadyCallback {
    DatabaseReference mDataBase;
    JSONObject jso;
    private GoogleMap map;
    private MarkerOptions origen, destino;
    private ArrayList<Marker> tmprealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> realTimeMarkers = new ArrayList<>();
    String key;
    String emailChofer,nombreChofer;
    TextView txtInfChofer;
    FloatingActionButton fabCancel;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_tiempo_real);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        //Bundle extras = getIntent().getExtras();
        //key = extras.getString("key");
        //txtInfChofer = findViewById(R.id.txtInfChofer);
        //fabCancel = findViewById(R.id.fabCancel);
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //       .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }
*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        posicionUsuario();
        permisoUbicacionChofer();
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(MapaUsuarioTiempoReal.this,PrincipalLogin.class);
               // startActivity(intent);
            }
        });
    }

    private void posicionUsuario() {
        mDataBase.child("Users").child(key).child("posicion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double latitudOrigen = dataSnapshot.child("latitudOrigen").getValue(Double.class);
                Double latitudDestino = dataSnapshot.child("latitudDestino").getValue(Double.class);
                Double longitudOrigen = dataSnapshot.child("longitudOrigen").getValue(Double.class);
                Double longitudDestino = dataSnapshot.child("longitudDestino").getValue(Double.class);
                MarkerOptions markerOptions = new MarkerOptions();
                MarkerOptions markerOptionsOrigen = new MarkerOptions();
                origen = markerOptionsOrigen.position(new LatLng(latitudOrigen, longitudOrigen)).title("Origen");
                destino = markerOptions.position(new LatLng(latitudDestino, longitudDestino)).title("destino");
                map.addMarker(origen);
                map.addMarker(destino);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(origen.getPosition().latitude, origen.getPosition().longitude))
                        .zoom(15)
                        .bearing(90)// Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                cambiarmarcadores();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void permisoUbicacionChofer() {
        mDataBase.child("Chofer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Marker marker : realTimeMarkers) {
                    marker.remove();
                }
                for (DataSnapshot  snapshot : dataSnapshot.getChildren()) {
                    Double latitudDestino = snapshot.child("latitud").getValue(Double.class);
                    Double longitudDestino = snapshot.child("longitud").getValue(Double.class);
                    boolean permiso = snapshot.child("permiso").getValue(Boolean.class);
                    String llaveusuario = snapshot.child("cliente").getValue(String.class);
                    if (permiso&&llaveusuario.equals(key)) {
                        emailChofer=snapshot.child("email").getValue(String.class);
                        nombreChofer=snapshot.child("name").getValue(String.class);
                        txtInfChofer.setText("Informacion del chofer\nLe atiende: "+nombreChofer+"\n"+"Correo: "+emailChofer);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(latitudDestino, longitudDestino)).title("Chofer").icon(BitmapDescriptorFactory.fromResource(R.mipmap.camion));
                        tmprealTimeMarkers.add(map.addMarker(markerOptions));
                    }
                }
                ;
                realTimeMarkers.clear();
                realTimeMarkers.addAll(tmprealTimeMarkers);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void cambiarmarcadores() {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origen.getPosition().latitude + "," + origen.getPosition().longitude + "&destination=" + destino.getPosition().latitude + "," + destino.getPosition().longitude + "&mode=driving&key=" + getString(R.string.google_maps_key);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jso = new JSONObject(response);
                    trazarRuta(jso);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    private void trazarRuta(JSONObject jso) {

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) (jRoutes.get(i))).getJSONArray("legs");
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "" + ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = PolyUtil.decode(polyline);
                        map.addPolyline(new PolylineOptions().addAll(list).color(Color.YELLOW).width(35));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}