package com.example.appmudanzas.prestador_Servicio.mudanza;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.example.appmudanzas.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    DatabaseReference driver;
    Marker miMarcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_prestador);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

            String sLatitud = String.valueOf(loc.getLatitude());
            String sLongitud = String.valueOf(loc.getLongitude());
            MiUbicacion = new LatLng(Double.parseDouble(sLatitud), Double.parseDouble(sLongitud));

            mkOp = new MarkerOptions()
                    .visible(true)
                    .position(MiUbicacion).title("Tú");

            miMarcador = mMap.addMarker(mkOp);
            geoFire.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(), new GeoLocation(Double.parseDouble(sLatitud), Double.parseDouble(sLongitud)), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if (miMarcador != null) {
                        miMarcador.remove();
                        miMarcador = mMap.addMarker(mkOp);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MiUbicacion, 15.0f));

                    }

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


}
