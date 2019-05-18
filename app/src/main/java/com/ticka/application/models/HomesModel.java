package com.ticka.application.models;

import java.io.Serializable;

public class HomesModel implements Serializable {

    private static HomesModel homesModel = null;

    private String d = "";

    public static HomesModel getInstance(){
        if(homesModel == null){
            homesModel = new HomesModel();
        }
        return homesModel;
    }

    private HomesModel(){

    }




}
