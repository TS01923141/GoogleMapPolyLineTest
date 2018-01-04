package com.example.tyc.googlemappolylinetest.Model.JsonFormat;

/**
 * Created by CarTek on 2018/1/2.
 */

public class Steps {
    Distance distance;
    Duration duration;
    Start_location start_location;
    End_location end_location;
    Polyline polyline;

    public class Polyline{
        String points;
        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
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

    public class Distance{
        String text;
        int value;

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
    }

    public class Duration{
        String text;
        int value;
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
    }

    public class Start_location{
        String lat;
        String lng;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }


    public class End_location{
        String lat;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        String lng;
    }
}
