package com.ticka.application.models;

import com.google.gson.annotations.SerializedName;

public class UploadModel {

    @SerializedName("Name")
    private String name;

    @SerializedName("Type")
    private int type;

    @SerializedName("Size")
    private int size;

    @SerializedName("HasThumbnail")
    private boolean hasThumbnail;

    @SerializedName("Base64Content")
    private String base64Content;

    public UploadModel(String name, int type, int size, boolean hasThumbnail, String base64Content) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.hasThumbnail = hasThumbnail;
        this.base64Content = base64Content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHasThumbnail() {
        return hasThumbnail;
    }

    public void setHasThumbnail(boolean hasThumbnail) {
        this.hasThumbnail = hasThumbnail;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }
}
