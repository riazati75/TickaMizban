package com.ticka.application.models;

import java.io.Serializable;

public class HomeDataModel implements Serializable {

    private static HomeDataModel homesModel = null;

    public static HomeDataModel getInstance(){
        if(homesModel == null){
            homesModel = new HomeDataModel();
        }
        return homesModel;
    }

    private String homeTitle = "";
    private Integer homeStateId = -1;
    private Integer homeCityId = -1;
    private String homeAddress = "";
    private String phone = "";
    private String homeDescription = "";
    private Integer buildingType = -1;
    private Integer locationType = -1;
    private Integer roomNumber = -1;
    private Integer landArea = -1;
    private Integer buildingArea = -1;
    private String photoArray = "";
    private Integer standardCapacity = -1;
    private Integer maximumCapacity = -1;
    private Integer singleBed = -1;
    private Integer doubleBed = -1;
    private Integer extraBed = -1;
    private String facilitiesArray = "";
    private String facilitiesDescription = "";
    private String rulesArray = "";
    private String ruleDescription = "";

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(Integer buildingType) {
        this.buildingType = buildingType;
    }

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
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

    public String getPhotoArray() {
        return photoArray;
    }

    public void setPhotoArray(String photoArray) {
        this.photoArray = photoArray;
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

    public String getFacilitiesArray() {
        return facilitiesArray;
    }

    public void setFacilitiesArray(String facilitiesArray) {
        this.facilitiesArray = facilitiesArray;
    }

    public String getFacilitiesDescription() {
        return facilitiesDescription;
    }

    public void setFacilitiesDescription(String facilitiesDescription) {
        this.facilitiesDescription = facilitiesDescription;
    }

    public String getRulesArray() {
        return rulesArray;
    }

    public void setRulesArray(String rulesArray) {
        this.rulesArray = rulesArray;
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
                ", photoArray='" + photoArray +
                "', standardCapacity=" + standardCapacity +
                ", maximumCapacity=" + maximumCapacity +
                ", singleBed=" + singleBed +
                ", doubleBed=" + doubleBed +
                ", extraBed=" + extraBed +
                "', facilitiesArray=" + facilitiesArray +
                ", facilitiesDescription='" + facilitiesDescription +
                "', rulesArray=" + rulesArray +
                ", ruleDescription='" + ruleDescription + "'}";
    }
}
