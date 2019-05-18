package com.ticka.application.helpers;

import android.content.Context;

import com.ticka.application.utils.SPUtils;

public class UserHelper {

    private static UserHelper userHelper;
    private SPUtils spUtils;

    private static final String KEY_USER_IS_JOIN  = "userIsJoin";
    private static final String KEY_USER_PHONE    = "userPhone";
    private static final String KEY_USER_TOKEN    = "userToken";
    private static final String KEY_REFRESH_TOKEN = "refreshToken";

    public static UserHelper getInstance(Context context){
        if(userHelper == null){
            userHelper = new UserHelper(context);
        }
        return userHelper;
    }

    private UserHelper(Context context) {
        spUtils = SPUtils.getInstance(context);
    }

    public void setUserJoined(boolean isJoin){
        spUtils.writeBoolean(
                KEY_USER_IS_JOIN,
                isJoin
        );
    }

    public boolean isUserJoined(){
        return spUtils.readBoolean(
                KEY_USER_IS_JOIN,
                false
        );
    }

    public void setUserPhone(String phone){
        spUtils.writeString(
                KEY_USER_PHONE,
                phone
        );
    }

    public String getUserPhone(){
        return spUtils.readString(
                KEY_USER_PHONE,
                ""
        );
    }

    public void setUserToken(String token){
        spUtils.writeString(
                KEY_USER_TOKEN,
                token
        );
    }

    public String getUserToken(){
        return spUtils.readString(
                KEY_USER_TOKEN,
                ""
        );
    }

    public void setRefreshToken(String token){
        spUtils.writeString(
                KEY_REFRESH_TOKEN,
                token
        );
    }

    public String getRefreshToken(){
        return spUtils.readString(
                KEY_REFRESH_TOKEN,
                ""
        );
    }



}
