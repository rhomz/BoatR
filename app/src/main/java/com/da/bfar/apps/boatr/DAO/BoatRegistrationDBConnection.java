package com.da.bfar.apps.boatr.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.da.bfar.apps.boatr.Admin;
import com.da.bfar.apps.boatr.BoatRegistration;
import com.da.bfar.apps.boatr.FisherFolk;
import com.da.bfar.apps.boatr.GearRegistration;
import com.da.bfar.apps.boatr.Particular;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2/27/2015.
 */
public class BoatRegistrationDBConnection extends OrmLiteSqliteOpenHelper {
        private Context context;
        private static final String databaseName="boatr.db";
        private static final String DATABASE_PATH = "/data/data/gov.da.bfar.boatregistration/databases/";
        private static String IMAGE_DIRECTORY_NAME = "UPLOAD";
        private static int databaseVersion =6;

        private Dao<BoatRegistration, String> daoBoatRegistration;
        private Dao<GearRegistration, String> daoGearRegistration;
        private Dao<FisherFolk, String> daoFisherFolk;
        private Dao<Particular,String> daoParticular;
        private Dao<User, String> daoUser;
        private Dao<TempTable,String> daoTempTable;
        private Dao<Admin,String> daoAdmin;


        public BoatRegistrationDBConnection(Context context) {
            super(context, databaseName, null,databaseVersion);
        }



        @Override
        public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            try{
                TableUtils.createTable(connectionSource, BoatRegistration.class);
                TableUtils.createTable(connectionSource,FisherFolk.class);
                TableUtils.createTable(connectionSource,GearRegistration.class);
                TableUtils.createTable(connectionSource,Particular.class);
                TableUtils.createTable(connectionSource,TempTable.class);
                TableUtils.createTable(connectionSource,User.class);
                TableUtils.createTable(connectionSource,Admin.class);

            }catch (SQLException e){
                e.printStackTrace();
                Log.e(BoatRegistrationDBConnection.class.getName(), "Unable to create databases", e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            try {
                TableUtils.dropTable(connectionSource,BoatRegistration.class, true);
                TableUtils.dropTable(connectionSource,FisherFolk.class, true);
                TableUtils.dropTable(connectionSource,GearRegistration.class, true);
                TableUtils.dropTable(connectionSource,Particular.class, true);
                TableUtils.dropTable(connectionSource,TempTable.class, true);
                TableUtils.dropTable(connectionSource,User.class, true);
                TableUtils.dropTable(connectionSource,Admin.class,true);
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                Log.e(BoatRegistrationDBConnection.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                        + newVersion, e);
            }
        }

        //DAO
        public Dao<BoatRegistration, String> getDaoBoatRegistration() {
            if(daoBoatRegistration==null){
                try {
                    daoBoatRegistration = getDao(BoatRegistration.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return daoBoatRegistration;
        }

        public Dao<GearRegistration, String> getDaoGearRegistration(){
            if (daoGearRegistration == null){
                try {
                    daoGearRegistration = getDao(GearRegistration.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return daoGearRegistration;
        }

        public Dao<FisherFolk, String> getDaoFisherFolk(){
            if(daoFisherFolk == null){
                try {
                    daoFisherFolk = getDao(FisherFolk.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return daoFisherFolk;
        }

        public Dao<Particular,String>getDaoParticular(){
            if(daoParticular == null){
                try {
                    daoParticular = getDao(Particular.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return daoParticular;
        }
        public Dao<User,String>getDaoUser(){
            if(daoUser == null){
                try {
                    daoUser = getDao(User.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return daoUser;
        }

        public Dao<TempTable,String>getDaoTempTable(){
            if(daoTempTable == null){
                try {
                    daoTempTable = getDao(TempTable.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return daoTempTable;
        }

        public Dao<Admin,String>getDaoAdmin(){
            if(daoAdmin == null){
                try {
                    daoAdmin = getDao(Admin.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return daoAdmin;
        }

        //BACK UP DB
   /* public void ExportDB() throws IOException{
        boolean success =true;
        File file = null;
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),IMAGE_DIRECTORY_NAME);
        String inFileName =DATABASE_PATH+databaseName;
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName =Environment.getExternalStorageDirectory()+"/boatr.db";
        Toast.makeText(context,outFileName,Toast.LENGTH_SHORT).show();
        //Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        fis.close();
    }*/
/*
        boolean success =true;
        File file = null;
        file = new File(Environment.getExternalStorageDirectory() +"/M.O.L.S_Backup");

        if(file.exists())
        {
            success =true;
        }
        else
        {
            success = file.mkdir();
        }


        boolean dbexist = checkdatabase();
        if (!dbexist) {

            // If database did not exist, try copying existing database from assets folder.
            try {
                InputStream myinput = context.getAssets().open(databaseName);
                String outfilename = DATABASE_PATH + databaseName;
                Log.i(BoatRegistrationDBConnection.class.getName(), "DB Path : " + outfilename);
                OutputStream myoutput = new FileOutputStream(outfilename);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myinput.read(buffer)) > 0) {
                    myoutput.write(buffer, 0, length);
                }
                myoutput.flush();
                myoutput.close();
                myinput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    *//*
    * Check whether or not database exist
    *//*
    private boolean checkdatabase() {
        boolean checkdb = false;

        String myPath = DATABASE_PATH + databaseName;
        File dbfile = new File(myPath);
        checkdb = dbfile.exists();

        Log.i(BoatRegistrationDBConnection.class.getName(), "DB Exist : " + checkdb);

        return checkdb;
    }*/
}
