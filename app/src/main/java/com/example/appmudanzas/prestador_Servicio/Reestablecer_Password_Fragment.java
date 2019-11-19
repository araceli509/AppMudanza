package com.example.appmudanzas.prestador_Servicio;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appmudanzas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class Reestablecer_Password_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Button btn_reset_password;
    private TextInputLayout inputEmail;
    private View vista;
    private String correo;
    private FirebaseAuth mAuth;
    private ProgressDialog progreso;
    public Reestablecer_Password_Fragment() {
        // Required empty public constructor
    }

    public static Reestablecer_Password_Fragment newInstance(String param1, String param2) {
        Reestablecer_Password_Fragment fragment = new Reestablecer_Password_Fragment();
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
        vista=inflater.inflate(R.layout.fragment_reestablecer__password_, container, false);
        crearComponentes();
        mAuth=FirebaseAuth.getInstance();

       btn_reset_password.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!validarEmail()){
                   return;
               }else{
                        resetPassword();
                        Login_Prestador_Servicio_Fragment l= new Login_Prestador_Servicio_Fragment();
                       FragmentTransaction fr= getFragmentManager().beginTransaction();
                       fr.replace(R.id.contenedor,l).addToBackStack(null);
                       fr.commit();
                   progreso.dismiss();
               }
           }
       });

        return vista;
    }

    private void resetPassword() {
        if(Conexion_Internet.compruebaConexion(getContext())){
            progreso= new ProgressDialog(getContext());
            progreso.setMessage("Enviando");
            progreso.show();
            mAuth.setLanguageCode("es");
            mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(),"Se ha enviado un correo para reestablecer tu contraseña",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getContext(),"No se pudo enviar el correo",Toast.LENGTH_SHORT).show();
                    }
                    progreso.dismiss();
                }
            });
        }

    }

    private void crearComponentes() {
        btn_reset_password=vista.findViewById(R.id.btn_reset_password);
        inputEmail=vista.findViewById(R.id.prestador_email);
    }

    private boolean validarEmail(){
        correo=inputEmail.getEditText().getText().toString().trim();
        if(correo.isEmpty()){
            inputEmail.setError("El campo no puede estar vacío");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            inputEmail.setError("Correo Invalido");
            return false;
        }else {
            inputEmail.setError(null);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
