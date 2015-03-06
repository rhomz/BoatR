package com.da.bfar.apps.boatr;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.da.bfar.apps.boatr.fragment.FragmentHome;
import com.da.bfar.apps.boatr.tabFragment.BasicInfoDone;
import com.da.bfar.apps.boatr.tabFragment.BoatRFormEngine;
import com.da.bfar.apps.boatr.tabFragment.BoatRegistrationFragment;
import com.da.bfar.apps.boatr.tabFragment.FishingGearsFragment;
import com.da.bfar.apps.boatr.tabFragment.FishingVesselDimension;
import com.da.bfar.apps.boatr.tabFragment.TabFragmentDone;

/*
    *  PAGER ADAPTER
    * */
public class PagerAdapter extends FragmentPagerAdapter {

    BoatRegistrationFragment boatRegistrationFragment = new BoatRegistrationFragment();
    boolean x = boatRegistrationFragment.getValidOrInvalid();
    public PagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    private final String[] TITLES = { "Basic Info", "Dimension & Tonnage", "Propulsion System","Particulars and Classification","Gear Classification","Done"};

    private int position;
    private  String warning="";
    private Context context;



    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

       /* if(position == 0){

            fragment = BoatRegistrationFragment.newInstance("", "");
        }else if(position == 1){
            if(boatRegistrationFragment.getValidOrInvalid()==true){
                fragment = TabFragmenDimensionTonnage.newInstance("", "");
            }else{
               // ((BoatRActivity)boatRegistrationFragment.getActivity()).onInvalid();
            }
        }*/
        switch (position){

            case 0:
                fragment = BoatRegistrationFragment.newInstance("", "");
                break;

            case 1:
                fragment = FishingVesselDimension.newInstance("", "");
                 break;

            case 2:
                fragment = BoatRFormEngine.newInstance("", "");
                break;

            case 3:
                fragment = FishingGearsFragment.newInstance("", "");
                break;
            case 4:
                fragment = BasicInfoDone.newInstance("", "");
                break;

            case 5:
                fragment = TabFragmentDone.newInstance("", "");
                break;



        }
        this.position = position;
        Log.e("PagerAdapter", "onViewCreated:" + position);
        return fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
    public  String returnTitle(){

        return String.valueOf(getPageTitle(position));
    }
public void Warning(String warning,Context context){

    this.warning = warning;
this.context = context;
}





}
