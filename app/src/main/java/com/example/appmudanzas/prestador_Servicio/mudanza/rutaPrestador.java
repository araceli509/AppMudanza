package com.example.appmudanzas.prestador_Servicio.mudanza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.appmudanzas.prestador_Servicio.Utilidades;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.example.appmudanzas.prestador_Servicio.mudanza.MudanzaAcitvaCliente.compruebaConexion;

public class rutaPrestador extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //Variables

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
    RequestQueue request;
    String origen,destino;
    Localizacion Local;
    Button comenzar;
    DatabaseReference seguimientos;
    int id_cliente,id_prestador,uid_prestador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_prestador);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        origen=getIntent().getStringExtra("origen");
        destino=getIntent().getStringExtra("destino");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        origen=getIntent().getStringExtra("origen");
        destino=getIntent().getStringExtra("destino");
        id_cliente=getIntent().getIntExtra("id_cliente",1);
        id_prestador=getIntent().getIntExtra("id_prestador",1);
        request = Volley.newRequestQueue(getApplicationContext());
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        drivers = db.getReference("Drivers");
        geoFire = new GeoFire(drivers);

        webServiceObtenerRuta(origen,destino);
        String datos[]= origen.split(",");
        double Lat= Double.parseDouble(datos[0]);
        double Long= Double.parseDouble(datos[1]);
        LatLng origin = new LatLng(Lat, Long);
        mMap.addMarker(new MarkerOptions().position(origin).title("Ubicacion de la mudanza"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
        comenzar = (Button) findViewById(R.id.continuar);
        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationStart();
                insertaruid();
                comenzar.setVisibility(View.INVISIBLE);
            }
        });

        Handler handlerr = new Handler();
        handlerr.postDelayed(new Runnable() {
            public void run() {

                // Esto ejecuta el metodo trazarRuta()
                trazarRuta();

            }
        }, 1500);
        handlerr.removeCallbacks(null);

    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();

        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);






    }

    public void terminarmudanza(){
        Local=null;
    }

    public class Localizacion implements LocationListener {
        rutaPrestador mainActivity;

        public rutaPrestador getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(rutaPrestador mainActivity) {
            this.mainActivity = mainActivity;
        }


        @Override
        public void onLocationChanged(Location loc) {

            latitud = loc.getLatitude();
            longitud = loc.getLongitude();


            MiUbicacion = new LatLng(latitud,longitud);

            mkOp = new MarkerOptions()
                    .visible(true)
                    .position(MiUbicacion).title("Tú");

            miMarcador = mMap.addMarker(mkOp);

            geoFire.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(), new GeoLocation(latitud, longitud), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if (miMarcador != null) {
                        miMarcador.setPosition(new LatLng(latitud,longitud));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MiUbicacion, 15.0f));

                    }
                    mkOp=null;
                    miMarcador.remove();
                    MiUbicacion=null;



                }
            });

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


    private void insertaruid(){
        if (compruebaConexion(getApplicationContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mudanzito.site/api/auth/mudanzas/insertarubicacion",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Algo salio mal"+ error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<>();
                    params.put("id_prestador", String.valueOf(id_prestador));
                    params.put("id_cliente", String.valueOf(id_cliente));
                    params.put("uid_prestador", auth.getCurrentUser().getUid());
                    return params;
                }
            };
            VolleySingleton.getInstanciaVolley(getApplication()).addToRequestQueue(stringRequest);

        } else {


        }
    }


    private void webServiceObtenerRuta(String origenLatLong, String destinoLatLong) {
        String url="";

        url = "https://maps.googleapis.com/maps/api/directions/json?mode=driving&transit_routing_preference=less_driving&origin=" + origenLatLong
                + "&destination=" +destinoLatLong+ "&key=AIzaSyALOMtroZJRkGj_iKu4rjbCvQ6UPKwfvh4";
        Log.d("url",url);
        Log.d("RUTA:", url);


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Este método PARSEA el JSONObject que retorna del API de Rutas de Google devolviendo
                //una lista del lista de HashMap Strings con el listado de Coordenadas de Lat y Long,
                //con la cual se podrá dibujar pollinas que describan la ruta entre 2 puntos.


                JSONArray jRoutes = null;
                JSONArray jLegs = null;
                JSONArray jSteps = null;

                try {

                    jRoutes = response.getJSONArray("routes");

                    /** Traversing all routes */
                    for (int i = 0; i < jRoutes.length(); i++) {
                        jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");

                        List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                        /** Traversing all legs */
                        for (int j = 0; j < jLegs.length(); j++) {

                            JSONObject distancia= (JSONObject)(((JSONObject) jLegs.get(j)).get("distance"));
                            String dis= distancia.getString("value");


                            jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                            /** Traversing all steps */
                            for (int k = 0; k < jSteps.length(); k++) {
                                String polyline = "";
                                polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                                List<LatLng> list = decodePoly(polyline);

                                /** Traversing all points */
                                for (int l = 0; l < list.size(); l++) {
                                    HashMap<String, String> hm = new HashMap<String, String>();
                                    hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                                    hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                                    path.add(hm);
                                }
                            }
                            Utilidades.routes.add(path);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString());
            }
        }
        );

        request.add(jsonObjectRequest);
    }



    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
        //Este método PARSEA el JSONObject que retorna del API de Rutas de Google devolviendo
        //una lista del lista de HashMap Strings con el listado de Coordenadas de Lat y Long,
        //con la cual se podrá dibujar pollinas que describan la ruta entre 2 puntos.
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;
        String distancia=null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");


                List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {
                    JSONObject distanceOb= (JSONObject) ((JSONObject) jLegs.get(j)).get("distance");
                    distancia= distanceOb.getString("value");
                    Log.d("LA DISTANCIA ES:",distancia);



                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                            hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    Utilidades.routes.add(path);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }



        return Utilidades.routes;
    }
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public void trazarRuta() {

        /////////////
        LatLng center = null;
        LatLng position = null;
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;

        MarkerOptions markerOptions = new MarkerOptions();


        // setUpMapIfNeeded();


        // recorriendo todas las rutas
        for (int i = 0; i < Utilidades.routes.size(); i++) {
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Obteniendo el detalle de la ruta
            List<HashMap<String, String>> path = Utilidades.routes.get(i);

            // Obteniendo todos los puntos y/o coordenadas de la ruta
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                position = new LatLng(lat, lng);
                if (center == null) {
                    //Obtengo la 1ra coordenada para centrar el mapa en la misma.
                    center = new LatLng(lat, lng);
                }
                points.add(position);
            }


            // Agregamos todos los puntos en la ruta al objeto LineOptions
            lineOptions.addAll(points);
            //Definimos el grosor de las Polilíneas
            lineOptions.width(5);
            //Definimos el color de la Polilíneas
            lineOptions.color(Color.BLUE);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        }


        PolylineOptions finalLineOptions = lineOptions;
        LatLng finalCenter = center;

        // Dibujamos las Polilineas en el Google Map para cada ruta
        mMap.addPolyline(finalLineOptions);


    }



}
