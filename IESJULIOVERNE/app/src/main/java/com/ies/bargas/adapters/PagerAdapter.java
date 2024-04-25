package com.ies.bargas.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ies.bargas.Fragment.Alumnos;
import com.ies.bargas.Fragment.Expulsiones;
import com.ies.bargas.Fragment.Partes;

class PagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;
    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Partes();
            case 1:
                return new Expulsiones();
            case 2:
                return new Alumnos();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}