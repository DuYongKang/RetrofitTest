package com.example.duyongkang.retrofittest;

import com.example.duyongkang.retrofittest.bean.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by duyongkang on 2017/4/28.
 */

public interface UserService {
    @Headers("Cache-Control: max-age=640000")
    @GET("/tasks")
    Call<List<Task>> getTasks();




}
