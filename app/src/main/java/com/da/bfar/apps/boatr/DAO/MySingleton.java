package com.da.bfar.apps.boatr.DAO;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by rmara on 3/1/15.
 */
public class MySingleton {
    private static MySingleton ourInstance = new MySingleton();

    private Bus FragmentTabBus = new Bus(ThreadEnforcer.ANY);

    private Bus BoatRegistrationFragmentBus = new Bus(ThreadEnforcer.ANY);
    private Bus FishingVesselDimensionBus = new Bus(ThreadEnforcer.ANY);
    private Bus BoatRFormEngineBus = new Bus(ThreadEnforcer.ANY);
    private Bus FishingGearsFragmentBus = new Bus(ThreadEnforcer.ANY);
    private Bus BasicInfoDoneBus = new Bus(ThreadEnforcer.ANY);

    private Bus TabFragmentDoneBus = new Bus(ThreadEnforcer.ANY);

    public static MySingleton getInstance() {
        return ourInstance;
    }

    private MySingleton() {
    }

    public Bus getFragmentTabBus() {
        return FragmentTabBus;
    }

    public Bus getBoatRegistrationFragmentBus() {
        return BoatRegistrationFragmentBus;
    }

    public Bus getFishingVesselDimensionBus() {
        return FishingVesselDimensionBus;
    }

    public Bus getBoatRFormEngineBus() {
        return BoatRFormEngineBus;
    }

    public Bus getBasicInfoDoneBus() {
        return BasicInfoDoneBus;
    }

    public Bus getTabFragmentDoneBus() {
        return TabFragmentDoneBus;
    }

    public Bus getFishingGearsFragmentBus() {
        return FishingGearsFragmentBus;
    }
}
