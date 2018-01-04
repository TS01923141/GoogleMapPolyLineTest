package com.example.tyc.googlemappolylinetest.Model.JsonFormat;

import java.util.ArrayList;

/**
 * Created by CarTek on 2018/1/2.
 */

public class JsonData {
    ArrayList<Routes> routes;
    String status;

    public ArrayList<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Routes> routes) {
        this.routes = routes;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
