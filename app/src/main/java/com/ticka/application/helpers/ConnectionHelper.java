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
import com.ticka.application.core.Logger;

import org.json.JSONObject;

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

    public ConnectionHelper addFileRequest(String key , File file){
        this.body.addFilePart(key , file);
        return this;
    }

    private ConnectionHelper disableProxy(){
        this.post.disableProxy();
        return this;
    }

    public void getStringResponse(final OnStringResponse onStringResponse) {

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

        this.post.setTimeout(timeOut);
        this.post.setBody(body);

        AsyncHttpClient.getDefaultInstance().executeJSONObject(post, new AsyncHttpClient.JSONObjectCallback() {
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

        this.post.setTimeout(timeOut);
        this.post.setBody(body);

        AsyncHttpClient.getDefaultInstance().executeFile(post, fileName , new AsyncHttpClient.FileCallback() {

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

    public String getRequestMethod(){
        return this.post.getMethod();
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
                    ConnectionHelper.METHOD_GET ,
                    ConnectionHelper.METHOD_HEAD ,
                    ConnectionHelper.METHOD_POST
            })
    public @interface ConnectionMethod {}

}