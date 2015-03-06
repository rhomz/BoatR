package com.da.bfar.apps.boatr;

import com.da.bfar.apps.boatr.DAO.User;

/**
 * Created by BFAR-PC on 3/2/2015.
 */
public class BoatrSingleton {

    private static BoatrSingleton ourInstance = null;
    private FisherFolk fisherFolk;
    private BoatRegistration boatRegistration;
    private User user;

    public static BoatrSingleton getInstance()
    {
        if(ourInstance ==null){
            ourInstance = new BoatrSingleton();
        }
        return ourInstance;
    }

    private BoatrSingleton() {
        this.fisherFolk = new FisherFolk();
        this.boatRegistration = new BoatRegistration();
        this.user = new User();

    }

    public String getFisherFolkName(){
        return this.fisherFolk.getFullname();

    }

    public void setFisherFolkName(String name){
        fisherFolk.setFullname(name);
    }

    public String getFisherFolkAddress(){
        return fisherFolk.getAddress();
    }

    public void setFisherFolkAddress(String address){
        fisherFolk.setAddress(address);
    }

    public String getFisherFolkTaskSequence(){ return fisherFolk.getTaskSequence(); }

    public void setFisherFolkTaskSequence(String taskSequence){ fisherFolk.setTaskSequence(taskSequence);}

    public int getBoatID() {
        return boatRegistration.getID();
    }

    public void setBoatID(int ID) {
        boatRegistration.setID(ID);
    }

    public String getBoatMFVRNo() {
        return boatRegistration.getMFVRNo();
    }

    public void setBoatMFVRNo(String MFVRNo) {
        boatRegistration.setMFVRNo(MFVRNo);
    }

    public String getBoatDate() {
        return boatRegistration.getDate();
    }

    public void setBoatDate(String date) {
        boatRegistration.setDate(date);
    }

    public String getBoatTypeOfRegistration() {
        return boatRegistration.getTypeOfRegistration();
    }

    public void setBoatTypeOfRegistration(String typeOfRegistration) {
        boatRegistration.setTypeOfRegistration(typeOfRegistration);
    }

    public String getBoatCFRNO() {
        return boatRegistration.getCFRNO();
    }

    public void setBoatCFRNO(String CFRNO)  {
        boatRegistration.setCFRNO(CFRNO);
    }

    public String getBoatHomeport() {
        return boatRegistration.getHomeport();
    }

    public void setBoatHomeport(String homeport) {
        boatRegistration.setHomeport(homeport);
    }

    public String getBoatNameOfVessel() {
        return boatRegistration.getNameOfVessel();
    }

    public void setBoatNameOfVessel(String nameOfVessel) {
        boatRegistration.setNameOfVessel(nameOfVessel);
    }

    public String getBoatVesselType() {
        return boatRegistration.getVesselType();
    }

    public void setBoatVesselType(String vesselType) {
        boatRegistration.setVesselType(vesselType);
    }

    public String getBoatPlaceBuilt() {
        return boatRegistration.getPlaceBuilt();
    }

    public void setBoatPlaceBuilt(String placeBuilt) {
        boatRegistration.setPlaceBuilt(placeBuilt);
    }

    public String getBoatYearBuilt() {
        return boatRegistration.getYearBuilt();
    }

    public void setBoatYearBuilt(String yearBuilt) {
        boatRegistration.setYearBuilt(yearBuilt);
    }

    public String getBoatMaterialUsed() {
        return boatRegistration.getMaterialUsed();
    }

    public void setBoatMaterialUsed(String materialUsed) {
        boatRegistration.setMaterialUsed(materialUsed);
    }

    public String getBoatRegLength() {
        return boatRegistration.getRegLength();
    }

    public void setBoatRegLength(String regLength) {
        boatRegistration.setRegLength(regLength);
    }

    public String getBoatTonLength() {
        return boatRegistration.getTonLength();
    }

    public void setBoatTonLength(String tonLength) {
        boatRegistration.setTonLength(tonLength);
    }

    public String getBoatRegBreadth() {
        return boatRegistration.getRegBreadth();
    }

    public void setBoatRegBreadth(String regBreadth) {
        boatRegistration.setRegBreadth(regBreadth);
    }

    public String getBoatTonBreadth() {
        return boatRegistration.getTonBreadth();
    }

    public void setBoatTonBreadth(String tonBreadth)  {
        boatRegistration.setTonBreadth(tonBreadth);
    }

    public String getBoatRegDepth() {
        return boatRegistration.getRegDepth();
    }

