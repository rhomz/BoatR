package com.da.bfar.apps.boatr;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2/27/2015.
 */
@DatabaseTable(tableName = "tblGearRegistration")
public class GearRegistration {
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField()
    private String CFRNO;
    @DatabaseField()
    private String GearName;
    @DatabaseField()
    private String RegBy;
    @DatabaseField()
    private String Date;
    @DatabaseField()
    private String isSend;

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }
    //GETTER AND SETTER

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCFRNO() {
        return CFRNO;
    }

    public void setCFRNO(String CFRNO) {
        this.CFRNO = CFRNO;
    }

    public String getGearName() {
        return GearName;
    }

    public void setGearName(String gearName) {
        GearName = gearName;
    }

    public String getRegBy() {
        return RegBy;
    }

    public void setRegBy(String regBy) {
        RegBy = regBy;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
