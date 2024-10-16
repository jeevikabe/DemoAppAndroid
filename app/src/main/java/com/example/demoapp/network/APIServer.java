package com.example.demoapp.network;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONArrayRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.interfaces.FileDownloadInterface;
import com.example.demoapp.interfaces.UploadFileAndResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import networkapi.Links;
import okhttp3.Response;

/**
 * Class used to do networking requests, Volley library is used
 */
@SuppressWarnings("HardCodedStringLiteral")
public class APIServer {
    //    private final AppAuth auth;
//    private final LocalStorage localStorage;
    String apiResponse;
    int apiStatusCode;
    Context context;
    APIResponseListener apiResponseListener;
    private HashMap<String, String> headers;
    public FileDownloadInterface fileDownloadInterface;
    UploadFileAndResponseListener uploadFileAndResponseListener;


    private static final String TAG = APIServer.class.getSimpleName();


    public APIServer(Context context) {

        this.context = context;
        AndroidNetworking.initialize(context);
        AndroidNetworking.setParserFactory(new GsonParserFactory());
        new Links(context).setLinks(context);
//        localStorage = new LocalStorage(context);
//
//
//        auth = new AppAuthDAO(context).getAppAuth();
//        /*=============Don't Forget to initialize Auth Headers Here and use same in AndroidNetworking.addHeaders(yourHeader)*/
//        headers = new HashMap<String, String>();
//        headers.put("Content-Type", "application/json; charset=utf-8");
//
//        AppAuth auth = new AppAuthDAO(context).getAppAuth();
//        if (auth != null) {
//            String credentials = auth.getUsername() + ":" + auth.getPassword() + ":" + auth.getDeviceId();
//
//            String basicAuth = "Basic "
//                    + Base64.encodeToString(credentials.getBytes(),
//                    Base64.NO_WRAP);
//            headers.put("Authorization", basicAuth);
//        }
    }


