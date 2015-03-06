package com.da.bfar.apps.boatr;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2/27/2015.
 */
@DatabaseTable(tableName = "tblParticular")
public class Particular {
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField()
    private String Flag;

    public Particular() {
    }

    //GETTER AND SETTER

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }
}
