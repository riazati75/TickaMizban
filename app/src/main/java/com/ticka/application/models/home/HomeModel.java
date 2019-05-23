package com.ticka.application.models.home;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeModel implements Serializable {

    @SerializedName("data")
    @Expose
    private List<HomeData> data = null;

    public List<HomeData> getData() {
        return data;
    }

    public void setData(List<HomeData> data) {
        this.data = data;
    }

}
