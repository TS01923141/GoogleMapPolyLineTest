package com.example.tyc.googlemappolylinetest.Model;

import android.annotation.SuppressLint;

import com.example.tyc.googlemappolylinetest.Model.JsonFormat.JsonData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by CarTek on 2018/1/2.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();

    //更新成功，刷新資料
    @SuppressLint("LongLogTag")
    void onDirectionFinderSuccess(ArrayList points);

    void markPoint(LatLng origin, LatLng destination);
}

