package com.example.appmudanzas.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.appmudanzas.Login.LoginPrincipal;
import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.Solicitudes_Servicio;
import com.example.appmudanzas.prestador_Servicio.solicitud_preview;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityRecycler extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Solicitudes_Servicio.OnFragmentInteractionListener
, solicitud_preview.OnFragmentInteractionListener,PayPalFragment.OnFragmentInteractionListener{
    private String URL="";
    private TextView txtUsuario,txtCorreo;
    private ImageView imagenPerfilCliente;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);
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
}
