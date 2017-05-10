package com.example.duyongkang.retrofittest;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.duyongkang.retrofittest.bean.Task;
import com.example.duyongkang.retrofittest.bean.User;
import com.example.duyongkang.retrofittest.service.FeedbackService;
import com.example.duyongkang.retrofittest.service.FileUploadService;
import com.example.duyongkang.retrofittest.service.LoginService;
import com.example.duyongkang.retrofittest.util.ErrorUtils;
import com.example.duyongkang.retrofittest.util.FileUtils;
import com.example.duyongkang.retrofittest.util.LogUtils;
import com.example.duyongkang.retrofittest.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST =100;
    private int PICK_IMAGE_FROM_GALLERY_REQUEST=1;

    private int PICK_IMAGE_FORM_GALLERY_ALBUM=3;

    private int PICK_IMAGE_FROM_PART_MAP=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(MainActivity.this);
        setContentView(R.layout.activity_main);
        //获取临时权限
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }

        Button uploadButton = (Button) findViewById(R.id.btn_upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(
                        Intent.createChooser(intent,"Select Picture"),
                        PICK_IMAGE_FROM_GALLERY_REQUEST
                );
            }
        });

        Button uploadAlbumButton = (Button) findViewById(R.id.btn_album);
        uploadAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(
                        Intent.createChooser(intent,"Select Picture"),
                        PICK_IMAGE_FORM_GALLERY_ALBUM
                );
            }
        });

        Button  partMapButton = (Button) findViewById(R.id.btn_part_map);
        partMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(
                        Intent.createChooser(intent,"Select Picture"),
                        PICK_IMAGE_FROM_PART_MAP
                );
            }
        });

        Button download= (Button) findViewById(R.id.btn_download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DownloadActivity.class);
                startActivity(intent);
            }
        });

        Button login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginService loginService =
                        ServiceGenerator.createService(LoginService.class, "user", "secretpassword");
                Call<User> call = loginService.basicLogin();
                call.enqueue(new Callback<User >() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            LogUtils.e("test1");
                            // user object available
                        } else {
                            LogUtils.e("test2");
                            // error response, no access to resource?
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        // something went completely south (like no internet connection)
                        LogUtils.e( t.getMessage());
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });



        GitHubClient client = ServiceGenerator.createService(GitHubClient.class);

        Call<List<GitHubRepo>> call =
                client.reposForUser("fs-opensource");
//
//        call.enqueue(new Callback<List<GitHubRepo>>() {
//            @Override
//            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
//                // The network call was a success and we got a response
//                // TODO: use the repository list and display it
//                LogUtils.e("debug",response);
//                LogUtils.e("debug",response.body().toString());
//                LogUtils.e("debug",response.raw());
//            }
//
//            @Override
//            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
//                // the network call was a failure
//                // TODO: handle error
//            }
//        });


//        //传递java对象
//        Task task = new Task(1, "my task title");
//
//
//        TaskService taskService = ServiceGenerator.createService(TaskService.class);
//        Map<String, String> map = new HashMap<>();
//        map.put("Page", String.valueOf(1));
//        if (BuildConfig.DEBUG) {
//            map.put("Accept", "application/vnd.yourapi.v1.full+json");
//            map.put("User-Agent", "Future Studio Debug");
//        }
//        else {
//            map.put("Accept", "application/json");
//            map.put("Accept-Charset", "utf-8");
//            map.put("User-Agent", "Future Studio Release");
//        }
//

//        Call<Task> callTask = taskService.createTask(task);
//        Call<List<Task>> callt = taskService.getTasks(map);
//        callt.enqueue(new Callback<List<Task>>() {
//            @Override
//            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
//                LogUtils.e("debug",response);
//                LogUtils.e("debug",response.raw());
//            }
//
//            @Override
//            public void onFailure(Call<List<Task>> call, Throwable t) {
//
//            }
//        });

        UserClient taskService = ServiceGenerator.createService(UserClient.class);
                Call<User> callTask = taskService.test();
        callTask.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(MainActivity.this,"yeah!",Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this,"nooo!",Toast.LENGTH_LONG);
            }
        });


//        callTask.enqueue(new Callback<Task>() {
//            @Override
//            public void onResponse(Call<Task> call, Response<Task> response) {
//                LogUtils.e("debug",response);
//                LogUtils.e("debug",response.raw());
//            }
//
//            @Override
//            public void onFailure(Call<Task> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && requestCode == RESULT_FIRST_USER && data != null){
            LogUtils.e(data);
            ClipData clipData=data.getClipData();
            ArrayList<Uri> fileUris=new ArrayList<>();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                ClipData.Item item = clipData.getItemAt(i);
                Uri uri=item.getUri();
                fileUris.add(uri);
            }
            uploadFiles(fileUris.get(0),fileUris.get(1));

//            Uri uri= data.getData();
//            uploadFile(uri);

//            uploadAlbum(fileUris);
        }else if(requestCode == PICK_IMAGE_FORM_GALLERY_ALBUM){
            LogUtils.e(data);
            ClipData clipData=data.getClipData();
            ArrayList<Uri> fileUris=new ArrayList<>();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                ClipData.Item item = clipData.getItemAt(i);
                Uri uri=item.getUri();
                fileUris.add(uri);
            }
