package com.ticka.application.service;

import android.content.Context;
import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSService extends SMSScanner {

    public static final String ACTION_CODE_RECEIVED = "RegisterCodeIncoming";
    public static final String HEAD_LINE_NUMBER     = "+9810008566";

    @Override
    protected void onMessageReceived(Context context, Intent intent, String phone, String message) {

        if(phoneIsValid(phone)){

            context.sendBroadcast(
                    new Intent(ACTION_CODE_RECEIVED)
                            .putExtra("code", getCode(message))
            );
        }
    }

    private boolean phoneIsValid(String phone){
        return phone.replace(" " , "").equals(HEAD_LINE_NUMBER);
    }

    private String getCode(String message){
        String code = "";
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(message);
        while(m.find()) {
            code = code + m.group();
        }
        return code;
    }
}
