package com.example.appmudanzas.prestador_Servicio;

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
import com.example.appmudanzas.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Registro_Datos_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View vista;
    private Button btn_registrar_datos_personales;
    private TextInputLayout inputNombre,inputApellidos,inputCorreo,inputTelefono,inputPassword,inputDireccion,inputCodigoPostal;
    private TextInputEditText txtNombre,txtApellidos,txtDireccion,txtCorreo,txtPassword,txtTelefono,txtCodigoPodtal;
    private String nombre,apellidos,correo,password,direccion,telefono,codigoPostal;

    public Registro_Datos_Fragment() {
        // Required empty public constructor
    }

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
                if(!validarNombre()|!validarApellidos()|!validarDireccion()|!validarCodigoPostal()|!validarEmail()|!validarPassword()|!validarTelefono()){
                    return;
                }else {
                    Bundle datos = new Bundle();
                    datos.putString("nombre", nombre);
                    datos.putString("apellidos", apellidos);
                    datos.putString("direccion", direccion);
                    datos.putString("telefono", telefono);
                    datos.putString("correo", correo);
                    datos.putString("password", password);
                    datos.putString("codigo_postal",codigoPostal);
                    Registro_Foto_Perfil_Fragment registro_foto_perfil_fragment = new Registro_Foto_Perfil_Fragment();
                    registro_foto_perfil_fragment.setArguments(datos);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, registro_foto_perfil_fragment).addToBackStack(null);
                    fr.commit();
                    //}
                }
            }
        });

        return vista;
    }

    public void crearComponentes(){
        inputNombre=vista.findViewById(R.id.prestador_nombre);
        inputApellidos=vista.findViewById(R.id.prestador_apellidos);
        inputCorreo=vista.findViewById(R.id.prestador_email);
        inputDireccion=vista.findViewById(R.id.prestador_direccion);
        inputTelefono=vista.findViewById(R.id.prestador_telefono);
        inputPassword=vista.findViewById(R.id.prestador_password);
        inputCodigoPostal=vista.findViewById(R.id.prestador_codigo_postal);
        txtNombre=vista.findViewById(R.id.txtNombre);
        txtApellidos=vista.findViewById(R.id.txtApellidos);
        txtDireccion=vista.findViewById(R.id.txtDireccion);
        txtCorreo=vista.findViewById(R.id.txtEmail);
        txtPassword=vista.findViewById(R.id.txtPassword);
        txtCodigoPodtal=vista.findViewById(R.id.txtCodigoPostal);
        txtTelefono=vista.findViewById(R.id.txtTelefono);
    }

    private boolean validarNombre() {
        nombre=inputNombre.getEditText().getText().toString();
        Pattern patron = Pattern.compile("^[A-Za-záéíóúÁÉÍÓÚ]*\\s()[A-Za-záéíóúÁÉÍÓÚ]*|^[A-Za-záéíóúÁÉÍÓÚ]*$");
        if (!patron.matcher(nombre).matches()) {
            inputNombre.setError("Nombre inválido");
            return false;
        }else if(nombre.isEmpty()){
            inputNombre.setError("El campo no puede estar vacío");
            return false;
        } else {
            inputNombre.setError(null);
            return true;
        }
    }

    private boolean validarApellidos(){
        apellidos=inputApellidos.getEditText().getText().toString().trim();
        Pattern patron = Pattern.compile("^[A-Za-záéíóúÁÉÍÓÚ]*\\s()[A-Za-záéíóúÁÉÍÓÚ]*|^[A-Za-záéíóúÁÉÍÓÚ]*$");
        if (!patron.matcher(apellidos).matches()) {
            inputApellidos.setError("Apellido inválido");
            return false;
        }else if(apellidos.isEmpty()){
            inputApellidos.setError("El campo no puede estar vacío");
            return false;
        } else {
            inputApellidos.setError(null);
            return true;
        }
    }

    private boolean validarEmail(){
        correo=inputCorreo.getEditText().getText().toString().trim();
        if(correo.isEmpty()){
            inputCorreo.setError("El campo no puede estar vacío");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            inputCorreo.setError("Correo Invalido");
            return false;
        }else {
            inputCorreo.setError(null);
            return true;
        }
    }

    private boolean validarPassword(){
        password=inputPassword.getEditText().getText().toString();
        if(password.isEmpty()){
            inputPassword.setError("El campo no puede estar vacío");
            return false;
        }else if(password.length()<6){
            inputPassword.setError("La contraseña debe tener al menos 6 caracteres");
            return false;
        }else if(password.length()>20){
            inputPassword.setError("La contraseña no puede ser mayor a 20 caracteres");
            return false;
        }else {
            inputPassword.setError(null);
            return true;
        }
    }

    private boolean validarDireccion(){
        direccion=inputDireccion.getEditText().getText().toString();
        if(direccion.isEmpty()){
            inputDireccion.setError("El campo no puede estar vacio");
            return false;
        }else {
            inputDireccion.setError(null);
            return true;
        }
    }

    private boolean validarTelefono(){
        telefono=inputTelefono.getEditText().getText().toString().trim();
        if(telefono.isEmpty()){
            inputTelefono.setError("El campo no puede quedar vacio");
            return false;
        }else if(!Patterns.PHONE.matcher(telefono).matches()||telefono.length()!=10){
            inputTelefono.setError("El numero telefonico es invalido");
            return false;
        }else {
            inputTelefono.setError(null);
            return true;
        }
    }

    private boolean validarCodigoPostal(){
        codigoPostal=inputCodigoPostal.getEditText().getText().toString().trim();
        if(codigoPostal.isEmpty()){
            inputCodigoPostal.setError("El campo no puede estar vacio");
            return false;
        }else if(codigoPostal.length()!=5){
            inputCodigoPostal.setError("El codigo postal es invalido");
            return false;
        }else{
            inputCodigoPostal.setError(null);
            return true;
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
