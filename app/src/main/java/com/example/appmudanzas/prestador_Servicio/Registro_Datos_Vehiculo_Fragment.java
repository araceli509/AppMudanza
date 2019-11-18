package com.example.appmudanzas.prestador_Servicio;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appmudanzas.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Registro_Datos_Vehiculo_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String modelo,placas,capacidad_carga;
    private Button btn_registrar_datos_vehiculo;
    private TextInputLayout inputModelo,inputPlacas,inputCapacidadCarga;
    private TextInputEditText txtModelo,txtPlacas,txtCapacidadCarga;
    private OnFragmentInteractionListener mListener;
    private View vista;
    public Registro_Datos_Vehiculo_Fragment() {

    }

    public static Registro_Datos_Vehiculo_Fragment newInstance(String param1, String param2) {
        Registro_Datos_Vehiculo_Fragment fragment = new Registro_Datos_Vehiculo_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_registro__datos__vehiculo_, container, false);
        crearComponentes();

        btn_registrar_datos_vehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarModelo()|!validarPlacas()|!validarCapacidadCarga()){
                    return;
                }else{
                    Bundle datos= new Bundle();
                    datos.putString("modelo",modelo);
                    datos.putString("placas",placas);
                    datos.putString("capacidad_carga",capacidad_carga);
                    Foto_Frontal_Vehiculo_Fragment foto_frontal_vehiculo_fragment= new Foto_Frontal_Vehiculo_Fragment();
                    foto_frontal_vehiculo_fragment.setArguments(datos);

                    FragmentTransaction fr= getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor,foto_frontal_vehiculo_fragment).addToBackStack(null);
                    fr.commit();
                }
            }
        });

        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void crearComponentes(){
        btn_registrar_datos_vehiculo=vista.findViewById(R.id.btn_registrar_datos_vehiculo);
        inputCapacidadCarga=vista.findViewById(R.id.vehiculo_capacidad_carga);
        inputModelo=vista.findViewById(R.id.vehiculo_modelo);
        inputPlacas=vista.findViewById(R.id.vehiculo_placas);
        txtCapacidadCarga=vista.findViewById(R.id.txtCapacidadCarga);
        txtModelo=vista.findViewById(R.id.txtModelo);
        txtPlacas=vista.findViewById(R.id.txtPlacas);
    }

    private boolean validarPlacas(){
        placas=inputPlacas.getEditText().getText().toString().trim();
        Pattern patron=Pattern.compile("^[A-Z]{3}[-]{1}[0-9]{3}$");
        if(!patron.matcher(placas).matches()){
            inputPlacas.setError("Placa Invalida");
            return false;
        }else if(placas.isEmpty()){
            inputPlacas.setError("El campo no puede estar vacio");
            return false;
        }else{
            inputPlacas.setError(null);
            return true;
        }
    }

    private boolean validarModelo(){
        modelo=inputModelo.getEditText().getText().toString().trim();
        if(modelo.isEmpty()){
            inputModelo.setError("El campo no puede estar vacio");
            return false;
        }else{
            inputModelo.setError(null);
            return true;
        }
    }

    private boolean validarCapacidadCarga(){
        capacidad_carga=inputPlacas.getEditText().getText().toString();
        if(capacidad_carga.isEmpty()){
            inputCapacidadCarga.setError("El campo no puede estar vacio");
            return false;
        }else if(Double.parseDouble(capacidad_carga)>10||Double.parseDouble(capacidad_carga)<.5){
            inputCapacidadCarga.setError("Capacidad de carga invalida");
            return false;
        }else{
            inputCapacidadCarga.setError(null);
            return true;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
