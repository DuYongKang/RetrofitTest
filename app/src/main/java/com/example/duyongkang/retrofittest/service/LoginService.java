package com.example.duyongkang.retrofittest.service;

import com.example.duyongkang.retrofittest.bean.User;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by duyongkang on 2017/5/10.
 */

public interface LoginService {
    @POST("api/login")
    Call<User> basicLogin();
}
