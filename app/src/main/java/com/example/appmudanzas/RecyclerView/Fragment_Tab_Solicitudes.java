package com.example.appmudanzas.RecyclerView;


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


    private RecyclerView RecyclerViewColgadores;
    private RecyclerViewSolicitudes adaptadorColgadores;
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

        RecyclerViewColgadores=(RecyclerView)view.findViewById(R.id.RecyclerColgadores);
        layoutManager= new LinearLayoutManager(getActivity());
        RecyclerViewColgadores.setLayoutManager(layoutManager);

        adaptadorColgadores=new RecyclerViewSolicitudes(obtenercolgadores());
        RecyclerViewColgadores.setAdapter(adaptadorColgadores);

        return view;
    }

    public List<ModeloSolicitudes> obtenercolgadores() {
        List<ModeloSolicitudes> colgadores=new ArrayList<>();
        colgadores.add(new ModeloSolicitudes(R.drawable.l_uno));
        colgadores.add(new ModeloSolicitudes(R.drawable.l_dos));
        colgadores.add(new ModeloSolicitudes(R.drawable.l_tres));
        colgadores.add(new ModeloSolicitudes(R.drawable.l_cuatro));
        colgadores.add(new ModeloSolicitudes(R.drawable.l_cinco));

        return colgadores;
    }

}
