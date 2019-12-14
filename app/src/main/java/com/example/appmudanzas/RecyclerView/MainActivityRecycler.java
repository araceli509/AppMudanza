package com.example.appmudanzas.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.appmudanzas.Cotizacion.SolicitudPojo;
import com.example.appmudanzas.Login.LoginPrincipal;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.Prestador;
import com.example.appmudanzas.prestador_Servicio.Solicitudes_Servicio;
import com.example.appmudanzas.prestador_Servicio.VolleySingleton;
import com.example.appmudanzas.prestador_Servicio.solicitud_preview;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityRecycler extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Solicitudes_Servicio.OnFragmentInteractionListener
, solicitud_preview.OnFragmentInteractionListener,PayPalFragment.OnFragmentInteractionListener, Response.Listener<JSONObject>, Response.ErrorListener{
    private String URL="http://mudanzito.site/api/auth/cliente/busquedaprestador/";
    private TextView txtUsuario,txtCorreo;
    private ImageView imagenPerfilCliente;
    private FirebaseAuth mAuth;
    private String montoPago;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private int idPrestador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navegador_abrir_drawer, R.string.navegador_cerrar_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        txtUsuario=hView.findViewById(R.id.txtUsuarioNav);
        txtUsuario.setText(mAuth.getCurrentUser().getDisplayName());
        txtCorreo=hView.findViewById(R.id.txtCorreoNav);
        txtCorreo.setText(mAuth.getCurrentUser().getEmail());
        imagenPerfilCliente=hView.findViewById(R.id.imagePerfilCliente);
        imagenPerfilCliente.setMaxHeight(30);
        imagenPerfilCliente.setMaxWidth(30);
        Glide.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(imagenPerfilCliente);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new InicioFragment()).commit();
        mAuth=FirebaseAuth.getInstance();
        Toast.makeText(this,mAuth.getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
        URL=URL+mAuth.getCurrentUser().getEmail();
        Toast.makeText(this,URL,Toast.LENGTH_LONG).show();
        obtenerDatos();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_opciones, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opciones) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id==R.id.nav_inicio) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new InicioFragment()).commit();
        }
        else
        if (id==R.id.nav_galeria){
            fragmentManager.beginTransaction().replace(R.id.contenedor, new GaleriaFragment()).commit();
        }
        else
        if (id==R.id.nav_acercade){
            fragmentManager.beginTransaction().replace(R.id.contenedor, new AcercaDeFragment()).commit();
        }

            if (id==R.id.nav_cerrarsesion){
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                goMainScreen();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goMainScreen() {
    Intent intent= new Intent(this, LoginPrincipal.class);
    startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void obtenerDatos(){
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Prestador p=new Prestador();

        try {
            JSONArray json=response.getJSONArray("prestador");
            JSONObject jsonObject=null;
            jsonObject=json.getJSONObject(0);
            idPrestador=jsonObject.optInt("id_prestador");

            Toast.makeText(this,"id "+jsonObject.optInt("id_prestador"),Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
