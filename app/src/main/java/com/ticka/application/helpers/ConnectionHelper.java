package com.ticka.application.helpers;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import com.koushikdutta.async.http.body.Part;
import com.ticka.application.core.Logger;

import org.json.JSONObject;

import java.io.File;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class ConnectionHelper {

    public static final String POST = "POST";
    public static final String GET = "GET";

    private AsyncHttpRequest asyncHttpRequest;
    private MultipartFormDataBody body;
    private Handler handler;

    public ConnectionHelper(@NonNull String configUrl , int timeOut , @ConnectionMethod String requestMethod){
        initMethod(configUrl,requestMethod);
        this.asyncHttpRequest.setTimeout(timeOut);
        this.body = new MultipartFormDataBody();
        this.handler = new Handler();
    }

    private void initMethod(String configUrl , @ConnectionMethod String requestMethod){

        switch(requestMethod){

            case POST:
                this.asyncHttpRequest = new AsyncHttpPost(configUrl);
                break;

            case GET:
                this.asyncHttpRequest = new AsyncHttpGet(configUrl);
                break;
        }
    }

    public void disableProxy(){
        this.asyncHttpRequest.disableProxy();
    }

    public void setProxyRequest(String host , int port){
        this.asyncHttpRequest.enableProxy(host , port);
    }

    public void setMethodRequest(@ConnectionMethod String method){
        this.asyncHttpRequest.setMethod(method);
    }

    public void setHeaderRequest(String name , String value){
        this.asyncHttpRequest.setHeader(name , value);
    }

    public void addHeaderRequest(String key , String value){
        this.asyncHttpRequest.addHeader(key , value);
    }

    public void setFollowRedirect(boolean followRedirect){
        this.asyncHttpRequest.setFollowRedirect(followRedirect);
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

    public ConnectionHelper addFileRequest(String key , File file){
        this.body.addFilePart(key , file);
        return this;
    }

    public String getRequestMethod(){
        return this.asyncHttpRequest.getMethod();
    }

    public void getStringResponse(final OnStringResponse onStringResponse) {

        this.asyncHttpRequest.setBody(body);

        AsyncHttpClient.getDefaultInstance().executeString(asyncHttpRequest, new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(final Exception e, AsyncHttpResponse source, final String result) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (e != null){
                            e.printStackTrace();
                            onStringResponse.notConnectToServer();
                        }
                        else if (result.equals("null")){
                            onStringResponse.onNullResponse(); //result is Null
                        }
                        else if (!result.equals("")){
                            onStringResponse.onSuccessResponse(result); //result is JSON callback
                        }
                        else {
                            Logger.Log("Response: empty");
                        }
                    }
                });
            }

            @Override
            public void onProgress(AsyncHttpResponse response, long downloaded, long total) {

            }

            @Override
            public void onConnect(AsyncHttpResponse response) {

            }
        });
    }

    public void getJsonObjectResponse(final OnJsonObjectResponse onJsonObjectResponse) {

        this.asyncHttpRequest.setBody(body);

        AsyncHttpClient.getDefaultInstance().executeJSONObject(asyncHttpRequest, new AsyncHttpClient.JSONObjectCallback() {
            @Override
            public void onCompleted(final Exception e, AsyncHttpResponse source, final JSONObject result) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (e != null){
                            e.printStackTrace();
                            onJsonObjectResponse.notConnectToServer();
                        }
                        else if (result.toString().equals("null")){
                            onJsonObjectResponse.onNullResponse();
                        }
                        else if (!result.toString().equals("")){
                            onJsonObjectResponse.onSuccessResponse(result);
                        }
                        else {
                            Logger.Log("Response: empty");
                        }
                    }
                });
            }

            @Override
            public void onProgress(AsyncHttpResponse response, long downloaded, long total) {

            }

            @Override
            public void onConnect(AsyncHttpResponse response) {

            }
        });
    }

    public void getFileResponse(final OnFileResponse onFileResponse , String fileName) {

        this.asyncHttpRequest.setBody(body);

        AsyncHttpClient.getDefaultInstance().executeFile(asyncHttpRequest, fileName , new AsyncHttpClient.FileCallback() {

            @Override
            public void onCompleted(final Exception e, AsyncHttpResponse source, final File result) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (e != null){
                            e.printStackTrace();
                            onFileResponse.notConnectToServer();
                        }
                        else if (result.toString().equals("null")){
                            onFileResponse.onNullResponse();
                        }
                        else {
                            Logger.Log("Response: empty");
                        }
                    }
                });
            }

            @Override
            public void onProgress(final AsyncHttpResponse response, final long downloaded, final long total) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSuccessful = response.code() == 200;
                        onFileResponse.onProgressDownload(isSuccessful , downloaded , total);
                    }
                });
            }

            @Override
            public void onConnect(final AsyncHttpResponse response) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSuccessful = response.code() == 200;
                        onFileResponse.onConnected(isSuccessful , response.message());
                    }
                });
            }
        });
    }

    public interface OnStringResponse {

        void notConnectToServer();

        void onSuccessResponse(String result);

        void onNullResponse();

    }

    public interface OnJsonObjectResponse {

        void notConnectToServer();

        void onSuccessResponse(JSONObject jsonObject);

        void onNullResponse();

    }

    public interface OnFileResponse {

        void notConnectToServer();

        void onConnected(boolean isSuccessful , String message);

        void onProgressDownload(boolean isSuccessful , long downloaded, long total);

        void onNullResponse();

    }

    @Retention(SOURCE)
    @StringDef(
            value = {
                    ConnectionHelper.GET,
                    ConnectionHelper.POST
            })
    @interface ConnectionMethod {}
}