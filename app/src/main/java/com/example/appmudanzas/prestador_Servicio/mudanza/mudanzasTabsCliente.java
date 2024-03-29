package com.example.appmudanzas.prestador_Servicio.mudanza;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.appmudanzas.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mudanzasTabsCliente.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link mudanzasTabsCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mudanzasTabsCliente extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
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
    int id_prestador=0;
    pageAdapter pageAdapter;

    public mudanzasTabsCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mudanzasTabsCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static mudanzasTabsCliente newInstance(String param1, String param2) {
        mudanzasTabsCliente fragment = new mudanzasTabsCliente();
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
        id_prestador= getArguments().getInt("id_cliente");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_mudanzas_tabs_cliente, container, false);


        pestañas=vista.findViewById(R.id.tabscliente);
        viewPager=vista.findViewById(R.id.viewpagercliente);

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
        Bundle id= new Bundle();
        id.putInt("id_cliente",id_prestador);

        Fragment mRealizada= new MudanzaRealizadaCliente();
        Fragment mEspera= new MudanzaenEsperaCliente();
        Fragment mActiva= new MudanzaAcitvaCliente();
        mRealizada.setArguments(id);
        mEspera.setArguments(id);
        mActiva.setArguments(id);
        pageAdapter.addFragment(mActiva,"Activa");
        pageAdapter.addFragment(mRealizada,"Completadas");
        pageAdapter.addFragment(mEspera,"En Espera");
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

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

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
