package com.example.appmudanzas.RecycleView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.appmudanzas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivityRecycle extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainrecycler);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Una Accion",Snackbar.LENGTH_LONG).setAction("Accion",null).show();
            }
        });

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navegador_abrir_drawer,R.string.navegador_cerrar_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,new InicioFragment()).commit();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_opciones,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id==R.id.opciones) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
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
                if (id==R.id.nav_acercade) {
                    fragmentManager.beginTransaction().replace(R.id.contenedor, new AcercadeFragment()).commit();
                }
                else
                    if (id==R.id.nav_dardealataconlaapp) {
                        fragmentManager.beginTransaction().replace(R.id.contenedor, new DardealtaappFragment()).commit();
                    }
                    else
                        if (id==R.id.nav_mismudanzas) {
                            fragmentManager.beginTransaction().replace(R.id.contenedor, new MismudanzasFragment()).commit();
                        }
                        else
                            if (id==R.id.nav_solicitudes) {
                                fragmentManager.beginTransaction().replace(R.id.contenedor, new SolicitudesFragment()).commit();
                            }

         DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
