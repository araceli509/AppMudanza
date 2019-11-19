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

public class Fragment_Tab_AcercaDe extends Fragment {

    private RecyclerView RecyclerViewMesas;
    private RecyclerViewAcercaDe adaptadorMesas;
    private LinearLayoutManager layoutManager;
    private ModeloAcercaDe adaptador;



    public Fragment_Tab_AcercaDe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab_acercade, container, false);

        RecyclerViewMesas=(RecyclerView)view.findViewById(R.id.RecyclerMesas);
        layoutManager= new LinearLayoutManager(getActivity());
        RecyclerViewMesas.setLayoutManager(layoutManager);

        adaptadorMesas=new RecyclerViewAcercaDe(obtenermesas());
        RecyclerViewMesas.setAdapter(adaptadorMesas);

        return view;
    }

    public List<ModeloAcercaDe> obtenermesas() {
        List<ModeloAcercaDe> mesas=new ArrayList<>();
        mesas.add(new ModeloAcercaDe(R.drawable.m_uno));
        mesas.add(new ModeloAcercaDe(R.drawable.m_dos));
        mesas.add(new ModeloAcercaDe(R.drawable.m_tres));
        mesas.add(new ModeloAcercaDe(R.drawable.m_cuatro));
        mesas.add(new ModeloAcercaDe(R.drawable.m_cinco));

        return mesas;
    }

}
