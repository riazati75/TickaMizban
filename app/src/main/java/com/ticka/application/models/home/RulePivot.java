package com.ticka.application.models.home;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RulePivot implements Serializable {

    @SerializedName("home_id")
    @Expose
    private Integer homeId;

    @SerializedName("rule_id")
    @Expose
    private Integer ruleId;

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

}
