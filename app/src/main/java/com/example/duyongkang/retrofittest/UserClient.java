package com.example.duyongkang.retrofittest;

import com.example.duyongkang.retrofittest.bean.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by duyongkang on 2017/5/3.
 */

public interface UserClient {
    @POST("user")
    Call<User> createAccount(@Body User user);

    @Multipart
    @POST("api/upload")
    Call<ResponseBody> uploadPhoto(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part photo
    );

    //new code for multiple files
    @Multipart
    @POST("api/uploadFiles")
    Call<ResponseBody> uploadPhotos(
        @Part MultipartBody.Part file1,
        @Part MultipartBody.Part file2
    );

    @GET("api")
    Call<User> test();
}
