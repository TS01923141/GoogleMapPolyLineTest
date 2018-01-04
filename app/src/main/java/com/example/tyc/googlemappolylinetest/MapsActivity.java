package com.example.tyc.googlemappolylinetest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyc.googlemappolylinetest.Model.DirectionFinderListener;
import com.example.tyc.googlemappolylinetest.Model.JsonFormat.JsonData;
import com.example.tyc.googlemappolylinetest.Presenter.DirectionFinder;
import com.example.tyc.googlemappolylinetest.Presenter.IDirectionFinder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


/*
執行流程
omMapReady:檢查權限
sendRequestTest:取得起訖點 or sendRequest:取得輸入的起訖點
directionFinder.execute:執行onDirectionFinderStart
onDirectionFinderStart:檢查是否有舊的線，有的話刪除
directionFinder.fetchData:取得JSON
directionFinder.parseJson:解析JSON，分別取得PolyLine跟起訖點
onDirectionFinderSuccess:畫線
markPoint:畫起訖點
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    private ProgressDialog progressDialog;
    IDirectionFinder directionFinder;
    Polyline polyline;
    Marker originMark;
    Marker destinationMark;
    TextView edOrigin, edDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        edOrigin = findViewById(R.id.origin);
        edDestination = findViewById(R.id.destination);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //檢查權限
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(25.0487345,
                        121.51423060000002), 15));
//        sendRequestTest();
//        sendRequestTest2();
    }

    private void sendRequest(String origin, String destination) {
        //執行取得Path
        directionFinder = new DirectionFinder(this, origin, destination);
        directionFinder.execute();
    }

    //*************************************************************************************************************************************************************************************************************************
    private void sendRequestTest() {
//        LatLng origin = new LatLng(25.0487345, 121.51423060000002);
//        LatLng destination = new LatLng(25.0339639, 121.56447219999995);
        String origin = "103台北市中正區鄭州路8號";
        String destination = "台北市信義區信義路五段台北101大樓";
        //執行取得Path
        ////經緯度用法
//        directionFinder = new DirectionFinder(this,
//                String.valueOf(origin.latitude) + "," + String.valueOf(origin.longitude) ,
//                String.valueOf(destination.latitude) + "," + String.valueOf(destination.longitude));
        //地址用法
        directionFinder = new DirectionFinder(this, origin, destination);
        directionFinder.execute();
    }
    //*************************************************************************************************************************************************************************************************************************

    //*************************************************************************************************************************************************************************************************************************
    private void sendRequestTest2() {
        String origin = "103台北市中正區鄭州路8號";
        String destination = "100台北市中正區寶慶路32之1號";
        //執行取得Path
        //地址用法
        directionFinder = new DirectionFinder(this, origin, destination);
        directionFinder.execute();
    }
    //*************************************************************************************************************************************************************************************************************************
    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_REQUEST:
                if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }

    @Override
    public void onDirectionFinderStart() {
        //顯示進度條
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);
        //刪除原本的polyLine、mark
        if (originMark != null) originMark.remove();
        if (destinationMark != null) destinationMark.remove();
        //**********************************************************
        if (polyline != null) polyline.remove();
        //**********************************************************
    }

    //畫線
    @SuppressLint("LongLogTag")
    @Override
    public void onDirectionFinderSuccess(ArrayList points) {
        PolylineOptions polylineOptions = new PolylineOptions();
        Log.e( "MapsActivity.onDirectionFinderSuccess","Current Thread"+Thread.currentThread().getName());
        //隱藏進度條
        progressDialog.dismiss();

        Log.e("points is null?", String.valueOf(points.isEmpty()));
        Log.e("points.size", String.valueOf(points.size()));
        polylineOptions.addAll(points);
        polylineOptions.width(5);
        polylineOptions.color(Color.BLUE);
        polylineOptions.geodesic(true);

        if (polylineOptions!=null){
            polyline = mMap.addPolyline(polylineOptions);
        } else {
            Toast.makeText(getApplicationContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
        }
    }
    //畫起始點
    @Override
    public void markPoint(LatLng origin, LatLng destination){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(origin);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        originMark = mMap.addMarker(markerOptions);

        markerOptions.position(destination);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        destinationMark = mMap.addMarker(markerOptions);
    }

    public void findPath(View view) {
        TextView edOrigin = findViewById(R.id.origin);
        TextView edDestination = findViewById(R.id.destination);

        String origin = edOrigin.getText().toString();
        String destination = edDestination.getText().toString();

        sendRequest(origin, destination);
    }
}
