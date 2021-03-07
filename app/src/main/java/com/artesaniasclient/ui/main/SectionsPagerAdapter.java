package com.artesaniasclient.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.artesaniasclient.R;
import com.artesaniasclient.fragments.fragment_my_crafts;
import com.artesaniasclient.fragments.fragment_tab_my_crafts;
import com.artesaniasclient.fragments.fragment_tab_registrer_crafts;
import com.artesaniasclient.utils.Util;

import org.jetbrains.annotations.NotNull;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.list_crafts, R.string.registry_crafts};
    private final Context mContext;
    Bundle bundle;
    fragment_my_crafts fragment_my_crafts;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Bundle distbundle, fragment_my_crafts fragment_my_crafts) {
        super(fm);
        mContext = context;
        bundle = distbundle;
        this.fragment_my_crafts = fragment_my_crafts;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new fragment_tab_my_crafts(fragment_my_crafts);
                fragment.setArguments(Util.getBundleFusion(fragment_my_crafts.getArguments(), bundle));
                break;
            case 1:
                fragment = new fragment_tab_registrer_crafts(mContext);
                fragment.setArguments(Util.getBundleFusion(fragment_my_crafts.getArguments(), bundle));
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String pagetitle =  mContext.getResources().getString(TAB_TITLES[position]);
        return pagetitle;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}