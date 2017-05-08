package com.example.duyongkang.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by duyongkang on 2017/4/28.
 */

public class DynamicBaseUrlActivity extends AppCompatActivity {

//    public static final String TAG = "CallInstances";
//    private Callback<ResponseBody> downloadCallback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_file_upload);
//
//        downloadCallback = new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d(TAG, "server contacted at: " + call.request().url());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d(TAG, "call failed against the url: " + call.request().url());
//            }
//        };
//
//        // first request
//        FileDownloadService downloadService = ServiceGenerator.create(FileDownloadService.class);
//        Call<ResponseBody> originalCall = downloadService.downloadFileWithFixedUrl();
//        originalCall.enqueue(downloadCallback);
//
//        // change base url
//        ServiceGenerator.changeApiBaseUrl("http://development.futurestud.io/api");
//
//        // new request against new base url
//        FileDownloadService newDownloadService = ServiceGenerator.create(FileDownloadService.class);
//        Call<ResponseBody> newCall = newDownloadService.downloadFileWithFixedUrl();
//        newCall.enqueue(downloadCallback);
//    }
}

