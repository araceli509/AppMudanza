package com.example.appmudanzas.prestador_Servicio.mudanza;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmudanzas.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mudanzas_tab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mudanzas_tab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mudanzas_tab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    private AppBarLayout appBar;
    private TabLayout pestañas;
    private ViewPager viewPager;
    int id_prestador=0;
    pageAdapter pageAdapter;

    private OnFragmentInteractionListener mListener;

    public mudanzas_tab() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static mudanzas_tab newInstance(String param1, String param2) {
        mudanzas_tab fragment = new mudanzas_tab();
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
        }
        id_prestador=Integer.parseInt( getArguments().getString("id_prestador"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_mudanzas_tab, container, false);


        pestañas=vista.findViewById(R.id.tabs);
        viewPager=vista.findViewById(R.id.viewpager);

        addTab();
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
        pestañas.setupWithViewPager(viewPager);
        return vista;
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

    public void addTab(){
        pageAdapter= new pageAdapter(getFragmentManager());
        Bundle id= new Bundle();
        id.putInt("id_prestador",id_prestador);
        Fragment mRealizada= new MudanzaRealizada();
        Fragment mespera= new MudanzaEspera();
        Fragment activa = new mudanzaActiva();
        activa.setArguments(id);
        mRealizada.setArguments(id);
        mespera.setArguments(id);
        pageAdapter.addFragment(activa,"Activa");
        pageAdapter.addFragment(mespera,"En Espera");
        pageAdapter.addFragment(mRealizada,"Completadas");
        viewPager.setAdapter(pageAdapter);
    }

}
