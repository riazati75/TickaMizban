package com.ticka.application.models.callback;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveCallback implements Serializable {

    @SerializedName("succeed")
    @Expose
    private Boolean succeed;

    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    @SerializedName("result")
    @Expose
    private Integer result;

    @SerializedName("errorCode")
    @Expose
    private Object errorCode;

    public Boolean isSuccessful() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}
