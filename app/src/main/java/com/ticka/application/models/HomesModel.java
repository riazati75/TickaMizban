package com.ticka.application.models;

import java.io.Serializable;

public class HomesModel implements Serializable {

    private static HomesModel homesModel = null;

    public static HomesModel getInstance(){
        if(homesModel == null){
            homesModel = new HomesModel();
        }
        return homesModel;
    }

    private String homeTitle;
    private Integer homeStateId;
    private Integer homeCityId;
    private String homeAddress;
    private String homeDescription;
    private String buildingType;
    private String locationType;
    private String buildingTip;
    private String roomNubmer;
    private Integer landArea;
    private Integer buildingArea;
    private String photoLocation1;
    private String photoLocation2;
    private String photoLocation3;
    private Integer standardCapacity;
    private Integer maximumCapacity;
    private Integer pricePerPersonAdded;
    private Integer singleBed;
    private Integer doubleBed;
    private Integer matsAdded;
    private String capacityDescription;
    private String possibilitiesDescriptiopn;
    private String ruleDescriptiopn;


}
