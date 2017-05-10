package com.example.duyongkang.retrofittest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duyongkang.retrofittest.service.FileDownloadClient;
import com.example.duyongkang.retrofittest.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by duyongkang on 2017/5/10.
 */

public class DownloadActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST =100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        if(ContextCompat.checkSelfPermission(DownloadActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DownloadActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }

        final String url="https://futurestud.io/images/futurestudio-university-logo.png";

        Button download = (Button) findViewById(R.id.btn_down);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                downloadFile();
                downloadFile(url);
            }
        });
    }

    private void downloadFile(String url){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://futurestud.io");
        Retrofit retrofit = builder.build();
        // TODO: 2017/5/10  get client & call object for the request
        FileDownloadClient fileDownloadClient = retrofit.create(FileDownloadClient.class);
        Call<ResponseBody> call = fileDownloadClient.downloadFileStream(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        boolean success = writeResponseBodyToDisk(response.body());
                        return null;
                    }
                }.execute();
                LogUtils.e("yeah!");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.e("no! :(");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body){
        try {
            File futureStudioIconFile = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "Future Studio Icon.png"
            );
            InputStream inputStream = null;
            OutputStream outputStream =null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownload = 0;

                inputStream=body.byteStream();
                outputStream=new FileOutputStream(futureStudioIconFile);

                while(true){
                    int read = inputStream.read(fileReader);
                    if(read == -1){
                        break;
                    }
                    outputStream.write(fileReader,0,read);

                    fileSizeDownload+=read;
                    LogUtils.e("Future Studio","file download: " + fileSizeDownload +" of" + fileSize);
                }
                outputStream.flush();
                return  true;
            }catch (IOException E){
                return  false;
            }finally {
                if(inputStream != null){
                    inputStream.close();
                }
                if (outputStream!=null){
                    outputStream.close();
                }
            }
        }catch (IOException e){
            return false;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission was granted,yay!DO
                    //the file-related task you need to do.
                }else {
                    //permission denied ,boo! Disable the
                    //functionality that depends on this permission.
                }
                return;
            }
        }
    }

}
