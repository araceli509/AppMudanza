package com.example.appmudanzas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
//araceli

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        primercommitjulian();
    }

    protected void hola(){



    }
    protected void numero(){

    }
    protected void valida(){

    }

    public void primercommitjulian(){
        Log.e("Test","mensaje de prueba") ;
    }

    public void prueba(boolean araceli,int angel){
        System.out.println("hola");
        System.out.println("nuevo");
    }

}