    public void setBoatRegDepth(String regDepth)  {
        boatRegistration.setRegDepth(regDepth);
    }

    public String getBoatTonDepth() {
        return boatRegistration.getTonDepth();
    }

    public void setBoatTonDepth(String tonDepth) {
        boatRegistration.setTonDepth(tonDepth);
    }

    public String getBoatGrossTonnage() {
        return boatRegistration.getGrossTonnage();
    }

    public void setBoatGrossTonnage(String grossTonnage) {
        boatRegistration.setGrossTonnage(grossTonnage);
    }

    public String getBoatNetTonnage() {
        return boatRegistration.getNetTonnage();
    }

    public void setBoatNetTonnage(String netTonnage) {
        boatRegistration.setNetTonnage(netTonnage);
    }

    public String getBoatEngineMake() {
        return boatRegistration.getEngineMake();
    }

    public void setBoatEngineMake(String engineMake) {
        boatRegistration.setEngineMake(engineMake);
    }

    public String getBoatSerialNumber() {
        return boatRegistration.getSerialNumber();
    }

    public void setBoatSerialNumber(String serialNumber)  {
        boatRegistration.setSerialNumber(serialNumber);
    }

    public String getBoatHorsepower() {
        return boatRegistration.getHorsepower();
    }

    public void setBoatHorsepower(String horsepower) {

        boatRegistration.setHorsepower(horsepower);
    }

    public String getBoatImagePath() {
        return boatRegistration.getImagePath();
    }

    public void setBoatImagePath(String imagePath) {
        boatRegistration.setImagePath(imagePath);
    }

    public String getBoatSignatureImage() {
        return boatRegistration.getSignatureImage();
    }

    public void setBoatSignatureImage(String signatureImage) {

        boatRegistration.setSignatureImage(signatureImage);
    }

    public String getBoatFishingGear() {return boatRegistration.getFishingGear();
    }

    public void setBoatFishingGear(String fishingGear) {
        boatRegistration.setFishingGear(fishingGear);
    }

    public String getBoatMunicipalCode() {
        return boatRegistration.getMunicipalCode();
    }

    public void setBoatMunicipalCode(String municipalCode) {
        boatRegistration.setMunicipalCode(municipalCode);
    }

    public String getBoatProvinceCode() {
        return boatRegistration.getProvinceCode();
    }

    public void setBoatProvinceCode(String provinceCode) {
        boatRegistration.setProvinceCode(provinceCode);
    }

    public String getBoatRegionCode() {
        return boatRegistration.getRegionCode();
    }

    public void setBoatRegionCode(String regionCode) {
        boatRegistration.setRegionCode(regionCode);
    }

    public String getBoatRegistered_By() {
        return boatRegistration.getRegistered_By();
    }

    public void setBoatRegistered_By(String registered_By) {
        boatRegistration.setRegistered_By(registered_By);
    }

    public String getBoatLastUpdated_By() {
        return boatRegistration.getLastUpdated_By();
    }

    public void setBoatLastUpdated_By(String lastUpdated_By) {
        boatRegistration.setLastUpdated_By(lastUpdated_By);
    }

    public String getBoatDateUpdated() {
        return boatRegistration.getDateUpdated();
    }

    public void setBoatDateUpdated(String dateUpdated) {
        boatRegistration.setDateUpdated(dateUpdated);
    }

    public String getBoatEngineMake2() {
        return boatRegistration.getEngineMake2();
    }

    public void setBoatEngineMake2(String engineMake2) {
        boatRegistration.setEngineMake2(engineMake2);
    }

    public String getBoatSerialNumber2() {
        return boatRegistration.getSerialNumber2();
    }

    public void setBoatSerialNumber2(String serialNumber2)  {
        boatRegistration.setSerialNumber2(serialNumber2);
    }

    public String getBoatHorsepower2() {
        return boatRegistration.getHorsepower2();
    }

    public void setBoatHorsepower2(String horsepower2)  {
        boatRegistration.setHorsepower2(horsepower2);
    }

    public void setUserUsername(String username) { user.setUsername(username); }

    public String getUserUsername() { return user.getUsername();}

    public void setUserPassword(String password) {user.setPassword(password); }

    public String getUserPassword() { return user.getPassword(); }

    public void setUserFullname(String fullname) {user.setFullname(fullname);}

    public String getUserFullname() { return user.getFullname(); }

    public void setUserMunicipalCode(String MunicipalCode) { user.setMunicipalityCode(MunicipalCode); }

    public String getUserMunicipalCode() { return user.getMunicipalityCode(); }

}
