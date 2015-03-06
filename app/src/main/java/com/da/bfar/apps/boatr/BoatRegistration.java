package com.da.bfar.apps.boatr;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2/27/2015.
 */
@DatabaseTable(tableName = "tblBoatRegistration")
public class BoatRegistration {
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField()
    private String MFVRNo = "null";
    @DatabaseField()
    private String Date = "null";
    @DatabaseField()
    private String TypeOfRegistration  = "null";
    @DatabaseField()
    private String CFRNO  = "null";
    @DatabaseField()
    private String Homeport  = "null";
    @DatabaseField()
    private String NameOfVessel = "null";
    @DatabaseField()
    private String VesselType = "null";
    @DatabaseField()
    private String PlaceBuilt = "null";
    @DatabaseField()
    private String YearBuilt = "null";
    @DatabaseField()
    private String MaterialUsed = "null";
    @DatabaseField()
    private String RegLength = "null";
    @DatabaseField()
    private String TonLength = "null";
    @DatabaseField()
    private String RegBreadth = "null";
    @DatabaseField()
    private String TonBreadth = "null";
    @DatabaseField()
    private String RegDepth = "null";
    @DatabaseField()
    private String TonDepth = "null";
    @DatabaseField()
    private String GrossTonnage = "null";
    @DatabaseField()
    private String NetTonnage = "null";
    @DatabaseField()
    private String EngineMake = "null";
    @DatabaseField()
    private String SerialNumber = "null";
    @DatabaseField()
    private String Horsepower = "null";
    @DatabaseField()
    private String ImagePath = "null";
    @DatabaseField()
    private String SignatureImage = "null";
    @DatabaseField()
    private String FishingGear = "null";
    @DatabaseField()
    private String MunicipalCode = "null";
    @DatabaseField()
    private String ProvinceCode = "null";
    @DatabaseField()
    private String RegionCode = "null";
    @DatabaseField()
    private String Registered_By = "null";
    @DatabaseField()
    private String LastUpdated_By = "null";
    @DatabaseField()
    private String DateUpdated = "null";
    @DatabaseField()
    private String EngineMake2 = "null";
    @DatabaseField()
    private String SerialNumber2 = "null";
    @DatabaseField()
    private String Horsepower2 = "null";
    @DatabaseField()
    private String isSend;

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    //GETTER AND SETTERS
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTypeOfRegistration() {
        return TypeOfRegistration;
    }

    public void setTypeOfRegistration(String typeOfRegistration) {
        TypeOfRegistration = typeOfRegistration;
    }

    public String getCFRNO() {
        return CFRNO;
    }

    public void setCFRNO(String CFRNO) {
        this.CFRNO = CFRNO;
    }

    public String getHomeport() {
        return Homeport;
    }

    public void setHomeport(String homeport) {
        Homeport = homeport;
    }

    public String getNameOfVessel() {
        return NameOfVessel;
    }

    public void setNameOfVessel(String nameOfVessel) {
        NameOfVessel = nameOfVessel;
    }

    public String getVesselType() {
        return VesselType;
    }

    public void setVesselType(String vesselType) {
        VesselType = vesselType;
    }

    public String getPlaceBuilt() {
        return PlaceBuilt;
    }

    public void setPlaceBuilt(String placeBuilt) {
        PlaceBuilt = placeBuilt;
    }

    public String getYearBuilt() {
        return YearBuilt;
    }

    public void setYearBuilt(String yearBuilt) {
        YearBuilt = yearBuilt;
    }

    public String getMaterialUsed() {
        return MaterialUsed;
    }

    public void setMaterialUsed(String materialUsed) {
        MaterialUsed = materialUsed;
    }

    public String getRegLength() {
        return RegLength;
    }

    public void setRegLength(String regLength) {
        RegLength = regLength;
    }

    public String getTonLength() {
        return TonLength;
    }

    public void setTonLength(String tonLength) {
        TonLength = tonLength;
    }

    public String getRegBreadth() {
        return RegBreadth;
    }

    public void setRegBreadth(String regBreadth) {
        RegBreadth = regBreadth;
    }

    public String getTonBreadth() {
        return TonBreadth;
    }

    public void setTonBreadth(String tonBreadth) {
        TonBreadth = tonBreadth;
    }

    public String getRegDepth() {
        return RegDepth;
    }

    public void setRegDepth(String regDepth) {
        RegDepth = regDepth;
    }

    public String getTonDepth() {
        return TonDepth;
    }

    public void setTonDepth(String tonDepth) {
        TonDepth = tonDepth;
    }

    public String getGrossTonnage() {
        return GrossTonnage;
    }

    public void setGrossTonnage(String grossTonnage) {
        GrossTonnage = grossTonnage;
    }

    public String getNetTonnage() {
        return NetTonnage;
    }

    public void setNetTonnage(String netTonnage) {
        NetTonnage = netTonnage;
    }

    public String getEngineMake() {
        return EngineMake;
    }

    public void setEngineMake(String engineMake) {
        EngineMake = engineMake;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getHorsepower() {
        return Horsepower;
    }

    public void setHorsepower(String horsepower) {
        Horsepower = horsepower;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getSignatureImage() {
        return SignatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        SignatureImage = signatureImage;
    }

    public String getFishingGear() {
        return FishingGear;
    }

    public void setFishingGear(String fishingGear) {
        FishingGear = fishingGear;
    }

    public String getMunicipalCode() {
        return MunicipalCode;
    }

    public void setMunicipalCode(String municipalCode) {
        MunicipalCode = municipalCode;
    }

    public String getProvinceCode() {
        return ProvinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        ProvinceCode = provinceCode;
    }

    public String getRegionCode() {
        return RegionCode;
    }

    public void setRegionCode(String regionCode) {
        RegionCode = regionCode;
    }

    public String getRegistered_By() {
        return Registered_By;
    }

    public void setRegistered_By(String registered_By) {
        Registered_By = registered_By;
    }

    public String getLastUpdated_By() {
        return LastUpdated_By;
    }

    public void setLastUpdated_By(String lastUpdated_By) {
        LastUpdated_By = lastUpdated_By;
    }

    public String getDateUpdated() {
        return DateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        DateUpdated = dateUpdated;
    }

    public String getEngineMake2() {
        return EngineMake2;
    }

    public void setEngineMake2(String engineMake2) {
        EngineMake2 = engineMake2;
    }

    public String getSerialNumber2() {
        return SerialNumber2;
    }

    public void setSerialNumber2(String serialNumber2) {
        SerialNumber2 = serialNumber2;
    }

    public String getHorsepower2() {
        return Horsepower2;
    }

    public void setHorsepower2(String horsepower2) {
        Horsepower2 = horsepower2;
    }


}
