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


public class Fragment_Tab_Mismudanzas extends Fragment {


    private RecyclerView RecyclerViewMismudanzas;
    private RecyclerViewMismudanzas adaptadorMismudanzas;
    private LinearLayoutManager layoutManager;
    private ModeloMismudanzas adaptador;

    public Fragment_Tab_Mismudanzas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab_mismudanzas, container, false);

        RecyclerViewMismudanzas=(RecyclerView)view.findViewById(R.id.RecyclerCamas);
        layoutManager= new LinearLayoutManager(getActivity());
        RecyclerViewMismudanzas.setLayoutManager(layoutManager);

        adaptadorMismudanzas=new RecyclerViewMismudanzas(obtenercamas());
        RecyclerViewMismudanzas.setAdapter(adaptadorMismudanzas);

        return view;
    }

    public List<ModeloMismudanzas> obtenercamas() {
        List<ModeloMismudanzas> Mismudanzas=new ArrayList<>();
        Mismudanzas.add(new ModeloMismudanzas(R.drawable.c_uno));
        Mismudanzas.add(new ModeloMismudanzas(R.drawable.c_dos));
        Mismudanzas.add(new ModeloMismudanzas(R.drawable.c_tres));
        Mismudanzas.add(new ModeloMismudanzas(R.drawable.c_cuatro));
        Mismudanzas.add(new ModeloMismudanzas(R.drawable.c_cinco));

        return Mismudanzas;
    }

}
