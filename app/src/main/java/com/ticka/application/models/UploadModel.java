package com.ticka.application.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UploadModel implements Serializable {

    @SerializedName("Name")
    private String Name;

    @SerializedName("Type")
    private double Type;

    @SerializedName("Size")
    private double Size;

    @SerializedName("HasThumbnail")
    private boolean HasThumbnail;

    @SerializedName("Base64Content")
    private String Base64Content;

    public UploadModel() {

    }

    public UploadModel(@Nullable String Name, double Type, double Size, boolean HasThumbnail, @Nullable String Base64Content) {
        this.Name = Name;
        this.Type = Type;
        this.Size = Size;
        this.HasThumbnail = HasThumbnail;
        this.Base64Content = Base64Content;
    }

    @Nullable
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public double getType() {
        return Type;
    }

    public void setType(double type) {
        this.Type = type;
    }

    public double getSize() {
        return Size;
    }

    public void setSize(double size) {
        this.Size = size;
    }

    public boolean isHasThumbnail() {
        return HasThumbnail;
    }

    public void setHasThumbnail(boolean hasThumbnail) {
        this.HasThumbnail = hasThumbnail;
    }

    public String getBase64Content() {
        return Base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.Base64Content = base64Content;
    }
}
