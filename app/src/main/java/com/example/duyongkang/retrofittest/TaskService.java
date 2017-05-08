package com.example.duyongkang.retrofittest;

import com.example.duyongkang.retrofittest.bean.Task;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by duyongkang on 2017/4/28.
 */

public interface TaskService {
    @POST("/tasks")
    Call<Task> createTask(@Body Task task);

    @GET("/tasks")
    Call<List<Task>> getTasks(
            @HeaderMap Map<String, String> headers
    );

//    @GET("/tasks")
//    Call<List<Task>> getTasks(
//            @Query("sort") String order,
//            @Query("page") Integer page);//对于原始数据类型int使用Integer,其他原始类型同上
}
