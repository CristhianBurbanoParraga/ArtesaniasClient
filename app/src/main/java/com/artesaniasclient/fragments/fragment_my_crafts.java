package com.artesaniasclient.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.artesaniasclient.R;
import com.artesaniasclient.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class fragment_my_crafts extends Fragment {

    String id, name;
    Bundle bundle;
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;

    public fragment_my_crafts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_crafts, container, false);
        id = getArguments().getString("id");
        name = getArguments().getString("name");
        bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("name",name);
        //Codigo traido desde el de jose
        sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(), getChildFragmentManager(), bundle);
        viewPager = view.findViewById(R.id.view_pager_main);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs_main);
        tabs.setupWithViewPager(viewPager);
        return view;
    }
}