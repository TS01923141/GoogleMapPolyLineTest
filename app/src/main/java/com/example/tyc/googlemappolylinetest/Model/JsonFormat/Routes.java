package com.example.tyc.googlemappolylinetest.Model.JsonFormat;

import java.util.ArrayList;

/**
 * Created by CarTek on 2018/1/2.
 */

public class Routes {
    ArrayList<Legs> legs;
    Overview_polyline overview_polyline;

    public Overview_polyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(Overview_polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public ArrayList<Legs> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Legs> legs) {
        this.legs = legs;
    }

    public class Overview_polyline{
        String points;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }
}
