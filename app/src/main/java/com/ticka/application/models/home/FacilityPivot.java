package com.ticka.application.models.home;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilityPivot implements Serializable {

    @SerializedName("home_id")
    @Expose
    private Integer homeId;

    @SerializedName("facility_id")
    @Expose
    private Integer facilityId;

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

}
