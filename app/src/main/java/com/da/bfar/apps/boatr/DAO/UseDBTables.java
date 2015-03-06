package com.da.bfar.apps.boatr.DAO;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.da.bfar.apps.boatr.Admin;
import com.da.bfar.apps.boatr.BoatRegistration;
import com.da.bfar.apps.boatr.DAO.CRUD_Interface;
import com.da.bfar.apps.boatr.FisherFolk;
import com.da.bfar.apps.boatr.GearRegistration;
import com.da.bfar.apps.boatr.Particular;
import com.google.gson.Gson;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;

/**
 * Created by Administrator on 2/26/2015.
 */
public class UseDBTables implements CRUD_Interface {
    /*private MFRS_mainTable table1;*/
    private Context context;
    String TAG = "UseDBTables";
    String Img,Sig,filename_json,ZipName;

    String PATH = Environment.getExternalStorageDirectory().getPath();
    String IMGPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
    private static String IMAGE_DIRECTORY_NAME = "Android File Upload";
    private static String Sig_Directory ="Bfar_Signature";
    BoatRegistrationDBConnection helper;
    public UseDBTables(Context context) {

        this.context = context;

        helper = new BoatRegistrationDBConnection(context);
    }

    //select from Tables
    public void SelectAllFromDB(String param,String tablename){

        switch (tablename){
            case "MFRS_mainTable":
                Log.e(TAG,"TABLE NAME1 **********"+tablename);
                break;
            case "MFRS_User_table":
                Log.e(TAG,"TABLE NAME2**********"+tablename);
                break;
            case "Organization_table":
                Log.e(TAG,"TABLE NAME3 **********"+tablename);
                break;
            case "Temp_table":
                Log.e(TAG,"TABLE NAME4 **********"+tablename);
                break;
            default:
                Log.e(TAG,"Default **********"+tablename);
                break;
        }
    }

    @Override
    public BoatRegistration CreateBoatRegistrationTable(BoatRegistration boatRegistration) {

        String id = null;
        String Img = IMGPATH+"/"+IMAGE_DIRECTORY_NAME+"/"+boatRegistration.getImagePath();
        String Sig = IMGPATH+"/"+Sig_Directory+"/"+boatRegistration.getSignatureImage();

        try {
            helper.getDaoBoatRegistration().createOrUpdate(boatRegistration);
            if(boatRegistration.getID() ==0){
                QueryBuilder<BoatRegistration,String> qb = helper.getDaoBoatRegistration().queryBuilder();
                qb.orderBy("ID",false);
                qb.limit(1);
                id  = Integer.valueOf(helper.getDaoBoatRegistration().queryForFirst(qb.prepare()).getID()).toString();
                Log.e("TAG",id);
            } else {
                id = Integer.valueOf(boatRegistration.getID()).toString();
            }

            Gson gson = new Gson();
            //convert java object to JSON format
            String json = gson.toJson(boatRegistration);
            //helper.getDaoBoatRegistration()
            filename_json =boatRegistration.getCFRNO()+"-BoatRegistration"+"_"+id+".json";
            ZipName = boatRegistration.getCFRNO()+"-BoatRegistration"+"_"+id+".zip";
            FtpSender ftpSender = new FtpSender(context,boatRegistration);
            ftpSender.textWritter(json,filename_json);

            //after writting.
            String[] dataFileZip = new String[3]; //change to 3

            dataFileZip[0] = PATH+"/"+filename_json;
            dataFileZip[1] = Img; //image name..
            dataFileZip[2] = Sig; //signature name
            ftpSender.zip(dataFileZip,PATH+"/"+ZipName);

            //after zip delete jsonfile;
            //ftpSender.DeleteData(PATH+"/"+filename_json);
         //  ftpSender.DeleteData(Img);
           // ftpSender.DeleteData(Sig);
            //ftpSender.DeleteData(PATH+"/a.txt");
            //ftpSender.DeleteData(PATH+"/a.zip");

            //detect if my net.. pag my apo
            if (ftpSender.isConnectingToInternet() == true){ //check networkstate..
                //pag my api n sa office
                ftpSender.CheckServer(2,ZipName); //check response to the server.. if reply send zip file

                //pag walang api use this code
                //ftpSender.SendToFtp(2,ZipName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boatRegistration;
    }

    @Override
    public GearRegistration CreateGearRegistrationTable(GearRegistration gearRegistration) {
        try {
            helper.getDaoGearRegistration().createOrUpdate(gearRegistration);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gearRegistration;
    }

    @Override
    public Particular CreateParticularTable(Particular particular) {
        try {
            helper.getDaoParticular().createOrUpdate(particular);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return particular;
    }

    @Override
    public FisherFolk CreateFisherFolkTable(FisherFolk fisherFolk) {
        try {
            helper.getDaoFisherFolk().create(fisherFolk);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fisherFolk;
    }

    @Override
    public User CreateUserTable(User user) {
        try {
            helper.getDaoUser().create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void UpdateIsSendEqTrue(Object object_name,int ID){
        String class_name = object_name.getClass().getSimpleName();
        switch (class_name){
            case "BoatRegistration":
                Log.e(TAG,"Class: "+class_name+"---ID Value: "+String.valueOf(ID));

                UpdateBuilder<BoatRegistration,String> updateBuilderForBoatR = helper.getDaoBoatRegistration().updateBuilder();
                try {
                    updateBuilderForBoatR.updateColumnValue("isSend","2");
                    updateBuilderForBoatR.where().eq("ID",ID).prepare();
                    updateBuilderForBoatR.update();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case "GearRegistration":

                Log.e(TAG,"Class: "+class_name+"---ID Value: "+String.valueOf(ID));

                UpdateBuilder<GearRegistration,String> updateBuilderForGearR = helper.getDaoGearRegistration().updateBuilder();
                try {
                    updateBuilderForGearR.updateColumnValue("isSend","2");
                    updateBuilderForGearR.where().eq("ID",ID).prepare();
                    updateBuilderForGearR.update();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void DeleteIsSendEqTrue(){
        DeleteBuilder<BoatRegistration,String> deleteBuilderForBoatR = helper.getDaoBoatRegistration().deleteBuilder();
        try {
            deleteBuilderForBoatR.where().eq("isSend","2").prepare();
            deleteBuilderForBoatR.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DeleteBuilder<GearRegistration,String> deleteBuilderForGearR = helper.getDaoGearRegistration().deleteBuilder();
        try {
            deleteBuilderForGearR.where().eq("isSend","2").prepare();
            deleteBuilderForGearR.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CreateAdminAccount(){
        Admin admin = new Admin();
        BoatRegistrationDBConnection boatr = new BoatRegistrationDBConnection(context);
        admin.setAdminUsername("BFAR_FIMC");
        admin.setAdminPassword("bfar1234");
        admin.setIsSetup("1");
        try {
            Log.e(TAG,"Creating Admin Account");
            boatr.getDaoAdmin().create(admin);
            Log.e(TAG,"Successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateAdminAccount(){
        Admin admin = new Admin();
        UpdateBuilder<Admin, String> ubuilder = new BoatRegistrationDBConnection(context).getDaoAdmin().updateBuilder();
        try {
            ubuilder.updateColumnValue("isSetup", "2");
            ubuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setFlagByCondition(String method){
        Particular particular = new Particular();
        switch(method){
            case "set":
                particular.setID(1);
                particular.setFlag("1");
                try {
                    helper.getDaoParticular().createOrUpdate(particular);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "unset":
                particular.setID(1);
                particular.setFlag("2");
                try {
                    helper.getDaoParticular().createOrUpdate(particular);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
