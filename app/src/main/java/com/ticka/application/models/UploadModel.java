package com.ticka.application.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UploadModel {

    @SerializedName("Name")
    @Nullable
    private String name;

    @SerializedName("Type")
    @Nullable
    private double type;

    @SerializedName("Size")
    @Nullable
    private double size;

    @SerializedName("HasThumbnail")
    @Nullable
    private boolean hasThumbnail;

    @SerializedName("Base64Content")
    @Nullable
    private Object base64Content;

    public UploadModel(@Nullable String name, double type, double size, boolean hasThumbnail, @Nullable Object base64Content) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.hasThumbnail = hasThumbnail;
        this.base64Content = base64Content;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getType() {
        return type;
    }

    public void setType(double type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isHasThumbnail() {
        return hasThumbnail;
    }

    public void setHasThumbnail(boolean hasThumbnail) {
        this.hasThumbnail = hasThumbnail;
    }

    public Object getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(Object base64Content) {
        this.base64Content = base64Content;
    }
}
