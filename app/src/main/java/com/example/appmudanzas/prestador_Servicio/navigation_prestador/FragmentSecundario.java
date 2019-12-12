package com.example.appmudanzas.prestador_Servicio.navigation_prestador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.Login_Prestador_Servicio_Fragment;
import com.example.appmudanzas.prestador_Servicio.Prestador_Servicio_Activity;
import com.google.firebase.auth.FirebaseAuth;


public class FragmentSecundario extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private View vista;
    private Button btnCerrarSesionPrestador;
    private FirebaseAuth mAuth;
    public FragmentSecundario() {

    }

    public static FragmentSecundario newInstance(String param1, String param2) {
        FragmentSecundario fragment = new FragmentSecundario();
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
       vista=inflater.inflate(R.layout.fragment_fragment_secundario, container, false);
       btnCerrarSesionPrestador=vista.findViewById(R.id.btnCerrarSesionPrestador);
       mAuth=FirebaseAuth.getInstance();
        Toast.makeText(getContext(),""+mAuth,Toast.LENGTH_LONG).show();
       btnCerrarSesionPrestador.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signOut();
                Intent i= new Intent(getActivity(), Prestador_Servicio_Activity.class);
                startActivity(i);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
