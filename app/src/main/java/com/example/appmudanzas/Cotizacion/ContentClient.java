package com.example.appmudanzas.Cotizacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;

import com.example.appmudanzas.R;

public class ContentClient extends AppCompatActivity implements MapsClienteFragment.OnFragmentInteractionListener,Cotizacion.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_client);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapsClienteFragment fragmento = new MapsClienteFragment();
        fragmentTransaction.add(R.id.contenedorcliente, fragmento);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
