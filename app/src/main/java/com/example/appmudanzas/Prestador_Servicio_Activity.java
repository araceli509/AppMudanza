package com.example.appmudanzas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

public class Prestador_Servicio_Activity extends AppCompatActivity implements
        Login_Prestador_Servicio_Fragment.OnFragmentInteractionListener,
        Registro_Datos_Fragment.OnFragmentInteractionListener,
        Registro_Ine_Fragment.OnFragmentInteractionListener,Registro_Foto_Perfil_Fragment.OnFragmentInteractionListener,Registro_Licencia_Conducir_Fragment.OnFragmentInteractionListener,Registro_Tarjeta_Circulacion_Fragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestador__servicio_);
        if (ContextCompat.checkSelfPermission(Prestador_Servicio_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Prestador_Servicio_Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Prestador_Servicio_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        Login_Prestador_Servicio_Fragment fragment1= new Login_Prestador_Servicio_Fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor,fragment1).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}
