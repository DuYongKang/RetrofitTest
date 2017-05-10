package com.example.duyongkang.retrofittest;

import com.example.duyongkang.retrofittest.bean.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

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

    @Multipart
    @POST("api/uploadAlbum")
    Call<ResponseBody> uploadAlbum(
            @Part ("description") RequestBody description,
            @Part List<MultipartBody.Part> files
    );

    @Multipart
    @POST("api/uploadPartMap")
    Call<ResponseBody> uploadPartMap(
            @PartMap Map<String,RequestBody> data,
            @Part MultipartBody.Part photo
            );

    @GET("api")
    Call<User> test();
}
