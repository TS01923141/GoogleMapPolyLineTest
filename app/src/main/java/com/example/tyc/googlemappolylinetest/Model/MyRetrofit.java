package com.example.tyc.googlemappolylinetest.Model;

import com.example.tyc.googlemappolylinetest.Model.JsonFormat.JsonData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by CarTek on 2018/1/2.
 */

public class MyRetrofit {
    //查詢資料條件式
    public interface MyDataService{
        //網址後查詢輸入在此
        @GET("directions/json")
        Call<JsonData> getData(@Query("origin") String origin, @Query("destination") String destination, @Query("sensor") String sensor, @Query("mode") String mode);
    }


    // 以Singleton模式建立
    private static MyRetrofit mInstance = new MyRetrofit();
    private MyDataService myDataService;
    private MyRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")//這裡輸入網址
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())//更改執行onResponse的Thread
                .build();

        myDataService = retrofit.create(MyDataService.class);
    }

    public static MyRetrofit getmInstance(){
        return mInstance;
    }
    public MyDataService getAPI(){
        return myDataService;
    }
}
