package com.designsapp.thebeatueart.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.designsapp.thebeatueart.Fragment.khadamatSalonmackupFragment;
import com.designsapp.thebeatueart.Model.MSalonServies.Datum;

import java.util.ArrayList;

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private ArrayList<Datum> xservices;
    private String idx;
    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, ArrayList<Datum> services, String id) {
        super(fm);
        mContext = context;
        xservices = services;
        idx = id;
    }



    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        int po = xservices.get(position).getId();
        bundle.putInt("edttext", position);
        bundle.putString("edttext2", idx);
        khadamatSalonmackupFragment fragobj = new khadamatSalonmackupFragment();
        fragobj.setArguments(bundle);
        return  fragobj;
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return xservices.size();
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        return xservices.get(position).getName();
    }


}