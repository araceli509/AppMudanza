package com.example.appmudanzas;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

public class Prestador_Servicio_Activity extends AppCompatActivity implements
        Login_Prestador_Servicio_Fragment.OnFragmentInteractionListener,
        Registro_Datos_Fragment.OnFragmentInteractionListener,Registro_Ine_Fragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestador__servicio_);

        Login_Prestador_Servicio_Fragment fragment1= new Login_Prestador_Servicio_Fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor,fragment1).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
