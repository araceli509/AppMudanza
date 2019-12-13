package com.example.appmudanzas.prestador_Servicio.mudanza;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
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
 * {@link mudanzasTabs.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mudanzasTabs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mudanzasTabs extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    View vista;
    private AppBarLayout appBar;
    private TabLayout pestañas;
    private ViewPager viewPager;
    NestedScrollView viewp;
    int id_prestador=0;
    pageAdapter pageAdapter;

    public mudanzasTabs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mudanzasTabs.
     */
    // TODO: Rename and change types and number of parameters
    public static mudanzasTabs newInstance(String param1, String param2) {
        mudanzasTabs fragment = new mudanzasTabs();
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
        id_prestador= getArguments().getInt("id_prestador");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_mudanzas_tabs, container, false);

        appBar= vista.findViewById(R.id.appbar);
        pestañas=new TabLayout(getContext());
        appBar.addView(pestañas);
        viewPager=vista.findViewById(R.id.viewpager);
        viewp = vista.findViewById(R.id.pager);

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

    public void addTab(){
        pageAdapter= new pageAdapter(getFragmentManager());
        Fragment mespera= new MudanzaEspera();
        Bundle id= new Bundle();
        id.putInt("id_prestador",id_prestador);
        Fragment mRealizada= new MudanzaRealizada();
        mRealizada.setArguments(id);
        mespera.setArguments(id);
        pageAdapter.addFragment(mespera,"En Espera");
        pageAdapter.addFragment(mRealizada,"Completadas");
        viewPager.setAdapter(pageAdapter);
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
}
