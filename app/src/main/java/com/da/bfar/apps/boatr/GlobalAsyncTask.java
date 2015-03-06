package com.da.bfar.apps.boatr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.da.bfar.apps.boatr.DAO.UseDBTables;
import com.da.bfar.apps.boatr.tabFragment.TabFragmentDone;

/**
 * Created by Administrator on 2/27/2015.
 */
public class GlobalAsyncTask extends AsyncTask {


    String TAG = GlobalAsyncTask.class.getSimpleName();

    private Object class_name;

    private Context context;

    ProgressDialog progressDialog;


    private int ID;
    //parameters for CreateOrUpdate of BoatR Registration
    private String MFVRNo;
    private String Date;
    private String TypeOfRegistration;
    private String CFRNO;
    private String Homeport;
    private String NameOfVessel;
    private String VesselType;
    private String PlaceBuilt;
    private String YearBuilt;
    private String MaterialUsed;
    private String RegLength;
    private String TonLength;
    private String RegBreadth;
    private String TonBreadth;
    private String RegDepth;
    private String TonDepth;
    private String GrossTonnage;
    private String NetTonnage;
    private String EngineMake;
    private String SerialNumber;
    private String Horsepower;
    private String ImagePath;
    private String SignatureImage;
    private String FishingGear;
    private String MunicipalCode;
    private String ProvinceCode;
    private String RegionCode;
    private String Registered_By;
    private String LastUpdated_By;
    private String DateUpdated;
    private String EngineMake2;
    private String SerialNumber2;
    private String Horsepower2;

    //End *************




    //**********************************************//
    // ******This the Constructor for the Table ****//
    // *******       of BoatRegistration     *******//
    // ********************************************//
    // *******************************************//
    public GlobalAsyncTask(Object class_name,
                           Context context,
                           int ID,
                           String MFVRNo,
                           String date,
                           String typeOfRegistration,
                           String CFRNO,
                           String homeport,
                           String nameOfVessel,
                           String vesselType,
                           String placeBuilt,
                           String yearBuilt,
                           String materialUsed,
                           String regLength,
                           String tonLength,
                           String regBreadth,
                           String tonBreadth,
                           String regDepth,
                           String tonDepth,
                           String grossTonnage,
                           String netTonnage,
                           String engineMake,
                           String serialNumber,
                           String horsepower,
                           String fishingGear,
                           String municipalCode,
                           String provinceCode,
                           String regionCode,
                           String registered_By,
                           String lastUpdated_By,
                           String dateUpdated,
                           String engineMake2,
                           String serialNumber2,
                           String horsepower2,
                           String imagePath,
                           String signatureImage)
    {

        this.class_name = class_name;
        this.context = context;

        //constant
        progressDialog = new ProgressDialog(context);
        //end

        //parameter for CreateOrUpdate of BoatRegistration
        this.ID = ID;
        this.MFVRNo = MFVRNo;
        Date = date;
        TypeOfRegistration = typeOfRegistration;
        this.CFRNO = CFRNO;
        Homeport = homeport;
        NameOfVessel = nameOfVessel;
        VesselType = vesselType;
        PlaceBuilt = placeBuilt;
        YearBuilt = yearBuilt;
        MaterialUsed = materialUsed;
        RegLength = regLength;
        TonLength = tonLength;
        RegBreadth = regBreadth;
        TonBreadth = tonBreadth;
        RegDepth = regDepth;
        TonDepth = tonDepth;
        GrossTonnage = grossTonnage;
        NetTonnage = netTonnage;
        EngineMake = engineMake;
        SerialNumber = serialNumber;
        Horsepower = horsepower;
        ImagePath = imagePath;
        SignatureImage = signatureImage;
        FishingGear = fishingGear;
        MunicipalCode = municipalCode;
        ProvinceCode = provinceCode;
        RegionCode = regionCode;
        Registered_By = registered_By;
        LastUpdated_By = lastUpdated_By;
        DateUpdated = dateUpdated;
        EngineMake2 = engineMake2;
        SerialNumber2 = serialNumber2;
        Horsepower2 = horsepower2;
    }
    //**********END*************//


    //**********************************************//
    // ******This the Constructor for the Table ****//
    // *******       of GearRegistration     *******//
    // ********************************************//
    // *******************************************//


    public GlobalAsyncTask(Object class_name, Context context, int GearID, String GearCFRNO, String GearfishingGear, String Gearregistered_By, String Geardate) {
        this.class_name = class_name;
        this.context = context;
        this.ID = GearID;
        this.CFRNO = GearCFRNO;
        FishingGear = GearfishingGear;
        Registered_By = Gearregistered_By;
        Date = Geardate;
        progressDialog = new ProgressDialog(context);
    }

    public GlobalAsyncTask(Object class_name, Context context) {
        this.class_name = class_name;
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }


