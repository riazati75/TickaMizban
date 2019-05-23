package com.ticka.application.models.home;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("home_id")
    @Expose
    private Integer homeId;

    @SerializedName("src")
    @Expose
    private String src;

    @SerializedName("is_removed")
    @Expose
    private Object isRemoved;

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

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Object getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Object isRemoved) {
        this.isRemoved = isRemoved;
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
