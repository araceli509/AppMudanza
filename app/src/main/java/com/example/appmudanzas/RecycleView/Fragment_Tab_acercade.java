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


public class Fragment_Tab_acercade extends Fragment {

    private RecyclerView RecyclerViewAcercade;
    private RecyclerViewAcercade adaptadorAcercade;
    private LinearLayoutManager layoutManager;
    private ModeloAcercade adaptador;



    public Fragment_Tab_acercade() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab_acercade, container, false);

        RecyclerViewAcercade=(RecyclerView)view.findViewById(R.id.RecyclerMesas);
        layoutManager= new LinearLayoutManager(getActivity());
        RecyclerViewAcercade.setLayoutManager(layoutManager);

        adaptadorAcercade=new RecyclerViewAcercade(obtenermesas());
        RecyclerViewAcercade.setAdapter(adaptadorAcercade);

        return view;
    }

    public List<ModeloAcercade> obtenermesas() {
        List<ModeloAcercade> Acercade=new ArrayList<>();
        Acercade.add(new ModeloAcercade(R.drawable.m_uno));
        Acercade.add(new ModeloAcercade(R.drawable.m_dos));
        Acercade.add(new ModeloAcercade(R.drawable.m_tres));
        Acercade.add(new ModeloAcercade(R.drawable.m_cuatro));
        Acercade.add(new ModeloAcercade(R.drawable.m_cinco));

        return Acercade;
    }

}