    /**
     * GET Method to get all data from server
     *
     * @param url                 link
     * @param apiResponseListener APIResponseListener
     */
    public void getString(String url, final APIResponseListener apiResponseListener) {
        apiResponse = null;
        apiStatusCode = 0;
        this.apiResponseListener = apiResponseListener;
        AndroidNetworking.get(url).setPriority(Priority.HIGH).setTag(context).addHeaders(headers).build()
                .getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
                        //Log.d("nk", "API GET response " + response);
                        apiResponse = response.toString();
                        apiStatusCode = okHttpResponse.code();
                        if (apiResponseListener != null) {
                            handleResponse(apiStatusCode, apiResponse);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if ((error != null) && (error.getResponse() != null)) {
                            Log.d("nk", "onError " + error.getMessage());
                            apiStatusCode = error.getErrorCode();
                            apiResponse = error.getMessage();
                            handleResponse(apiStatusCode, apiResponse);
                        }
                    }
                });
    }


    /**
     * POST method to post jsonObject to server
     *
     * @param url                 link
     * @param jsonObject          JSONObject
     * @param apiResponseListener APIResponseListener
     */
    public void postJSON(String url, JSONObject jsonObject, final APIResponseListener apiResponseListener) {
        apiResponse = null;
        apiStatusCode = 0;
        this.apiResponseListener = apiResponseListener;
        AndroidNetworking.post(url).addJSONObjectBody(jsonObject).setTag(context).setPriority(Priority.HIGH).addHeaders(headers).build().setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsOkHttpResponseAndJSONObject(new OkHttpResponseAndJSONObjectRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, JSONObject response) {

                        //Log.d("nk", "API POST response " + response.toString());
                        apiResponse = response.toString();
                        apiStatusCode = okHttpResponse.code();
                        if (apiResponseListener != null) {
                            handleResponse(apiStatusCode, apiResponse);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if ((anError != null) && (anError.getResponse() != null)) {
                            Log.d("nk", "onError " + anError.getErrorCode());
                            apiStatusCode = anError.getErrorCode();
                            apiResponse = anError.getMessage();
                            handleResponse(apiStatusCode, apiResponse);
                        }
                    }
                });
    }

    public void postString(String url,
                           String key,
                           String stringToSend,
                           final APIResponseListener apiResponseListener) {
        apiResponse = null;
        apiStatusCode = 0;
        this.apiResponseListener = apiResponseListener;
        AndroidNetworking.post(url)
                .addBodyParameter(key, stringToSend).setTag(context).setPriority(Priority.HIGH)
                .addHeaders(headers).build().setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsOkHttpResponseAndJSONObject(new OkHttpResponseAndJSONObjectRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, JSONObject response) {

                        //Log.d("nk", "API POST response " + response.toString());
                        apiResponse = response.toString();
                        apiStatusCode = okHttpResponse.code();
                        if (apiResponseListener != null) {
                            handleResponse(apiStatusCode, apiResponse);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        if ((anError != null) && (anError.getResponse() != null)) {
                            Log.d("nk", "onError " + anError.getErrorCode());
                            apiStatusCode = anError.getErrorCode();
                            apiResponse = anError.getMessage();
                            handleResponse(apiStatusCode, apiResponse);
                        }
                    }
                });
    }


    /**
     * POST method to post jsonArray to server
     *
     * @param url                 link
     * @param jsonArray           JSONArray
     * @param apiResponseListener APIResponseListener
     */
    public void postJsonArray(String url, JSONArray jsonArray, final APIResponseListener apiResponseListener) {
        apiResponse = null;
        apiStatusCode = 0;
        this.apiResponseListener = apiResponseListener;
        AndroidNetworking.post(url).addJSONArrayBody(jsonArray).setTag(context).setPriority(Priority.HIGH).addHeaders(headers).build().setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .getAsOkHttpResponseAndJSONArray(new OkHttpResponseAndJSONArrayRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, JSONArray response) {
                        //Log.d("nk", "API POST response " + response.toString());
                        apiResponse = response.toString();
                        apiStatusCode = okHttpResponse.code();
                        if (apiResponseListener != null) {
                            handleResponse(apiStatusCode, apiResponse);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        if ((anError != null) && (anError.getResponse() != null)) {
                            Log.d("nk", "onError " + anError.getErrorCode());
                            apiStatusCode = anError.getErrorCode();
                            apiResponse = anError.getMessage();
                            handleResponse(apiStatusCode, apiResponse);
                        }
                    }
                });
    }


    /**
     * PUT method to update data in server
     *
     * @param url                 link
     * @param jsonObject          JSONObject
     * @param apiResponseListener APIResponseListener
     */
    public void putJsonObject(String url, JSONObject jsonObject, final APIResponseListener apiResponseListener) {
        apiResponse = null;
        apiStatusCode = 0;
        this.apiResponseListener = apiResponseListener;
        AndroidNetworking.post(url).addJSONObjectBody(jsonObject).setTag(context).addHeaders(headers).setPriority(Priority.HIGH).build().setAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

                Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);

                Log.d(TAG, " bytesSent : " + bytesSent);

                Log.d(TAG, " bytesReceived : " + bytesReceived);

                Log.d(TAG, " isFromCache : " + isFromCache);
            }
        }).getAsOkHttpResponseAndJSONObject(new OkHttpResponseAndJSONObjectRequestListener() {
            @Override
            public void onResponse(Response okHttpResponse, JSONObject response) {

                //Log.d("nk", "API POST response " + response.toString());
                apiResponse = response.toString();
                apiStatusCode = okHttpResponse.code();
                if (apiResponseListener != null) {
                    handleResponse(apiStatusCode, apiResponse);
                }
            }

            @Override
            public void onError(ANError anError) {


                if ((anError != null) && (anError.getResponse() != null)) {
                    Log.d("nk", "onError " + anError.getErrorCode());
                    apiStatusCode = anError.getErrorCode();
                    apiResponse = anError.getMessage();
                    handleResponse(apiStatusCode, apiResponse);
                }
            }
        });
    }


    /**
     * PUT method to update data in server
     *
     * @param url                 link
     * @param jsonArray           JSONObject
     * @param apiResponseListener APIResponseListener
     */
    public void putJsonArray(String url, JSONArray jsonArray, final APIResponseListener apiResponseListener) {
        apiResponse = null;
        apiStatusCode = 0;
        this.apiResponseListener = apiResponseListener;
        AndroidNetworking.post(url).addJSONArrayBody(jsonArray).setTag(context).addHeaders(headers).setPriority(Priority.HIGH).build().setAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {

                Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);

                Log.d(TAG, " bytesSent : " + bytesSent);

                Log.d(TAG, " bytesReceived : " + bytesReceived);

                Log.d(TAG, " isFromCache : " + isFromCache);
            }
        }).getAsOkHttpResponseAndJSONArray(new OkHttpResponseAndJSONArrayRequestListener() {
            @Override
            public void onResponse(Response okHttpResponse, JSONArray response) {
                //Log.d("nk", "API POST response " + response.toString());
                apiResponse = response.toString();
                apiStatusCode = okHttpResponse.code();
                if (apiResponseListener != null) {
                    handleResponse(apiStatusCode, apiResponse);
                }
            }

            @Override
            public void onError(ANError anError) {


                if ((anError != null) && (anError.getResponse() != null)) {
                    Log.d("nk", "onError " + anError.getErrorCode());
                    apiStatusCode = anError.getErrorCode();
                    apiResponse = anError.getMessage();
                    handleResponse(apiStatusCode, apiResponse);
                }
            }
        });
    }


    /**
     * Method to handle response finally
     *
     * @param statusCode
     * @param response
     */
    private void handleResponse(int statusCode, String response) {
//        if (statusCode == 401) {
////            localStorage.put(LocalStorage.FIRST_USE, "true");
//            Toast.makeText(context, "Authentication Failure", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            ((Activity) context).startActivity(intent);
//            ((Activity) context).finish();
//        } else {
        apiResponseListener.onResponse(statusCode, response);
//        }
    }

    public void uploadImageWithObject(JSONObject jsonObject, String uniqueParams, String url,
                                      String imageFileName, String imageFilePath,
                                      final APIResponseListener responseListener,
                                      final UploadFileAndResponseListener uploadFileAndResponseListener) {
        final long bytesUploaded = 0, totalBytes = 0;
        apiResponse = null;
        apiStatusCode = 0;
        this.apiResponseListener = responseListener;
        this.uploadFileAndResponseListener = uploadFileAndResponseListener;
        AndroidNetworking.upload(url)
                .setPriority(Priority.HIGH)
                .addHeaders(headers)
                .addMultipartParameter(uniqueParams, String.valueOf(jsonObject))
                .addMultipartFile(imageFileName, new File(imageFilePath))
                .setTag(this)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent,
                                           long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        Log.d(TAG, "bytesUploaded : " + bytesUploaded + " totalBytes : " + totalBytes);
                        Log.d(TAG, "setUploadProgressListener isMainThread : "
                                + String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
                        uploadFileAndResponseListener.onResponse((float) bytesUploaded, (float) totalBytes);
                    }
                }).getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
                        if (apiResponseListener != null) {
                            handleResponse(okHttpResponse.code(), response);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (apiResponseListener != null) {
                            handleResponse(anError.getErrorCode(), anError.getErrorBody());
                        }
                    }
                });
    }

    public void uploadObjectWithMultipart(JSONObject jsonObject, String uniqueParams, String url,
                                          // String imageFileName, String imageFilePath,
                                          final APIResponseListener responseListener,
                                          final UploadFileAndResponseListener uploadFileAndResponseListener) {
        final long bytesUploaded = 0, totalBytes = 0;
        apiResponse = null;
        apiStatusCode = 0;
        this.apiResponseListener = responseListener;
        this.uploadFileAndResponseListener = uploadFileAndResponseListener;
        AndroidNetworking.upload(url)
                .setPriority(Priority.HIGH)
                .addHeaders(headers)
                .addMultipartParameter(uniqueParams, String.valueOf(jsonObject))
                //.addMultipartFile(imageFileName, new File(imageFilePath))
                .setTag(this)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent,
                                           long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                })
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        Log.d(TAG, "bytesUploaded : " + bytesUploaded + " totalBytes : " + totalBytes);
                        Log.d(TAG, "setUploadProgressListener isMainThread : "
                                + String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
                        uploadFileAndResponseListener.onResponse((float) bytesUploaded, (float) totalBytes);
                    }
                }).getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
                        if (apiResponseListener != null) {
                            handleResponse(okHttpResponse.code(), response);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (apiResponseListener != null) {
                            handleResponse(anError.getErrorCode(), anError.getErrorBody());
                        }
                    }
                });
    }


    public void downloadFile(String url, final String dirPath, final String fileName, final FileDownloadInterface fileDownloadInterface) {


//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("Content-Type", "application/json; charset=utf-8");
//
//        Auth auth =  new GsonBuilder().create().fromJson(localStorage.get(LocalStorage.AUTH), Auth.class);
//        if (auth != null)
//        {
//            String credentials =
//                    auth.getUsername() + ":" +  localStorage.get(LocalStorage.LOCAL_PASSWORD) +
//                            ":"+ auth.getImei() ;
//
//
//            String basicAuth = "Basic "
//                    + Base64.encodeToString(credentials.getBytes(),
//                    Base64.NO_WRAP);
//            headers.put("Authorization", basicAuth);
//        }
        this.fileDownloadInterface = fileDownloadInterface;
        AndroidNetworking.download(url, dirPath, fileName)
                .setTag(context)
                .addHeaders(headers)
                .setPriority(Priority.HIGH)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress

                        fileDownloadInterface.onDownloadProgress(bytesDownloaded, totalBytes);
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
                        fileDownloadInterface.onDownloadComplete(new File(dirPath + "/" + fileName), true);
                    }

                    @Override
                    public void onError(ANError error) {
                        fileDownloadInterface.onDownloadComplete(new File(dirPath + "/" + fileName), false);
                    }
                });
    }

    public GlideUrl loadImageFromServer(String url) {
        return new GlideUrl(url,
                new LazyHeaders.Builder()
                        .addHeader("Authorization", headers.get("Authorization"))
                        .build());
    }
}
