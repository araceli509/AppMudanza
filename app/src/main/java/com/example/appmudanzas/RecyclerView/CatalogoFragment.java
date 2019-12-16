package com.example.appmudanzas.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appmudanzas.R;

import java.util.ArrayList;
import java.util.List;


public class CatalogoFragment extends Fragment {
    private View vista;
    String[]items;
    private modeloCatalogo adaptador;
    private OnFragmentInteractionListener mListener;
    private boolean isFirstTime=true;
    private Spinner spinner;
    private ImageView imagenCatalogo;


    public CatalogoFragment() {
        // Required empty public constructor
    }

    public static CatalogoFragment newInstance(String param1, String param2) {
        CatalogoFragment fragment = new CatalogoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_catalogo, container, false);
        spinner=vista.findViewById(R.id.spinner);
        imagenCatalogo=vista.findViewById(R.id.imagenCatalogo);
        items=vista.getResources().getStringArray(R.array.Seleccione);
       ArrayAdapter<String>adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,items);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(isFirstTime){
                   isFirstTime=false;

               }else{
                   Toast.makeText(getContext(),items[position],Toast.LENGTH_SHORT).show();
                   Log.e("error",items[position]);

               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

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

public List<modeloCatalogo>obtenerCatalogo() {
    List<modeloCatalogo> catalogoi = new ArrayList<>();
    catalogoi.add(new modeloCatalogo(R.drawable.siilon));
    catalogoi.add(new modeloCatalogo(R.drawable.estufa));
    catalogoi.add(new modeloCatalogo(R.drawable.cama));
    catalogoi.add(new modeloCatalogo(R.drawable.refri1));
    catalogoi.add(new modeloCatalogo(R.drawable.mesa1));
return catalogoi;
}



    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
