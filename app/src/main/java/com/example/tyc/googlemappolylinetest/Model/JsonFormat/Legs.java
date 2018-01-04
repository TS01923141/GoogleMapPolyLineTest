package com.example.tyc.googlemappolylinetest.Model.JsonFormat;

import java.util.ArrayList;

/**
 * Created by CarTek on 2018/1/2.
 */

public class Legs {
    ArrayList<Steps> steps;
    String start_address;
    Start_location start_location;
//    LatLng start_location;
    String end_address;
//    LatLng end_location;
    End_location end_location;
    Distance distance;
    public class Start_location{
        String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        String lng;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
    public class End_location{
        String lat;
        String lng;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;

        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    Duration duration;

    public class Distance{
        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        int value;
    }
    public class Duration{
        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        int value;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }


    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public Start_location getStart_location() {
        return start_location;
    }

    public void setStart_location(Start_location start_location) {
        this.start_location = start_location;
    }

    public End_location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(End_location end_location) {
        this.end_location = end_location;
    }
}
