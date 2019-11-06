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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmudanzas.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Hashtable;
import java.util.Map;
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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View vista;
    private Button btn_registrar_datos_personales;
    private TextInputLayout inputNombre,inputCorreo,inputTelefono;
    private TextInputEditText txtNombre,txtApellidos,txtDireccion,txtCorreo,txtPassword,txtTelefono;
    private String nombre,apellidos,correo,password,direccion,telefono;

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
                    obtenerDatos();
                    Bundle datos= new Bundle();
                    datos.putString("nombre",nombre);
                    datos.putString("apellidos",apellidos);
                    datos.putString("direccion",direccion);
                    datos.putString("telefono",telefono);
                    datos.putString("correo",correo);
                    datos.putString("password",password);
                    Registro_Foto_Perfil_Fragment registro_foto_perfil_fragment= new Registro_Foto_Perfil_Fragment();
                    registro_foto_perfil_fragment.setArguments(datos);

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
        txtApellidos=vista.findViewById(R.id.txtApellidos);
        txtDireccion=vista.findViewById(R.id.txtDireccion);
        txtCorreo=vista.findViewById(R.id.txtEmail);
        txtPassword=vista.findViewById(R.id.txtPassword);
        txtTelefono=vista.findViewById(R.id.txtTelefono);
    }

    public void obtenerDatos(){
        nombre=txtNombre.getText().toString();
        apellidos=txtApellidos.getText().toString();
        direccion=txtDireccion.getText().toString();
        correo=txtCorreo.getText().toString();
        password=txtPassword.getText().toString();
        telefono=txtTelefono.getText().toString();
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
