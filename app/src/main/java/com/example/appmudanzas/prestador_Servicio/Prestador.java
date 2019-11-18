package com.example.appmudanzas.prestador_Servicio;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Prestador {
    private RequestQueue queue;
    private String URL="http://192.168.1.79:80/api/auth/prestador_servicio/ultimo";
    JsonObjectRequest request;
    private String id_prestador;
    private int i=0;

    public Prestador(Context context){
        queue= Volley.newRequestQueue(context);
    }

    public String ultimoPrestadorRegistrado(){
        String URL="http://192.168.1.79:80/api/auth/prestador_servicio/ultimo";
        JsonObjectRequest request;
        request= new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray mJsonArray = response.getJSONArray("Prestador");
                    for(i=0; i<mJsonArray.length(); i++){

                    }
                    JSONObject mjJsonObject =mJsonArray.getJSONObject(1);
                    id_prestador =String.valueOf(mjJsonObject.getInt("id_prestador"));

                }catch (JSONException j){
                    System.out.println(j.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(request);
        return id_prestador;

    }
}
