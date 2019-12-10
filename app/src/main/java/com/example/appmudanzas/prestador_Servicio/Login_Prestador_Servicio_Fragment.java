package com.example.appmudanzas.prestador_Servicio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appmudanzas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login_Prestador_Servicio_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login_Prestador_Servicio_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_Prestador_Servicio_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;
    private String mParam1;
    private String mParam2;
    private String email,password;
    private TextInputLayout inputCorreo,inputPassword;
    private Button btn_login_prestador,btn_registrar_prestador,btn_reset_password;
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
        mAuth=FirebaseAuth.getInstance();
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

        btn_login_prestador.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                email=inputCorreo.getEditText().getText().toString().trim();
                password=inputPassword.getEditText().getText().toString().trim();
                if(!email.isEmpty()&&!password.isEmpty()){
                    loginUser();
                }

            }
        });
        return vista;
    }

    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i= new Intent(getActivity(),Navigation_Prestador_Servicio.class);
                    startActivity(i);
                    //getActivity().finish();
                }else {
                    Toast.makeText(getContext(), "Usuario o contrañsea incorrectos ", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        btn_login_prestador=vista.findViewById(R.id.btn_login_prestador);
        btn_registrar_prestador=vista.findViewById(R.id.btn_registrar_prestador);
        btn_reset_password=vista.findViewById(R.id.btn_reset_password);
        inputCorreo=vista.findViewById(R.id.usuario_prestador);
        inputPassword=vista.findViewById(R.id.password_prestador);
    }
}
