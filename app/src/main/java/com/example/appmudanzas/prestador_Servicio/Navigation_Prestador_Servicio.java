package com.example.appmudanzas.prestador_Servicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.navigation_prestador.FragmentSecundario;
import com.example.appmudanzas.prestador_Servicio.navigation_prestador.Fragment_Principal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class Navigation_Prestador_Servicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Solicitudes_Servicio.OnFragmentInteractionListener
        , solicitud_preview.OnFragmentInteractionListener,
        Fragment_Principal.OnFragmentInteractionListener,
        FragmentSecundario.OnFragmentInteractionListener,
        Login_Prestador_Servicio_Fragment.OnFragmentInteractionListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseDatabase db;
    private TextView txtPrestador,txtCorreoPrestador;
    private FirebaseAuth mAuth;
    CircleImageView imagenPerfilPrestador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__prestador__servicio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Una Accion", Snackbar.LENGTH_LONG).setAction("Accion", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navegador_abrir_drawer, R.string.navegador_cerrar_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        //txtPrestador=hView.findViewById(R.id.txtUsuarioNav);
        //txtPrestador.setText(mAuth.getCurrentUser().getDisplayName());
        txtCorreoPrestador=hView.findViewById(R.id.txtCorreoNav);
        txtCorreoPrestador.setText(mAuth.getCurrentUser().getEmail());
        //imagenPerfilPrestador=hView.findViewById(R.id.imagenPerfilPrestador);
        //imagenPerfilPrestador.setMaxHeight(30);
        //imagenPerfilPrestador.setMaxWidth(30);
        //Glide.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(imagenPerfilPrestador);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new Fragment_Principal()).commit();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();

        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id==R.id.nav_principal) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new Solicitudes_Servicio()).commit();

        }
        else
        if (id==R.id.nav_secundario){
            fragmentManager.beginTransaction().replace(R.id.contenedor, new FragmentSecundario()).commit();
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
}
