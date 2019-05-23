
package com.ticka.application.models.rules;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RuleData implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("home_id")
    @Expose
    private Integer homeId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("is_removed")
    @Expose
    private Integer isRemoved;

    @SerializedName("created_at")
    @Expose
    private Object createdAt;

    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public RuleData(Integer id, Integer homeId, String description, Integer isRemoved, Object createdAt, Object updatedAt) {
        this.id = id;
        this.homeId = homeId;
        this.description = description;
        this.isRemoved = isRemoved;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Integer isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}
