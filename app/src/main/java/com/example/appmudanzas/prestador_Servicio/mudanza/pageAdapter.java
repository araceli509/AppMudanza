package com.example.appmudanzas.prestador_Servicio.mudanza;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class pageAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> fragments= new ArrayList<>();
    private final ArrayList<String> titulos= new ArrayList<>();

     public pageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return  fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment f,String titulo) {
        fragments.add(f);
        titulos.add(titulo);
    }
}
