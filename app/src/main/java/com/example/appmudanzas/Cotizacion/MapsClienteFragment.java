package com.example.appmudanzas.Cotizacion;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.PolyUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsClienteFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private Boolean actualPosition = true;
    private JSONObject jso;
    private Double longitudOrigen, latitudOrigen;
    private List<MarkerOptions> listaMarker = new ArrayList<>();
    private Button getOrigen, getDestino;
    private TextView tvLocInfo;
    private FloatingActionButton fabTaxi;
    private List<Polyline> polylines = new ArrayList<>();
    private MarkerOptions origen, destino;
    private String direccionOrigen, direccionDestino;
    private int id_prestador;

    public MapsClienteFragment() {

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
        View v = inflater.inflate(R.layout.fragment_maps_cliente, container, false);
        direccionDestino = "";
        direccionOrigen = "";
        tvLocInfo = v.findViewById(R.id.textOrigen);
        getOrigen = v.findViewById(R.id.buttonOrigen);
        getDestino = v.findViewById(R.id.buttonDestino);
        fabTaxi = v.findViewById(R.id.fabTaxi);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    public String origenLatLong(){
        String origenLatLon = origen.getPosition().latitude+ "," + origen.getPosition().longitude;
        return origenLatLon;
    }

    public String destinoLatLong(){
        String destinoLatLon = destino.getPosition().latitude+ "," + destino.getPosition().longitude;
        return destinoLatLon;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getOrigen.setSelected(true);
        getOrigen.setOnClickListener(clickOrigen);
        getDestino.setOnClickListener(clickDestino);
        fabTaxi.setOnClickListener(clickfabTaxi);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            return;
        }

        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (actualPosition) {
                    latitudOrigen = location.getLatitude();
                    longitudOrigen = location.getLongitude();
                    actualPosition = false;

                    LatLng miPosicion = new LatLng(latitudOrigen, longitudOrigen);
                    origen = new MarkerOptions().position(miPosicion).title("origen").draggable(true);
                    map.addMarker(origen);
                    listaMarker.add(origen);
                    direccionOrigen = getdireccion(origen.getPosition());
                    cambiarTextoOrigen();

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitudOrigen, longitudOrigen))      // Sets the center of the map to Mountain View
                            .zoom(15)
                            .bearing(90)// Sets the zoom
                            .build();                   // Creates a CameraPosition from the builder
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    map.setOnMapLongClickListener(marcadordestino);
                    map.setOnMarkerDragListener(marcadordraggable);
                }
            }
        });
    }

    private void cambiarTextoOrigen() {
        tvLocInfo.setText("Direccion de origen: " + direccionOrigen);
    }

    private void cambiarTextoDestino() {
        if (direccionDestino.equals("")) {
            tvLocInfo.setText("Mantenga precionado la direccion de destino");
        } else
            tvLocInfo.setText("Direccion de destino: " + direccionDestino);
    }

    private void removeRuta() {
        for (Polyline poli : polylines) {
            poli.remove();
        }
    }

    private String getdireccion(LatLng micoordenada) {
        String direccion = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> direcciones = geocoder.getFromLocation(micoordenada.latitude, micoordenada.longitude, 1);
            direccion = direcciones.get(0).getAddressLine(0);
            String[] direccionsplit = direccion.split(",");
            direccion = direccionsplit[0] + direccionsplit[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return direccion;
    }

    private float getdistancia() {
        float distancia = 0;
        if (listaMarker.size() == 2) {
            Location locationA = new Location("punto A");
            locationA.setLatitude(origen.getPosition().latitude);
            locationA.setLongitude(origen.getPosition().longitude);
            Location locationB = new Location("punto B");
            locationB.setLatitude(destino.getPosition().latitude);
            locationB.setLongitude(destino.getPosition().longitude);
            distancia = locationA.distanceTo(locationB);
            distancia = distancia / 1000;
        }
        return distancia;
    }

    private void cambiarmarcadores() {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origen.getPosition().latitude + "," + origen.getPosition().longitude + "&destination=" + destino.getPosition().latitude + "," + destino.getPosition().longitude + "&mode=driving&key=" + getString(R.string.google_maps_key);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                        polylines.add(map.addPolyline(new PolylineOptions().addAll(list).color(Color.YELLOW).width(35)));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener clickOrigen = new View.OnClickListener() {
        public void onClick(View v) {
            cambiarTextoOrigen();
            getOrigen.setSelected(true);
            getDestino.setSelected(false);
        }
    };
    private View.OnClickListener clickDestino = new View.OnClickListener() {
        public void onClick(View v) {
            cambiarTextoDestino();
            getOrigen.setSelected(false);
            getDestino.setSelected(true);
        }
    };
    private View.OnClickListener clickfabTaxi = new View.OnClickListener() {
        public void onClick(View v) {

            if (listaMarker.size() == 2) {
                //Crear bundle, que son los datos que pasaremos
                Bundle datosAEnviar = new Bundle();
                // Aquí pon todos los datos que quieras en formato clave, valor
                datosAEnviar.putString("origen", direccionOrigen);
                datosAEnviar.putString("destino", direccionDestino);
                datosAEnviar.putFloat("kilometros", getdistancia());
                datosAEnviar.putString("origenLatLong", origenLatLong());
                datosAEnviar.putString("destinoLatLong", destinoLatLong());
                datosAEnviar.putInt("id_prestador", id_prestador);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Cotizacion fragmento = new Cotizacion();
                fragmento.setArguments(datosAEnviar);
                fragmentTransaction.replace(R.id.contenedor, fragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("Debe seleccionar un destino")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }
                        );
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Informacion");
                titulo.show();
            }
        }
    };
    private GoogleMap.OnMapLongClickListener marcadordestino = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            if (listaMarker.size() < 2) {
                destino = new MarkerOptions().position(latLng).title("Destino").draggable(true);
                map.addMarker(destino);
                listaMarker.add(destino);
                cambiarmarcadores();
                removeRuta();
                direccionDestino = getdireccion(destino.getPosition());
                if (getDestino.isSelected()) {
                    cambiarTextoDestino();
                }
            } else {
                Toast.makeText(getContext(), "Solo se puede seleccionar un destino", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private GoogleMap.OnMarkerDragListener marcadordraggable = new GoogleMap.OnMarkerDragListener() {

        @Override
        public void onMarkerDragStart(Marker marker) {
        }

        @Override
        public void onMarkerDrag(Marker marker) {
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            if (marker.getId().equals("m1")) {
                destino = new MarkerOptions().anchor(0.0f, 0.1f).position(marker.getPosition()).title("Destino").draggable(true);
                direccionDestino = getdireccion(marker.getPosition());
                if (getDestino.isSelected()) {
                    cambiarTextoDestino();
                }
            } else {
                origen = new MarkerOptions().anchor(0.0f, 0.1f).position(marker.getPosition()).title("Origen").draggable(true);
                direccionOrigen = getdireccion(marker.getPosition());
                if (getOrigen.isSelected()) {
                    cambiarTextoOrigen();
                }
            }
            if (listaMarker.size() == 2) {
                cambiarmarcadores();
                removeRuta();
            }
        }
    };

}
