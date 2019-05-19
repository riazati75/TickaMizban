package com.ticka.application.models.home;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("city_id")
    @Expose
    private Object cityId;

    @SerializedName("home_status_id")
    @Expose
    private Object homeStatusId;

    @SerializedName("base_capacity")
    @Expose
    private Object baseCapacity;

    @SerializedName("max_capacity")
    @Expose
    private Object maxCapacity;

    @SerializedName("place_area")
    @Expose
    private Object placeArea;

    @SerializedName("home_type")
    @Expose
    private Object homeType;

    @SerializedName("room_count")
    @Expose
    private Object roomCount;

    @SerializedName("single_bed")
    @Expose
    private Object singleBed;

    @SerializedName("double_bed")
    @Expose
    private Object doubleBed;

    @SerializedName("extra_bed")
    @Expose
    private Object extraBed;

    @SerializedName("building_size")
    @Expose
    private Object buildingSize;

    @SerializedName("area_size")
    @Expose
    private Object areaSize;

    @SerializedName("operator_id")
    @Expose
    private Object operatorId;

    @SerializedName("name")
    @Expose
    private Object name;

    @SerializedName("description")
    @Expose
    private Object description;

    @SerializedName("address")
    @Expose
    private Object address;

    @SerializedName("rules")
    @Expose
    private Object rules;

    @SerializedName("phone")
    @Expose
    private Object phone;

    @SerializedName("cellphone")
    @Expose
    private Object cellphone;

    @SerializedName("team_note")
    @Expose
    private Object teamNote;

    @SerializedName("is_removed")
    @Expose
    private Object isRemoved;

    @SerializedName("is_recommended")
    @Expose
    private Object isRecommended;

    @SerializedName("latitude")
    @Expose
    private Object latitude;

    @SerializedName("longitude")
    @Expose
    private Object longitude;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCityId() {
        return cityId;
    }

    public void setCityId(Object cityId) {
        this.cityId = cityId;
    }

    public Object getHomeStatusId() {
        return homeStatusId;
    }

    public void setHomeStatusId(Object homeStatusId) {
        this.homeStatusId = homeStatusId;
    }

    public Object getBaseCapacity() {
        return baseCapacity;
    }

    public void setBaseCapacity(Object baseCapacity) {
        this.baseCapacity = baseCapacity;
    }

    public Object getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Object maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Object getPlaceArea() {
        return placeArea;
    }

    public void setPlaceArea(Object placeArea) {
        this.placeArea = placeArea;
    }

    public Object getHomeType() {
        return homeType;
    }

    public void setHomeType(Object homeType) {
        this.homeType = homeType;
    }

    public Object getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Object roomCount) {
        this.roomCount = roomCount;
    }

    public Object getSingleBed() {
        return singleBed;
    }

    public void setSingleBed(Object singleBed) {
        this.singleBed = singleBed;
    }

    public Object getDoubleBed() {
        return doubleBed;
    }

    public void setDoubleBed(Object doubleBed) {
        this.doubleBed = doubleBed;
    }

    public Object getExtraBed() {
        return extraBed;
    }

    public void setExtraBed(Object extraBed) {
        this.extraBed = extraBed;
    }

    public Object getBuildingSize() {
        return buildingSize;
    }

    public void setBuildingSize(Object buildingSize) {
        this.buildingSize = buildingSize;
    }

    public Object getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(Object areaSize) {
        this.areaSize = areaSize;
    }

    public Object getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Object operatorId) {
        this.operatorId = operatorId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getRules() {
        return rules;
    }

    public void setRules(Object rules) {
        this.rules = rules;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getCellphone() {
        return cellphone;
    }

    public void setCellphone(Object cellphone) {
        this.cellphone = cellphone;
    }

    public Object getTeamNote() {
        return teamNote;
    }

    public void setTeamNote(Object teamNote) {
        this.teamNote = teamNote;
    }

    public Object getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Object isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Object getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Object isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
