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


public class Fragment_Tab_MisMudanzas extends Fragment {


    private RecyclerView RecyclerViewCamas;
    private RecyclerViewMisMudanzas adaptadorCamas;
    private LinearLayoutManager layoutManager;
    private ModeloMisMudanzas adaptador;

    public Fragment_Tab_MisMudanzas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mismudanzas, container, false);

        RecyclerViewCamas=(RecyclerView)view.findViewById(R.id.RecyclerCamas);
        layoutManager= new LinearLayoutManager(getActivity());
        RecyclerViewCamas.setLayoutManager(layoutManager);

        adaptadorCamas=new RecyclerViewMisMudanzas(obtenercamas());
        RecyclerViewCamas.setAdapter(adaptadorCamas);

        return view;
    }

    public List<ModeloMisMudanzas> obtenercamas() {
        List<ModeloMisMudanzas> camas=new ArrayList<>();
        camas.add(new ModeloMisMudanzas(R.drawable.c_uno));
        camas.add(new ModeloMisMudanzas(R.drawable.c_dos));
        camas.add(new ModeloMisMudanzas(R.drawable.c_tres));
        camas.add(new ModeloMisMudanzas(R.drawable.c_cuatro));
        camas.add(new ModeloMisMudanzas(R.drawable.c_cinco));

        return camas;
    }

}
