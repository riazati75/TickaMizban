package com.ticka.application.helpers;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.async.http.callback.HttpConnectCallback;
import com.ticka.application.core.Logger;

import java.io.File;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class ConnectionHelper {

    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET  = "GET";
    public static final String METHOD_HEAD = "HEAD";

    private int timeOut;
    private MultipartFormDataBody body;
    private AsyncHttpPost post;
    private Handler handler = new Handler();

    public ConnectionHelper(@NonNull String configUrl , int timeOut){
        this.timeOut = timeOut;
        this.post = new AsyncHttpPost(configUrl);
        this.body = new MultipartFormDataBody();
    }

    public ConnectionHelper setProxyRequest(String host , int port){
        this.post.enableProxy(host , port);
        return this;
    }

    public ConnectionHelper setMethodRequest(@ConnectionMethod String method){
        this.post.setMethod(method);
        return this;
    }

    public ConnectionHelper setHeaderRequest(String name , String value){
        this.post.setHeader(name , value);
        return this;
    }

    public ConnectionHelper addHeaderRequest(String key , String value){
        this.post.addHeader(key , value);
        return this;
    }

    public ConnectionHelper addRequest(Headers headers){
        this.body.addPart(new Part(headers));
        return this;
    }

    public ConnectionHelper addRequest(Part part){
        this.body.addPart(part);
        return this;
    }

    public ConnectionHelper addStringRequest(String key , String value){
        this.body.addStringPart(key , value);
        return this;
    }

    public ConnectionHelper addFileRequest(String key , String path){
        this.body.addFilePart(key , new File(path));
        return this;
    }

    private void getString(final OnGetResponse onGetResponse) {

        this.post.setTimeout(timeOut);
        this.post.setBody(body);

        AsyncHttpClient.getDefaultInstance().executeString(post, new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(final Exception e, AsyncHttpResponse source, final String result) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (e != null){
                            e.printStackTrace();
                            onGetResponse.notConnectToServer();
                        }
                        else if (result.equals("null")){
                            onGetResponse.onNullResponse(); //result is Null
                        }
                        else if (!result.equals("")){
                            onGetResponse.onSuccessResponse(result); //result is JSON callback
                        }
                        else {
                            Logger.Log("Response: empty");
                        }
                    }
                });
            }

            @Override
            public void onProgress(AsyncHttpResponse response, long downloaded, long total) {
                super.onProgress(response, downloaded, total);
                Logger.Log("Response: onProgress");
            }

            @Override
            public void onConnect(AsyncHttpResponse response) {
                super.onConnect(response);
                Logger.Log("Response: onConnect");
            }
        });
    }

    private void getJsonObject(final OnGetResponse onGetResponse) {

        this.post.setTimeout(timeOut);
        this.post.setBody(body);

        AsyncHttpClient.getDefaultInstance().execute(post, new HttpConnectCallback() {
            @Override
            public void onConnectCompleted(Exception e, AsyncHttpResponse result) {

                if (e != null){
                    e.printStackTrace();
                    onGetResponse.notConnectToServer();
                }
                else if (result.toString().equals("null")){
                    onGetResponse.onNullResponse(); //result is Null
                }
                else if (!result.toString().equals("")){
                    onGetResponse.onSuccessResponse(result.toString()); //result is JSON callback
                }
                else {
                    Logger.Log("Response: empty");
                }
            }
        });
    }

    public void getStringResponse(final OnGetResponse onGetResponse){

        new Thread(new Runnable() {
            @Override
            public void run() {
                getString(onGetResponse);
            }
        }).start();
    }

    public void getJsonObjectResponse(final OnGetResponse onGetResponse){

        new Thread(new Runnable() {
            @Override
            public void run() {
                getJsonObject(onGetResponse);
            }
        }).start();
    }

    public String getRequestMethod(){
        return this.post.getMethod();
    }

    private ConnectionHelper disableProxy(){
        this.post.disableProxy();
        return this;
    }

    public interface OnGetResponse {

        void notConnectToServer();

        void onSuccessResponse(String result);

        void onNullResponse();

    }

    @Retention(SOURCE)
    @StringDef(value = {ConnectionHelper.METHOD_GET , ConnectionHelper.METHOD_HEAD , ConnectionHelper.METHOD_POST})
    public @interface ConnectionMethod {}

}