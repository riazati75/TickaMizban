package com.ticka.application.models.facility;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilityData implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("english_name")
    @Expose
    private Object englishName;

    @SerializedName("description")
    @Expose
    private Object description;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEnglishName() {
        return englishName;
    }

    public void setEnglishName(Object englishName) {
        this.englishName = englishName;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
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
