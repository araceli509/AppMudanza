package com.example.appmudanzas.prestador_Servicio;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.appmudanzas.R;
import com.example.appmudanzas.prestador_Servicio.solicitudes.reservacion;
import com.example.appmudanzas.prestador_Servicio.solicitudes.cliente;


public class solicitud_preview extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public solicitud_preview() {
        // Required empty public constructor
    }
    /*
    * variables
    *
    *
    * */
    private TextView idsolicitud,cliente,numero,correo;

    private TextView pago,origencalles,destinocalles,pisos ,estatus,fecha,km;

    private ImageView eliminar,rutamaps,aceptar;

    private reservacion  reservacion;
    private cliente cliente_datos;


    // TODO: Rename and change types and number of parameters
    public static solicitud_preview newInstance(String param1, String param2) {
        solicitud_preview fragment = new solicitud_preview();
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

            reservacion= (reservacion) getArguments().getSerializable("reservacion");
            cliente_datos= reservacion.getCliente();


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_solicitud_preview, container, false);

        idsolicitud= view.findViewById(R.id.id_solicitud);
        cliente=view.findViewById(R.id.cliente);
        numero=view.findViewById(R.id.numero);
        correo=view.findViewById(R.id.correo);

         pago=view.findViewById(R.id.pago);
         origencalles=view.findViewById(R.id.origencalles);
         destinocalles=view.findViewById(R.id.destinocalles);
         pisos=view.findViewById(R.id.pisos);
         estatus=view.findViewById(R.id.estatus);
         fecha=view.findViewById(R.id.fecha);
         km=view.findViewById(R.id.km);

         eliminar=view.findViewById(R.id.eliminar);
         rutamaps=view.findViewById(R.id.rutamaps);
         aceptar=view.findViewById(R.id.aceptar);

         if(cliente_datos!=null&reservacion!=null){
             Log.e("id_reservacion",String.valueOf(reservacion.getId_reservacion()));
             idsolicitud.setText(reservacion.getId_reservacion());
             cliente.setText(cliente_datos.getNombre()+" "+cliente_datos.getApellidos());
             numero.setText(cliente_datos.getTelefono());
         }

        return view;
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
