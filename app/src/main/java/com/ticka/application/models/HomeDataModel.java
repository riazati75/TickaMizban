package com.ticka.application.models;

import java.io.Serializable;
import java.util.List;

public class HomeDataModel implements Serializable {

    private static HomeDataModel homesModel = null;

    public static HomeDataModel getInstance(){
        if(homesModel == null){
            homesModel = new HomeDataModel();
        }
        return homesModel;
    }

    private String homeTitle;
    private Integer homeStateId;
    private Integer homeCityId;
    private String homeAddress;
    private String homeDescription;
    private String buildingType;
    private String locationType;
    private Integer roomNumber;
    private Integer landArea;
    private Integer buildingArea;
    private String photoLocation1;
    private String photoLocation2;
    private String photoLocation3;
    private Integer standardCapacity;
    private Integer maximumCapacity;
    private Integer pricePerPersonAdded;
    private Integer singleBed;
    private Integer doubleBed;
    private Integer extraBed;
    private String capacityDescription;
    private List<Integer> possibilitiesList;
    private String possibilitiesDescription;
    private List<Integer> rulesList;
    private String ruleDescription;

    public String getHomeTitle() {
        return homeTitle;
    }

    public void setHomeTitle(String homeTitle) {
        this.homeTitle = homeTitle;
    }

    public Integer getHomeStateId() {
        return homeStateId;
    }

    public void setHomeStateId(Integer homeStateId) {
        this.homeStateId = homeStateId;
    }

    public Integer getHomeCityId() {
        return homeCityId;
    }

    public void setHomeCityId(Integer homeCityId) {
        this.homeCityId = homeCityId;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomeDescription() {
        return homeDescription;
    }

    public void setHomeDescription(String homeDescription) {
        this.homeDescription = homeDescription;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getLandArea() {
        return landArea;
    }

    public void setLandArea(Integer landArea) {
        this.landArea = landArea;
    }

    public Integer getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(Integer buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getPhotoLocation1() {
        return photoLocation1;
    }

    public void setPhotoLocation1(String photoLocation1) {
        this.photoLocation1 = photoLocation1;
    }

    public String getPhotoLocation2() {
        return photoLocation2;
    }

    public void setPhotoLocation2(String photoLocation2) {
        this.photoLocation2 = photoLocation2;
    }

    public String getPhotoLocation3() {
        return photoLocation3;
    }

    public void setPhotoLocation3(String photoLocation3) {
        this.photoLocation3 = photoLocation3;
    }

    public Integer getStandardCapacity() {
        return standardCapacity;
    }

    public void setStandardCapacity(Integer standardCapacity) {
        this.standardCapacity = standardCapacity;
    }

    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public Integer getPricePerPersonAdded() {
        return pricePerPersonAdded;
    }

    public void setPricePerPersonAdded(Integer pricePerPersonAdded) {
        this.pricePerPersonAdded = pricePerPersonAdded;
    }

    public Integer getSingleBed() {
        return singleBed;
    }

    public void setSingleBed(Integer singleBed) {
        this.singleBed = singleBed;
    }

    public Integer getDoubleBed() {
        return doubleBed;
    }

    public void setDoubleBed(Integer doubleBed) {
        this.doubleBed = doubleBed;
    }

    public Integer getExtraBed() {
        return extraBed;
    }

    public void setExtraBed(Integer extraBed) {
        this.extraBed = extraBed;
    }

    public String getCapacityDescription() {
        return capacityDescription;
    }

    public void setCapacityDescription(String capacityDescription) {
        this.capacityDescription = capacityDescription;
    }

    public List<Integer> getPossibilitiesList() {
        return possibilitiesList;
    }

    public void setPossibilitiesList(List<Integer> possibilitiesList) {
        this.possibilitiesList = possibilitiesList;
    }

    public String getPossibilitiesDescription() {
        return possibilitiesDescription;
    }

    public void setPossibilitiesDescription(String possibilitiesDescription) {
        this.possibilitiesDescription = possibilitiesDescription;
    }

    public List<Integer> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<Integer> rulesList) {
        this.rulesList = rulesList;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public String parsData() {
        return "\n\nHomeDataModel{" +
                "homeTitle='" + homeTitle +
                "', homeStateId=" + homeStateId +
                ", homeCityId=" + homeCityId +
                ", homeAddress='" + homeAddress +
                "', homeDescription='" + homeDescription +
                "', buildingType='" + buildingType +
                "', locationType='" + locationType +
                "', roomNumber=" + roomNumber +
                ", landArea=" + landArea +
                ", buildingArea=" + buildingArea +
                ", photoLocation1='" + photoLocation1 +
                "', photoLocation2='" + photoLocation2 +
                "', photoLocation3='" + photoLocation3 +
                "', standardCapacity=" + standardCapacity +
                ", maximumCapacity=" + maximumCapacity +
                ", pricePerPersonAdded=" + pricePerPersonAdded +
                ", singleBed=" + singleBed +
                ", doubleBed=" + doubleBed +
                ", extraBed=" + extraBed +
                ", capacityDescription='" + capacityDescription +
                "', possibilitiesList=" + possibilitiesList +
                ", possibilitiesDescription='" + possibilitiesDescription +
                "', rulesList=" + rulesList +
                ", ruleDescription='" + ruleDescription + "'}";
    }
}
