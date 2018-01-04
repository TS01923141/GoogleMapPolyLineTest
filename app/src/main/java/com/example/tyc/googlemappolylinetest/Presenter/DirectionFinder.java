package com.example.tyc.googlemappolylinetest.Presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.tyc.googlemappolylinetest.Model.DirectionFinderListener;
import com.example.tyc.googlemappolylinetest.Model.JsonFormat.JsonData;
import com.example.tyc.googlemappolylinetest.Model.JsonFormat.Legs;
import com.example.tyc.googlemappolylinetest.Model.JsonFormat.Routes;
import com.example.tyc.googlemappolylinetest.Model.JsonFormat.Steps;
import com.example.tyc.googlemappolylinetest.Model.MyRetrofit;
import com.google.android.gms.maps.model.LatLng;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CarTek on 2018/1/2.
 */

public class DirectionFinder implements IDirectionFinder {
    DirectionFinderListener listener;
    private String origin;
    private String destination;
    JsonData jsonData;

    public DirectionFinder(DirectionFinderListener listener, String origin, String destination) {
        this.listener = listener;
        this.origin = origin;
        this.destination = destination;
    }

    //執行DirectionFinder
    @Override
    public void execute(){
        listener.onDirectionFinderStart();
        Log.e( "execute","Current Thread"+Thread.currentThread().getName());

        Flowable.just(1)
                .subscribeOn(Schedulers.io())//在IO執行緒抓取JSON並轉成List
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer i) throws Exception {
//                        Log.e( "execute.subscribe","Current Thread"+Thread.currentThread().getName());
                        fetchData();
                    }
                });
    }

    //取得Json，將Json傳入parseJson
    @Override
    public void fetchData(){
        Log.e( "fetchData","Current Thread"+Thread.currentThread().getName());
        MyRetrofit.MyDataService myDataService;
        myDataService = MyRetrofit.getmInstance().getAPI();
        Call<JsonData> call = myDataService.getData(origin, destination , "false", "driving");
//        Log.e("WebSite of Json","https://maps.googleapis.com/maps/api/directions/json"+"?origin="+origin+"&destination="+destination+"&GOOGLE_API_KEY"+ GOOGLE_API_KEY);
        Log.e("WebSite of Json","https://maps.googleapis.com/maps/api/directions/json"+"?origin="+origin+"&destination="+destination+"&sensor=false&mode=driving");

        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
//                Log.e( "fetchData.onResponse","Current Thread"+Thread.currentThread().getName());

                jsonData = response.body();

                Log.e("response.status", String.valueOf(response.body().getStatus()));
//                Log.e("routeList.isEmpty", String.valueOf(jsonData==null));

//                listener.onDirectionFinderSuccess(jsonData);
                Flowable.just(jsonData)
//                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<JsonData>() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void accept(JsonData jsonData) throws Exception {
//                                Log.e( "fetchData.onResponse.subscribe","Current Thread"+Thread.currentThread().getName());
//                                listener.onDirectionFinderSuccess(jsonData);
                                parseJson(jsonData);
                            }
                        });

            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e("fetchData:onFailure",t.getMessage());
            }
        });
    }

    //解析Json，分別畫線跟標記起訖點
    @Override
    public void parseJson(JsonData jsonData){
//        Log.e( "parseJson","Current Thread"+Thread.currentThread().getName());
        final ArrayList points = new ArrayList();
        //標示起訖點
        Flowable.just(jsonData)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonData>() {
                    @Override
                    public void accept(JsonData jsonData) throws Exception {
                        LatLng origin = new LatLng(Double.parseDouble(jsonData.getRoutes().get(0).getLegs().get(0).getStart_location().getLat()),
                                Double.parseDouble(jsonData.getRoutes().get(0).getLegs().get(0).getStart_location().getLng()));
                        LatLng destination = new LatLng(Double.parseDouble(jsonData.getRoutes().get(0).getLegs().get(0).getEnd_location().getLat()),
                                Double.parseDouble(jsonData.getRoutes().get(0).getLegs().get(0).getEnd_location().getLng()));
                        listener.markPoint(origin, destination);
                    }
                });

        //畫路徑線(polyLine)
        Flowable.just(jsonData)
                //取得JsonData所有的steps
                .flatMap(new Function<JsonData, Publisher<Routes>>() {
                    @Override
                    public Publisher<Routes> apply(JsonData jsonData) throws Exception {
                        Log.e( "parseJson.flatMap","Current Thread"+Thread.currentThread().getName());
                        return Flowable.fromIterable(jsonData.getRoutes());
                    }
                })
                .flatMap(new Function<Routes, Publisher<Legs>>() {
                    @Override
                    public Publisher<Legs> apply(Routes routes) throws Exception {
                        return Flowable.fromIterable(routes.getLegs());
                    }
                })
                .flatMap(new Function<Legs, Publisher<Steps>>() {
                    @Override
                    public Publisher<Steps> apply(Legs legs) throws Exception {
                        return Flowable.fromIterable(legs.getSteps());
                    }
                })
//                .flatMap(new Function<JsonData, Publisher<Steps>>() {
//                    @Override
//                    public Publisher<Steps> apply(JsonData jsonData) throws Exception {
//                        return Flowable.fromIterable(jsonData.getRoutes().get(0).getLegs().get(0).getSteps());
//                    }
//                })
//                //分次(steps數)畫出兩點之間的線
//                //在此解析polyLine.points轉成arrayList<Latlng>
//                .map(new Function<Steps, ArrayList>() {
//                    @Override
//                    public ArrayList apply(Steps steps) throws Exception {
//                        Log.e( "parseJson.map","Current Thread"+Thread.currentThread().getName());
//                        ArrayList list = (ArrayList) decodePolyline(steps.getPolyline().getPoints());
//                        return list;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())//畫線切回UI執行緒
//                //畫線
//                .subscribe(new Consumer<ArrayList>() {
//                    @Override
//                    public void accept(ArrayList arrayList) throws Exception {
//                        Log.e( "parseJson.subscribe","Current Thread"+Thread.currentThread().getName());
//                        listener.onDirectionFinderSuccess(arrayList);
//                    }
//                });

        ////把所有points存成一個Arraylist後畫線
        ////***************************************************************************************************************************************************************************
                //取得所有polyline.points
                .flatMap(new Function<Steps, Publisher<LatLng>>() {
                    @Override
                    public Publisher<LatLng> apply(Steps steps) throws Exception {
                        //把steps內的polyline.points由String亂碼轉成Latlng存進一個List後回傳
                        List list = decodePolyline(steps.getPolyline().getPoints());
                        //寄出list內的所有points
                        return Flowable.fromIterable(list);
                    }
                })
                //
                .subscribe(new Consumer<LatLng>() {
                    @Override
                    public void accept(LatLng latLng) throws Exception {
                        points.add(latLng);
                    }
                });
        //畫線
        Flowable.just(points)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList>() {
                    @Override
                    public void accept(ArrayList arrayList) throws Exception {
                        listener.onDirectionFinderSuccess(arrayList);
                    }
                });
        ////***************************************************************************************************************************************************************************
    }

    /**
     * 解析polyLine，返回List<Latlng>
     * Method to decode polyline
     * Source : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
    public List decodePolyline(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
