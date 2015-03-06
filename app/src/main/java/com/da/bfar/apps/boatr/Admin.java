package com.da.bfar.apps.boatr;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by BFAR-PC on 3/4/2015.
 */
@DatabaseTable(tableName = "tblAdmin")
public class Admin {
    @DatabaseField
    private String AdminUsername;
    @DatabaseField
    private String AdminPassword;
    @DatabaseField
    private String isSetup;

    public Admin(String adminUsername, String adminPassword, String isSetup) {
        AdminUsername = adminUsername;
        AdminPassword = adminPassword;
        this.isSetup = isSetup;
    }

    public Admin() {

    }

    public String getAdminUsername() {
        return AdminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        AdminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return AdminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        AdminPassword = adminPassword;
    }

    public String getIsSetup() {
        return isSetup;
    }

    public void setIsSetup(String isSetup) {
        this.isSetup = isSetup;
    }
}
