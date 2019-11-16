package com.example.appmudanzas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.appmudanzas.Cotizacion.ContentClient;
import com.example.appmudanzas.prestador_Servicio.Prestador_Servicio_Activity;
//araceli

public class MainActivity extends AppCompatActivity {
    private Button btn_prestador_servicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_prestador_servicio=findViewById(R.id.btn_prestador_servicio);
        btn_prestador_servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(getApplicationContext(), Prestador_Servicio_Activity.class);
                startActivity(i);
            }
        });
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
