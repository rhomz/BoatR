package com.da.bfar.apps.boatr.DAO;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2/27/2015.
 */
@DatabaseTable(tableName = "tblUser")
public class User {

    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField()
    private String Username;
    @DatabaseField()
    private String Password;
    @DatabaseField()
    private String Fullname;
    @DatabaseField()
    private String MunicipalityCode;

    public User(String username, String password, String fullname, String municipalityCode) {
        Username = username;
        Password = password;
        Fullname = fullname;
        MunicipalityCode = municipalityCode;
    }

    public User(){}

    //GETTER AND SETTER

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getMunicipalityCode() {
        return MunicipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        MunicipalityCode = municipalityCode;
    }
}
