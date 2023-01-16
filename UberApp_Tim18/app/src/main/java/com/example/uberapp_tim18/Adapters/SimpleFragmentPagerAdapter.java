package com.example.uberapp_tim18.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.uberapp_tim18.CreateRide;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new CreateRide();
    }

    @Override
    public int getCount() {
        return 0;
    }
}
