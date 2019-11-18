package com.example.appmudanzas.RecycleView;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appmudanzas.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

//import android.support.annotation.Nullable;


public class GaleriaFragment extends Fragment {

    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;


    public GaleriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_galeria, container, false);

        View contenedor=(View)container.getParent();
        appBar=(AppBarLayout)contenedor.findViewById(R.id.appbar);
        tabs=new TabLayout(getActivity());

        tabs.setTabTextColors(Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"));
        appBar.addView(tabs);

        viewPager=(ViewPager)view.findViewById(R.id.pager);
        ViewPagerAdapter pagerAdapter=new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onDestroy() {
        appBar.removeView(tabs);
        super.onDestroy();
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {


        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    Fragment_Tab_acercade fragment_tab_acercade=new Fragment_Tab_acercade();
                    return fragment_tab_acercade;
                case 1:
                    Fragment_Tab_Dardealtaapp fragment_tab_dardealtaapp=new Fragment_Tab_Dardealtaapp();
                    return fragment_tab_dardealtaapp;
                case 2:
                    Fragment_Tab_Mismudanzas fragment_tab_mismudanzas=new Fragment_Tab_Mismudanzas();
                    return fragment_tab_mismudanzas;
                case 3:
                    Fragment_Tab_Solicitudes fragment_tab_solicitudes=new Fragment_Tab_Solicitudes();
                    return fragment_tab_solicitudes;
            }


            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    String tab_text_1=("A cerca de");
                    return tab_text_1;
                case 1:
                    String tab_text_2=("Dar de alta con la app");
                    return tab_text_2;
                case 2:
                    String tab_text_3=("Mis mudanzas");
                    return tab_text_3;
                case 3:
                    String tab_text_4=("Solicitudes");
                    return tab_text_4;



            }


            return super.getPageTitle(position);
        }
    }
}
