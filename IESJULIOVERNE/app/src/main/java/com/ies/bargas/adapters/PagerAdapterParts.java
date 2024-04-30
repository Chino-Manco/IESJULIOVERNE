package com.ies.bargas.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ies.bargas.fragments.AlumnosFragment;
import com.ies.bargas.fragments.ExpulsionesFragment;
import com.ies.bargas.fragments.PartesFragment;

public class PagerAdapterParts extends FragmentStatePagerAdapter {
    private int numberOfTabs;
    public PagerAdapterParts(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PartesFragment();
            case 1:
                return new ExpulsionesFragment();
            case 2:
                return new AlumnosFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}