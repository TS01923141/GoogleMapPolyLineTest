package com.example.tyc.googlemappolylinetest.Presenter;

import com.example.tyc.googlemappolylinetest.Model.JsonFormat.JsonData;

/**
 * Created by CarTek on 2018/1/4.
 */

public interface IDirectionFinder {
    //執行DirectionFinder
    void execute();

    //取得Json，將Json傳入parseJson
    void fetchData();

    //解析Json，分別畫線跟標記起訖點
    void parseJson(JsonData jsonData);
}
