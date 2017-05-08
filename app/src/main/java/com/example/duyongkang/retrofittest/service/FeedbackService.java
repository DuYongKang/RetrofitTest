package com.example.duyongkang.retrofittest.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by duyongkang on 2017/5/2.
 */

public interface FeedbackService {

    @FormUrlEncoded
    @POST("/feedback")
    Call<ResponseBody> sendFeedbackSimple(
            @Field("osName") String osName,
            @Field("osVersion") int osVersion,
            @Field("device") String device,
            @Field("message") String message,
            @Field("userIsATalker") Boolean userIsATalker);

}
