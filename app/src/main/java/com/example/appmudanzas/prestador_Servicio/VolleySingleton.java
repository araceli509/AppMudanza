package com.example.appmudanzas.prestador_Servicio;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instanciaVolley;
    private RequestQueue requestQueue;
    private Context context;

    private VolleySingleton(Context c) {
        context=c;
        requestQueue= getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue==null){
            requestQueue=Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T>request){
        getRequestQueue().add(request);
    }

    public static synchronized VolleySingleton getInstanciaVolley(Context c){
        if(instanciaVolley==null){
            instanciaVolley= new VolleySingleton(c);
        }
        return instanciaVolley;
    }
}
