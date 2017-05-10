package com.example.duyongkang.retrofittest;

import android.text.TextUtils;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by duyongkang on 2017/4/27.
 */

public class ServiceGenerator {

    private static final String BASE_URL = "http://192.168.4.153:3000/";
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);
    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

//    public static void changeApiBaseUrl(String newApiBaseUrl) {
//        apiBaseUrl = newApiBaseUrl;
//
//        builder = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(apiBaseUrl);
//    }

    public static Retrofit retrofit(){
        return retrofit;
    }

    public static <S> S createService(
            Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
    public  static <S> S createService(Class<S> serviceClass,String username,String password){
        if(!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)){
            String autoToken = Credentials.basic(username,password);
            return createService(serviceClass,autoToken);
        }
        return createService(serviceClass,null,null);
    }

    public static <S> S createService(Class<S> serviceClass,final String authToken){
        if(!TextUtils.isEmpty(authToken)){
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if(!httpClient.interceptors().contains(interceptor)){
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit=builder.build();
            }
        }

        return  retrofit.create(serviceClass);
    }
}
