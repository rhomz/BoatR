package com.da.bfar.apps.boatr;

/**
 * Created by BFAR-PC on 3/4/2015.
 */
public class StatusSingleton {
    private static StatusSingleton ourInstance = new StatusSingleton();

    private DataStatus MyStatus;

    public static StatusSingleton getInstance() {
        return ourInstance;
    }

    private StatusSingleton() {
        this.MyStatus = new DataStatus();
    }

   public String getDataStatus(){
      return this.MyStatus.getDataStatus();
   }

    public void setDataStatus(String dataStatus){
        this.MyStatus.setDataStatus(dataStatus);
    }
}
