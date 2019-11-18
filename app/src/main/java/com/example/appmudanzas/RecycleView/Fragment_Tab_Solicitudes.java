package com.example.appmudanzas.RecycleView;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmudanzas.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Tab_Solicitudes extends Fragment {


    private RecyclerView RecyclerViewSolicitudes;
    private RecyclerViewSolicitudes adaptadorSolicitudes;
    private LinearLayoutManager layoutManager;
    private ModeloSolicitudes adaptador;

    public Fragment_Tab_Solicitudes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab_solicitudes, container, false);

        RecyclerViewSolicitudes=(RecyclerView)view.findViewById(R.id.RecyclerColgadores);
        layoutManager= new LinearLayoutManager(getActivity());
        RecyclerViewSolicitudes.setLayoutManager(layoutManager);

        adaptadorSolicitudes=new RecyclerViewSolicitudes(obtenercolgadores());
        RecyclerViewSolicitudes.setAdapter(adaptadorSolicitudes);

        return view;
    }

    public List<ModeloSolicitudes> obtenercolgadores() {
        List<ModeloSolicitudes> Solicitudes=new ArrayList<>();
        Solicitudes.add(new ModeloSolicitudes(R.drawable.l_uno));
        Solicitudes.add(new ModeloSolicitudes(R.drawable.l_dos));
        Solicitudes.add(new ModeloSolicitudes(R.drawable.l_tres));
        Solicitudes.add(new ModeloSolicitudes(R.drawable.l_cuatro));
        Solicitudes.add(new ModeloSolicitudes(R.drawable.l_cinco));

        return Solicitudes;
    }

}
