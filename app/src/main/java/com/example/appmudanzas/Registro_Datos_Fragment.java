package com.example.appmudanzas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registro_Datos_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registro_Datos_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro_Datos_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View vista;
    private Button btn_registrar_datos_personales;
    private TextInputLayout inputNombre,inputCorreo,inputTelefono;
    private TextInputEditText txtNombre,txtCorreo,txtTelefono;
    private String nombre,correo,telefono;

    public Registro_Datos_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registro_Datos_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Registro_Datos_Fragment newInstance(String param1, String param2) {
        Registro_Datos_Fragment fragment = new Registro_Datos_Fragment();
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

        vista=inflater.inflate(R.layout.fragment_registro__datos_, container, false);

        crearComponentes();


        btn_registrar_datos_personales=vista.findViewById(R.id.btn_registrar_datos_personales);
        btn_registrar_datos_personales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(esNombreValido(txtNombre.getText().toString())){
                    Registro_Foto_Perfil_Fragment registro_foto_perfil_fragment= new Registro_Foto_Perfil_Fragment();
                    FragmentTransaction fr= getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor,registro_foto_perfil_fragment).addToBackStack(null);
                    fr.commit();
                }

            }
        });

        return vista;
    }

    public void crearComponentes(){
        inputNombre=vista.findViewById(R.id.prestador_nombre);
        txtNombre=vista.findViewById(R.id.txtNombre);
    }

    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            inputNombre.setError("Nombre inválido");
            return false;
        } else {
            inputNombre.setError(null);
        }

        return true;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
