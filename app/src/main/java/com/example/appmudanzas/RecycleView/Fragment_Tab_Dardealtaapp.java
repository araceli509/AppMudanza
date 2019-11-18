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


public class Fragment_Tab_Dardealtaapp extends Fragment {

    private RecyclerView RecyclerViewDaralta;
    private RecyclerViewDardealtaapp adaptadorDaralta;
    private LinearLayoutManager layoutManager;
    private ModeloDardealtaapp adaptador;

    public Fragment_Tab_Dardealtaapp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab_dardealtaapp, container, false);

        RecyclerViewDaralta=(RecyclerView)view.findViewById(R.id.RecyclerEstantes);
        layoutManager= new LinearLayoutManager(getActivity());
        RecyclerViewDaralta.setLayoutManager(layoutManager);

        adaptadorDaralta=new RecyclerViewDardealtaapp(obtenerestantes());
        RecyclerViewDaralta.setAdapter(adaptadorDaralta);

        return view;
    }

    public List<ModeloDardealtaapp> obtenerestantes() {
        List<ModeloDardealtaapp> Daralta=new ArrayList<>();
        Daralta.add(new ModeloDardealtaapp(R.drawable.e_uno));
        Daralta.add(new ModeloDardealtaapp(R.drawable.e_dos));
        Daralta.add(new ModeloDardealtaapp(R.drawable.e_tres));
        Daralta.add(new ModeloDardealtaapp(R.drawable.e_cuatro));
        Daralta.add(new ModeloDardealtaapp(R.drawable.e_cinco));

        return Daralta;
    }


}
