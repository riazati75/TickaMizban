package com.ticka.application.models.home;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeData implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("home_status_id")
    @Expose
    private Integer homeStatusId;
    @SerializedName("base_capacity")
    @Expose
    private Integer baseCapacity;
    @SerializedName("max_capacity")
    @Expose
    private Integer maxCapacity;
    @SerializedName("place_area")
    @Expose
    private Integer placeArea;
    @SerializedName("home_type")
    @Expose
    private Integer homeType;
    @SerializedName("room_count")
    @Expose
    private Integer roomCount;
    @SerializedName("single_bed")
    @Expose
    private Integer singleBed;
    @SerializedName("double_bed")
    @Expose
    private Integer doubleBed;
    @SerializedName("extra_bed")
    @Expose
    private Integer extraBed;
    @SerializedName("building_size")
    @Expose
    private Integer buildingSize;
    @SerializedName("area_size")
    @Expose
    private Integer areaSize;
    @SerializedName("operator_id")
    @Expose
    private Object operatorId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("facility_description")
    @Expose
    private Object facilityDescription;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("rules")
    @Expose
    private String rules;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("cellphone")
    @Expose
    private String cellphone;
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
    @SerializedName("facility")
    @Expose
    private List<Facility> facility = null;
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;
    @SerializedName("rule")
    @Expose
    private List<Rule> rule = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getHomeStatusId() {
        return homeStatusId;
    }

    public void setHomeStatusId(Integer homeStatusId) {
        this.homeStatusId = homeStatusId;
    }

    public Integer getBaseCapacity() {
        return baseCapacity;
    }

    public void setBaseCapacity(Integer baseCapacity) {
        this.baseCapacity = baseCapacity;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getPlaceArea() {
        return placeArea;
    }

    public void setPlaceArea(Integer placeArea) {
        this.placeArea = placeArea;
    }

    public Integer getHomeType() {
        return homeType;
    }

    public void setHomeType(Integer homeType) {
        this.homeType = homeType;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
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

    public Integer getBuildingSize() {
        return buildingSize;
    }

    public void setBuildingSize(Integer buildingSize) {
        this.buildingSize = buildingSize;
    }

    public Integer getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(Integer areaSize) {
        this.areaSize = areaSize;
    }

    public Object getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Object operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getFacilityDescription() {
        return facilityDescription;
    }

    public void setFacilityDescription(Object facilityDescription) {
        this.facilityDescription = facilityDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
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

    public List<Facility> getFacility() {
        return facility;
    }

    public void setFacility(List<Facility> facility) {
        this.facility = facility;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    public List<Rule> getRule() {
        return rule;
    }

    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }

}
