package com.da.bfar.apps.boatr;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2/27/2015.
 */
@DatabaseTable(tableName = "tblFisherFolk")
public class FisherFolk {
    @DatabaseField()
    private String CFRNO;
    @DatabaseField()
    private String Fullname;
    @DatabaseField()
    private String Address;
    @DatabaseField
    private String TaskSequence;

    public FisherFolk(String CFRNO, String fullname, String address, String taskSequence) {
        this.CFRNO = CFRNO;
        Fullname = fullname;
        Address = address;
        TaskSequence = taskSequence;
    }

    public FisherFolk() {
    }

    //GETTER AND SETTERS

    public String getCFRNO() {
        return CFRNO;
    }

    public void setCFRNO(String CFRNO) {
        this.CFRNO = CFRNO;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTaskSequence() {
        return TaskSequence;
    }

    public void setTaskSequence(String taskSequence) {
        TaskSequence = taskSequence;
    }
}
