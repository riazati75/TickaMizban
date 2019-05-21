package com.ticka.application.helpers;

public class BuildingHelper {

    private static BuildingHelper buildingHelper = null;

    private String[] buildingType = {
            "نوع ساختمان",
            "ویلایی",
            "آپارتمان",
            "سویت",
            "خانه",
            "کلبه"
    };

    private int[] buildingTypeId = {
            0,
            1,
            2,
            3,
            4,
            5

    };

    private String[] buildingLocation = {
            "نوع منطقه ای",
            "ساحلی",
            "شهری",
            "جنگلی",
            "کوهستانی",
            "کویری",
            "روستایی"
    };

    private int[] buildingLocationId = {
            00,
            11,
            22,
            33,
            44,
            55,
            66
    };

    public static BuildingHelper getInstance(){

        if(buildingHelper == null){
            buildingHelper = new BuildingHelper();
        }
        return buildingHelper;
    }

    public String getBuildingTypeById(int id){

        for(int i = 0; i < buildingType.length; i++){
            if(buildingTypeId[i] == id){
                return buildingType[i];
            }
        }
        return "";
    }

    public String getBuildingLocationById(int id){

        for(int i = 0; i < buildingLocation.length; i++){
            if(buildingLocationId[i] == id){
                return buildingLocation[i];
            }
        }
        return "";
    }

    public String[] getBuildingType() {
        return buildingType;
    }

    public int[] getBuildingTypeId() {
        return buildingTypeId;
    }

    public String[] getBuildingLocation() {
        return buildingLocation;
    }

    public int[] getBuildingLocationId() {
        return buildingLocationId;
    }
}
