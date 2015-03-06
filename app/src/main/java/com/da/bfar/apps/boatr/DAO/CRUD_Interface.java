package com.da.bfar.apps.boatr.DAO;


import com.da.bfar.apps.boatr.BoatRegistration;
import com.da.bfar.apps.boatr.DAO.User;
import com.da.bfar.apps.boatr.FisherFolk;
import com.da.bfar.apps.boatr.GearRegistration;
import com.da.bfar.apps.boatr.Particular;

/**
 * Created by Administrator on 2/26/2015.
 */
public interface CRUD_Interface {
    //create here

    public BoatRegistration CreateBoatRegistrationTable(BoatRegistration boatRegistration);
    public GearRegistration CreateGearRegistrationTable(GearRegistration gearRegistration);
    public Particular CreateParticularTable(Particular particular);
    public FisherFolk CreateFisherFolkTable(FisherFolk fisherFolk);
    public User CreateUserTable(User user);
}
