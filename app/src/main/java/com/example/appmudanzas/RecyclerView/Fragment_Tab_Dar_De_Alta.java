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


public class Fragment_Tab_Dar_De_Alta extends Fragment {

    private RecyclerView RecyclerViewDardealta;
    private RecyclerViewDarDeAlta adaptadorDardealta;
    private LinearLayoutManager layoutManager;
    private Modelo_Dar_De_Alta adaptador;

    public Fragment_Tab_Dar_De_Alta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab_dar_de_alta, container, false);

        RecyclerViewDardealta=(RecyclerView)view.findViewById(R.id.RecyclerDardealta);
        layoutManager= new LinearLayoutManager(getActivity());
        RecyclerViewDardealta.setLayoutManager(layoutManager);

        adaptadorDardealta=new RecyclerViewDarDeAlta(obtenerestantes());
        RecyclerViewDardealta.setAdapter(adaptadorDardealta);

        return view;
    }

    public List<Modelo_Dar_De_Alta> obtenerestantes() {
        List<Modelo_Dar_De_Alta> estantes=new ArrayList<>();
        estantes.add(new Modelo_Dar_De_Alta(R.drawable.e_uno));
        estantes.add(new Modelo_Dar_De_Alta(R.drawable.e_dos));
        estantes.add(new Modelo_Dar_De_Alta(R.drawable.e_tres));
        estantes.add(new Modelo_Dar_De_Alta(R.drawable.e_cuatro));
        estantes.add(new Modelo_Dar_De_Alta(R.drawable.e_cinco));

        return estantes;
    }


}
