package com.da.bfar.apps.boatr.DAO;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2/27/2015.
 */
@DatabaseTable(tableName = "tblTempTable")
public class TempTable {
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField()
    private String MFVRNo;
    @DatabaseField()
    private String BoatName;
    @DatabaseField()
    private String CFRNO;
    @DatabaseField()
    private String FisherFolkName;
    @DatabaseField()
    private String GearName;

    //GETTER AND SETTER


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMFVRNo() {
        return MFVRNo;
    }

    public void setMFVRNo(String MFVRNo) {
        this.MFVRNo = MFVRNo;
    }

    public String getBoatName() {
        return BoatName;
    }

    public void setBoatName(String boatName) {
        BoatName = boatName;
    }

    public String getCFRNO() {
        return CFRNO;
    }

    public void setCFRNO(String CFRNO) {
        this.CFRNO = CFRNO;
    }

    public String getFisherFolkName() {
        return FisherFolkName;
    }

    public void setFisherFolkName(String fisherFolkName) {
        FisherFolkName = fisherFolkName;
    }

    public String getGearName() {
        return GearName;
    }

    public void setGearName(String gearName) {
        GearName = gearName;
    }
}
