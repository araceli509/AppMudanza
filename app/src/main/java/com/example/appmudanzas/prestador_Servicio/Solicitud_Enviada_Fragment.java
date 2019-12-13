package com.example.appmudanzas.prestador_Servicio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appmudanzas.R;

public class Solicitud_Enviada_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Button btnSolicitudEnviada;
    private View vista;
    public Solicitud_Enviada_Fragment() {
        // Required empty public constructor
    }

    public static Solicitud_Enviada_Fragment newInstance(String param1, String param2) {
        Solicitud_Enviada_Fragment fragment = new Solicitud_Enviada_Fragment();
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

        vista=inflater.inflate(R.layout.fragment_solicitud__enviada_, container, false);
        btnSolicitudEnviada=vista.findViewById(R.id.btn_finalizar);
        btnSolicitudEnviada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_Prestador_Servicio_Fragment l= new Login_Prestador_Servicio_Fragment();
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.contenedor,l).addToBackStack(null);
                fr.commit();
            }
        });
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
