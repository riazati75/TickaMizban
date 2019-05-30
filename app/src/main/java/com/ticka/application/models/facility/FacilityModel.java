package com.ticka.application.models.facility;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FacilityModel implements Serializable {

    @SerializedName("data")
    @Expose
    private List<FacilityData> data = null;

    public List<FacilityData> getData() {
        return data;
    }

    public void setData(List<FacilityData> data) {
        this.data = data;
    }

}
