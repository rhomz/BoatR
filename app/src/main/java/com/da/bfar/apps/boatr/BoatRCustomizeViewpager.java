package com.da.bfar.apps.boatr;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.da.bfar.apps.boatr.DAO.MySingleton;
import com.da.bfar.apps.boatr.fragment.FragmentHome;
import com.da.bfar.apps.boatr.tabFragment.*;
import com.da.bfar.apps.boatr.tabFragment.BoatRegistrationFragment;

/**
 * Created by BFAR-PC on 2/28/2015.
 */
public class BoatRCustomizeViewpager extends ViewPager {
    private String TAG = this.getClass().getSimpleName();
    private boolean enabled;


    public BoatRCustomizeViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void setCurrentItem(int item) {


        switch (item){
            case 1:
                MySingleton.getInstance().getBoatRegistrationFragmentBus().post("hello from pager");
                Log.e(TAG, "index:getBoatRegistrationFragmentBus" + item);
                break;

            case 2:
                MySingleton.getInstance().getFishingVesselDimensionBus().post("getFishingVesselDimensionBus");
                Log.e(TAG, "index:getFishingVesselDimensionBus" + item);
                break;

            case 3:
                MySingleton.getInstance().getBoatRFormEngineBus().post("hello from pager");
                break;

            case 4:
                MySingleton.getInstance().getFishingGearsFragmentBus().post("hello from pager");
                break;

            case 5:
                MySingleton.getInstance().getBasicInfoDoneBus().post("hello from pager");
                break;
        }

//        super.setCurrentItem(item);

    }
    public void loadNextFragment(int item){
        super.setCurrentItem(item);
    }
}