//            uploadFiles(fileUris.get(0),fileUris.get(1));
//
////            Uri uri= data.getData();
////            uploadFile(uri);
            LogUtils.e("执行");
            uploadAlbum(fileUris);
        }else {
            if(data!=null){
                Uri uri= data.getData();
                uploadPartMap(uri);
            }
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



    private void sendFeedbackFormSimple(@NonNull String message) {
        // create the service to make the call, see first Retrofit blog post
        FeedbackService taskService = ServiceGenerator.createService(FeedbackService.class);

        // create flag if message is especially long
        boolean userIsATalker = (message.length() > 200);

        Call<ResponseBody> call = taskService.sendFeedbackSimple(
                "Android",
                android.os.Build.VERSION.SDK_INT,
                Build.MODEL,
                message,
                userIsATalker
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



    private void uploadFile(Uri fileUri) {
        final EditText name = (EditText) findViewById(R.id.input_description);
        // create upload service client
        UserClient service =
                ServiceGenerator.createService(UserClient.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
//        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(getContentResolver().getType(fileUri)),
//                        file
//                );
        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM,name.getText().toString());
        File originalFile = FileUtils.getFile(this,fileUri);
        RequestBody filePart= RequestBody.create(
                                    MediaType.parse(getContentResolver().getType(fileUri)),
                                   originalFile
        );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        LogUtils.e("步骤1");
        // finally, execute the request
        Call<ResponseBody> call = service.uploadPhoto(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this,"yeah!",Toast.LENGTH_LONG);
                if (response.isSuccessful()) {
                    // use response data and do some fancy stuff :)
                } else {
                    // parse the response body …
                    APIError error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMessage());
                }
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"nooo :(",Toast.LENGTH_LONG);
                Log.e("Upload error:", t.getMessage());
            }
        });
    }


    private void uploadFiles(Uri fileUri,Uri fileUri2) {

        // create upload service client
        UserClient service =
                ServiceGenerator.createService(UserClient.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
//        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(getContentResolver().getType(fileUri)),
//                        file
//                );
        File originalFile = FileUtils.getFile(this,fileUri);
        RequestBody filePart= RequestBody.create(
                MediaType.parse(getContentResolver().getType(fileUri)),
                originalFile
        );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        LogUtils.e("步骤1");
        // finally, execute the request
        Call<ResponseBody> call = service.uploadPhotos(prepareFilePart("picture",fileUri),prepareFilePart("parana",fileUri2));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this,"yeah!",Toast.LENGTH_LONG);
                if (response.isSuccessful()) {
                    // use response data and do some fancy stuff :)
                } else {
                    // parse the response body …
                    APIError error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMessage());
                }
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"nooo :(",Toast.LENGTH_LONG);
                Log.e("Upload error:", t.getMessage());
            }
        });
    }


    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private void uploadAlbum(List<Uri> fileUris){
        final EditText name = (EditText) findViewById(R.id.input_description);
        // create upload service client
        UserClient service =
                ServiceGenerator.createService(UserClient.class);


        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < fileUris.size(); i++) {
            parts.add(prepareFilePart(""+i,fileUris.get(i)));
        }
        // finally, execute the request
        Call<ResponseBody> call = service.uploadAlbum(createPartFromString(name.getText().toString()),parts );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this,"yeah!",Toast.LENGTH_LONG);
                if (response.isSuccessful()) {
                    // use response data and do some fancy stuff :)
                } else {
                    // parse the response body …
                    APIError error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMessage());
                }
                Log.v("Upload", "success");
                LogUtils.e("success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"nooo :(",Toast.LENGTH_LONG);
                Log.e("Upload error:", t.getMessage());
            }
        });
    }


    private void uploadPartMap(Uri fileUri) {
        final EditText name = (EditText) findViewById(R.id.input_description);
        final EditText photographer = (EditText) findViewById(R.id.input_photographer);
        final EditText year= (EditText) findViewById(R.id.input_year);
        final EditText location = (EditText) findViewById(R.id.input_location);
        // create upload service client
        UserClient service =
                ServiceGenerator.createService(UserClient.class);

        Map<String,RequestBody> partmap= new HashMap<>();
        partmap.put("client",createPartFromString("android"));
        partmap.put("secret",createPartFromString("hunter2"));
        if (!TextUtils.isEmpty(name.getText().toString())){
            partmap.put("description",createPartFromString(name.getText().toString()));
        }
        if (!TextUtils.isEmpty(photographer.getText().toString())) {
            partmap.put("photographer", createPartFromString(photographer.getText().toString()));
        }
        if (!TextUtils.isEmpty(year.getText().toString())) {
            partmap.put("year", createPartFromString(year.getText().toString()));
        }
        if (!TextUtils.isEmpty(location.getText().toString())) {
            partmap.put("location", createPartFromString(location.getText().toString()));
        }


        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        LogUtils.e("步骤1");
        // finally, execute the request
        Call<ResponseBody> call = service.uploadPartMap(partmap, prepareFilePart("photo",fileUri));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this,"yeah!",Toast.LENGTH_LONG);
                if (response.isSuccessful()) {
                    // use response data and do some fancy stuff :)
                } else {
                    // parse the response body …
                    APIError error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.getMessage());
                }
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"nooo :(",Toast.LENGTH_LONG);
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

}
