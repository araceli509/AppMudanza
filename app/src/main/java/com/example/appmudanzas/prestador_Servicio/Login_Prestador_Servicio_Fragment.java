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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login_Prestador_Servicio_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login_Prestador_Servicio_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_Prestador_Servicio_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btn_registrar_prestador,btn_reset_password;
    private View vista;
    private OnFragmentInteractionListener mListener;

    public Login_Prestador_Servicio_Fragment() {
    }

    public static Login_Prestador_Servicio_Fragment newInstance(String param1, String param2) {
        Login_Prestador_Servicio_Fragment fragment = new Login_Prestador_Servicio_Fragment();
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
        vista=inflater.inflate(R.layout.fragment_login__prestador__servicio_, container, false);
        crearComponentes();

        btn_registrar_prestador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro_Datos_Fragment registro_datos_fragment= new Registro_Datos_Fragment();
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.contenedor,registro_datos_fragment);
                fr.commit();
            }
        });

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reestablecer_Password_Fragment reestablecer_password_fragment= new Reestablecer_Password_Fragment();
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.contenedor,reestablecer_password_fragment).addToBackStack(null);
                fr.commit();
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

    private void crearComponentes(){
        btn_registrar_prestador=vista.findViewById(R.id.btn_registrar_prestador);
        btn_reset_password=vista.findViewById(R.id.btn_reset_password);
    }
}