    @Override
    protected Object doInBackground(Object[] params) {


        String getClassName = class_name.getClass().getSimpleName();
        switch (getClassName){
            case "BoatRegistration":



                //insert method for update or insert here
                insertIntoBoatRegistration();
                if (Thread.currentThread().isInterrupted()){
                    throw new RuntimeException("This Thread has been Interrupted !");
                }else {
                    Log.e(TAG, "Object Name: ->" + getClassName);
                }
                break;
            case "GearRegistration":
                insertIntoGearRegistration();
                if (Thread.currentThread().isInterrupted()){
                    throw  new RuntimeException("This Thread has been Interrupted!");
                }else {
                    Log.e(TAG, "Object Name: ->" + getClassName);
                }
                break;
            /*case "FisherFolk":
                insertFisherFolk();
                break;*/
            default:
                Log.e(TAG,getClassName);
                break;
        }

        Log.e(TAG,"CLASS NAME: ************ "+getClassName);

        //runnable background loading
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //switch Table name
                if (Thread.currentThread().isInterrupted()){
                    throw new RuntimeException("Thread 2:The Thread is interrupted");
                }else {
                    Log.e(TAG, "Thread 2:Successful");
                }
                try {
                    Thread.sleep(3000);
                    Thread.holdsLock(class_name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.run();


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Please wait ..");
        progressDialog.setMessage("The application is processing you data ..");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();


        /*Intent intent = new Intent(context,BoatRActivity.class);
        context.startActivity(intent);*/


        /*((MainActivity)context).fragmentNavigator(0);*/

    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }



    //method for insert

    public void insertIntoBoatRegistration(){

        BoatRegistration boatRegistration = new BoatRegistration();
        UseDBTables useDBTables = new UseDBTables(context);

        boatRegistration.setID(ID);


        boatRegistration.setMFVRNo(MFVRNo);

        boatRegistration.setDate(Date);

        boatRegistration.setTypeOfRegistration(TypeOfRegistration);

        boatRegistration.setCFRNO(CFRNO);

        boatRegistration.setHomeport(Homeport);

        boatRegistration.setNameOfVessel(NameOfVessel);

        boatRegistration.setVesselType(VesselType);

        boatRegistration.setPlaceBuilt(PlaceBuilt);

        boatRegistration.setYearBuilt(YearBuilt);

        boatRegistration.setMaterialUsed(MaterialUsed);

        boatRegistration.setRegLength(RegLength);

        boatRegistration.setTonLength(TonLength);

        boatRegistration.setRegBreadth(RegBreadth);

        boatRegistration.setTonBreadth(TonBreadth);

        boatRegistration.setRegDepth(RegDepth);

        boatRegistration.setTonDepth(TonDepth);

        boatRegistration.setGrossTonnage(GrossTonnage);

        boatRegistration.setNetTonnage(NetTonnage);

        boatRegistration.setEngineMake(EngineMake);

        boatRegistration.setSerialNumber(SerialNumber);

        boatRegistration.setHorsepower(Horsepower);

        boatRegistration.setImagePath(ImagePath);

        boatRegistration.setSignatureImage(SignatureImage);

        boatRegistration.setFishingGear(FishingGear);

        boatRegistration.setMunicipalCode(MunicipalCode);

        boatRegistration.setProvinceCode(ProvinceCode);

        boatRegistration.setRegionCode(RegionCode);

        boatRegistration.setRegistered_By(Registered_By);

        boatRegistration.setLastUpdated_By(LastUpdated_By);

        boatRegistration.setDateUpdated(DateUpdated);

        boatRegistration.setEngineMake2(EngineMake2);

        boatRegistration.setSerialNumber2(SerialNumber2);

        boatRegistration.setHorsepower2(Horsepower2);

        if (ID == 0){
            boatRegistration.setIsSend("1");
        }else{
            boatRegistration.setIsSend("2");
        }

        useDBTables.CreateBoatRegistrationTable(boatRegistration);
    }


    public void insertIntoGearRegistration(){
        GearRegistration gearRegistration = new GearRegistration();
        UseDBTables useDBTables = new UseDBTables(context);

        gearRegistration.setID(ID);

        gearRegistration.setCFRNO(CFRNO);

        gearRegistration.setGearName(FishingGear);

        gearRegistration.setRegBy(Registered_By);

        gearRegistration.setDate(Date);

        if(ID == 0){
            gearRegistration.setIsSend("1");
        }else{
            gearRegistration.setIsSend("2");
        }

        useDBTables.CreateGearRegistrationTable(gearRegistration);

    }

    /*public void insertFisherFolk(){
        FisherFolk fisherFolk = new FisherFolk();

        UseDBTables useDBTables = new UseDBTables(context);
        fisherFolk.setID(ID);
        fisherFolk.setCFRNO("BO-TG-000001-2015");
        fisherFolk.setFullname("Alma Lumahang Abuyabor");
        fisherFolk.setAddress("Purok 2 , Gondong St. Ubujan Tagbilaran City (Capital) Bohol");




        useDBTables.CreateFisherFolkTable(fisherFolk);
        Log.e(TAG,"Success");
    }*/

}
