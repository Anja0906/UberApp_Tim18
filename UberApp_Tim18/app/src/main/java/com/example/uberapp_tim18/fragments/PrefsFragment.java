package com.example.uberapp_tim18.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.uberapp_tim18.R;


public class PrefsFragment extends PreferenceFragmentCompat{

    public static PrefsFragment newInstance() {
        Bundle args = new Bundle();
        PrefsFragment fragment = new PrefsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

    }


}