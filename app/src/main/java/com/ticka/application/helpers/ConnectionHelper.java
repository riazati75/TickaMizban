package com.ticka.application.helpers;

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

/*
  this class is responsible for sending values to the server and
  receiving the response with Async.
 */
public class ConnectionHelper {

    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET  = "GET";
    public static final String METHOD_HEAD = "HEAD";

    private int timeOut;
    private MultipartFormDataBody body;
    private AsyncHttpPost post;

    /**
     * can be accessed from the outside.
     * @param configUrl url php connection.
     * @param timeOut time out connection.
     */
    public ConnectionHelper(@NonNull String configUrl , int timeOut){
        this.timeOut = timeOut;
        this.post = new AsyncHttpPost(configUrl);
        this.body = new MultipartFormDataBody();
    }

    /**
     * can be accessed from the outside.
     * @param host string value for proxy host.
     * @param port int value for proxy port.
     * @return class
     */
    public ConnectionHelper setProxyRequest(String host , int port){
        this.post.enableProxy(host , port);
        return this;
    }

    /**
     * can be accessed from the outside.
     * @param method string value for request method.
     * @return class
     */
    public ConnectionHelper setMethodRequest(@ConnectionMethod String method){
        this.post.setMethod(method);
        return this;
    }

    /**
     * can be accessed from the outside.
     * @param name string name for header.
     * @param value string value for header.
     * @return class
     */
    public ConnectionHelper setHeaderRequest(String name , String value){
        this.post.setHeader(name , value);
        return this;
    }

    /**
     * can be accessed from the outside.
     * @param key string key for header.
     * @param value string value for header.
     * @return class
     */
    public ConnectionHelper addHeaderRequest(String key , String value){
        this.post.addHeader(key , value);
        return this;
    }

    /**
     * can be accessed from the outside.
     * @param headers for sending to the server.
     * @return class
     */
    public ConnectionHelper addRequest(Headers headers){
        this.body.addPart(new Part(headers));
        return this;
    }

    /**
     * can be accessed from the outside.
     * @param part for sending to the server.
     * @return class
     */
    public ConnectionHelper addRequest(Part part){
        this.body.addPart(part);
        return this;
    }

    /**
     * can be accessed from the outside.
     * @param key string key for sending to the server.
     * @param value string value for sending to the server.
     * @return class
     */
    public ConnectionHelper addStringRequest(String key , String value){
        this.body.addStringPart(key , value);
        return this;
    }

    /**
     * can be accessed from the outside.
     * @param key string key for sending to the server.
     * @param path string path file for sending to the server.
     * @return class
     */
    public ConnectionHelper addFileRequest(String key , String path){
        this.body.addFilePart(key , new File(path));
        return this;
    }

    /**
     * can not be accessed from the outside.
     * sending request to the server.
     * @param onGetResponse interface for get callback.
     */
    private void getString(final OnGetResponse onGetResponse) {

        this.post.setTimeout(timeOut);
        this.post.setBody(body);

        AsyncHttpClient.getDefaultInstance().executeString(post, new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(Exception e, AsyncHttpResponse source, String result) {

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

    /**
     * can not be accessed from the outside.
     * sending request to the server.
     * @param onGetResponse interface for get callback.
     */
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

    /**
     * can be accessed from the outside.
     * @param onGetResponse interface callback.
     */
    public void getStringResponse(final OnGetResponse onGetResponse){

        new Thread(new Runnable() {
            @Override
            public void run() {
                getString(onGetResponse);
            }
        }).start();
    }

    /**
     * can be accessed from the outside.
     * @param onGetResponse interface callback.
     */
    public void getJsonObjectResponse(final OnGetResponse onGetResponse){

        new Thread(new Runnable() {
            @Override
            public void run() {
                getJsonObject(onGetResponse);
            }
        }).start();
    }

    /**
     * can be accessed from the outside.
     * @return method.
     */
    public String getRequestMethod(){
        return this.post.getMethod();
    }

    /**
     * disabled all proxy from request.
     * @return class.
     */
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