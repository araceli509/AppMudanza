package com.example.appmudanzas.prestador_Servicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appmudanzas.R;
import com.example.appmudanzas.RecyclerView.ServiciosExtraFragment;
import com.example.appmudanzas.prestador_Servicio.mudanza.MudanzaEspera;
import com.example.appmudanzas.prestador_Servicio.mudanza.MudanzaRealizada;
import com.example.appmudanzas.prestador_Servicio.mudanza.mudanzasTabs;
import com.example.appmudanzas.prestador_Servicio.mudanza.mudanzas_tab;
import com.example.appmudanzas.prestador_Servicio.navigation_prestador.FragmentSecundario;
import com.example.appmudanzas.prestador_Servicio.navigation_prestador.Fragment_Principal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class Navigation_Prestador_Servicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ServiciosExtraFragment.OnFragmentInteractionListener,
        Solicitudes_Servicio.OnFragmentInteractionListener,
        MudanzaEspera.OnFragmentInteractionListener
        ,solicitud_preview.OnFragmentInteractionListener,
        Fragment_Principal.OnFragmentInteractionListener,
        FragmentSecundario.OnFragmentInteractionListener,
        Login_Prestador_Servicio_Fragment.OnFragmentInteractionListener,
        Response.Listener<JSONObject>,
        Response.ErrorListener,mudanzasTabs.OnFragmentInteractionListener,
        MudanzaRealizada.OnFragmentInteractionListener,
        mudanzas_tab.OnFragmentInteractionListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseDatabase db;
    private TextView txtPrestador,txtCorreoPrestador;
    private FirebaseAuth mAuth;
    CircleImageView imagenPerfilPrestador;
    int idPrestador;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__prestador__servicio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navegador_abrir_drawer, R.string.navegador_cerrar_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        //txtPrestador=hView.findViewById(R.id.txtUsuarioNav);
        //txtPrestador.setText(mAuth.getCurrentUser().getDisplayName());
        txtCorreoPrestador = hView.findViewById(R.id.txtCorreoNav);
        txtCorreoPrestador.setText(mAuth.getCurrentUser().getEmail());
        //imagenPerfilPrestador=hView.findViewById(R.id.imagenPerfilPrestador);
        //imagenPerfilPrestador.setMaxHeight(30);
        //imagenPerfilPrestador.setMaxWidth(30);
        //Glide.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(imagenPerfilPrestador);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment_Principal()).commit();
        navigationView.setNavigationItemSelectedListener(this);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

    //traer el id del prestador actual
        cargarDatos();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();

        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id==R.id.nav_principal) {
            Bundle bundle = new Bundle();
            bundle.putInt("idprestador", idPrestador);

            Fragment solicitudes_servicio = new Solicitudes_Servicio();
            solicitudes_servicio.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.contenedor, solicitudes_servicio).commit();

        }
        else
        if (id==R.id.nav_secundario){
            fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentSecundario()).commit();
        }else if(id==R.id.nav_Serviciosextra){
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ServiciosExtraFragment()).commit();

        }else if(id==R.id.nav_mudanzas){
            Bundle datos= new Bundle();
            datos.putInt("id_prestador",idPrestador);
            Fragment mudanzas=new mudanzas_tab();
            mudanzas.setArguments(datos);
            fragmentManager.beginTransaction().replace(R.id.contenedor,mudanzas).commit();
        }

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        if (id == R.id.item_cerrar_sesion) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onFragmentInteraction(Uri uri) {

    }

    private void cargarDatos(){
        boolean conexion=compruebaConexion(getApplicationContext());
        if(conexion) {
            FirebaseUser user = mAuth.getCurrentUser();
            String correo=user.getEmail();
            String url = "http://mudanzito.site/api/auth/cliente/busquedaprestador/" +correo;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            requestQueue.add(jsonObjectRequest);
        }else{

            Toast.makeText(getApplicationContext(),"Revise su conexion a internet",Toast.LENGTH_LONG).show();
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
            JSONArray jsonArray = response.getJSONArray("prestador");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            idPrestador= jsonObject.getInt("id_prestador");
            Toast.makeText(getApplicationContext(),String.valueOf(idPrestador),Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
