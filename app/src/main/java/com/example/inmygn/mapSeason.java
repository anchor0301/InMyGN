package com.example.inmygn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class mapSeason extends AppCompatActivity implements OnMapReadyCallback {
    private String key = "WugO7Pgnqoa7fJuWbJ4nMaaIh%2Bvw2l%2F%2FaGF7MGgIyBl4vRTVhumNtnrqkL%2BJDxh94rXo%2BR8DgPREJu8h6AVefQ%3D%3D";
    private String address = "http://apis.data.go.kr/6480000/gyeongnamtourseason/gyeongnamtourseasonlist";
    private ListView listView;
    private Button btnData;
    private GoogleMap mMap;
    ArrayAdapter adapter;

    String urlAddress = address + "?serviceKey=" + key + "&pageNo=1&numOfRows=55&resultType=json" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_season);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapSeason.this);


        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        Context context = this;
        // final LatLng Pocheon = new LatLng(37.894936, 127.200344);   // 마커 추가

                try {
                    URL url = new URL(urlAddress);
                    InputStream is =  url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line + "\n");
                        line = reader.readLine();
                    }

                    String jsonData = buffer.toString();

                    // jsonData를 먼저 JSONObject 형태로 바꾼다.
                    JSONObject obj = new JSONObject(jsonData);

                    // obj의 "gyeongnamtourseasonlist"의 JSONObject를 추출
                    JSONObject gyeongnamtourseasonlist = (JSONObject)obj.get("gyeongnamtourseasonlist");


                    // gyeongnamtourseasonlist "body"의 JSONObject를 추출
                    JSONObject SeasonBody = (JSONObject)gyeongnamtourseasonlist.get("body");

                    // SeasonBody "item"의 JSONObject를 추출
                    JSONObject SeasonItems = (JSONObject)SeasonBody.get("items");



                    // boxOfficeResult의 JSONObject에서 "dailyBoxOfficeList"의 JSONArray 추출
                    JSONArray dailyBoxOfficeList = (JSONArray)SeasonItems.get("item");

                    for (int i = 0; i < dailyBoxOfficeList.length(); i++) {

                        JSONObject temp = dailyBoxOfficeList.getJSONObject(i);

                        //타이틀 제목
                        String data_title = temp.getString("user_address");


                        Location cityHallLocation = addrToPoint(context,data_title);
                        final LatLng Pocheon = new LatLng(cityHallLocation.getLatitude(), cityHallLocation.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(Pocheon);
                        markerOptions.title(data_title);                                    // 마커 옵션 추가
                        googleMap.addMarker(markerOptions);                                 // 마커 등록
                    }





                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

    }

    public static Location addrToPoint(Context context,String locationName) {
        Location location = new Location("");
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(locationName, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            for (int i = 0; i < addresses.size(); i++) {
                Address lating = addresses.get(i);
                location.setLatitude(lating.getLatitude());
                location.setLongitude(lating.getLongitude());
            }
        }
        return location;
    }

//
//    @Override
//    public void onMapReady(final GoogleMap googleMap) {
//        mMap = googleMap;
//        Context context = this;
//
//
//
//        double latitude = 35.1655662;
//        double longitude = 128.0987056;
//        LatLng YN = new LatLng(latitude, longitude);
//
//        MarkerOptions markerOptions = new MarkerOptions();         // 마커 생성
//        markerOptions.position(YN)
//                .snippet("한국의 자랑")         // 마커 설명
//                .title("연암공대");                         // 마커 제목
//        mMap.addMarker(markerOptions);
//
//
///***
// - zoom Size
// 1: 세계
// 5: 대륙
// 10: 도시
// 15: 거리
// 20: 건물
//
//
//
// - setMapType
//
// https://developers.google.com/maps/documentation/android-sdk/reference/com/google/android/libraries/maps/GoogleMap?hl=ko#public-void-setmaptype-int-type
//
// MAP_TYPE_NORMAL: 기본 지도.
// MAP_TYPE_SATELLITE: 위성 이미지입니다.
// MAP_TYPE_HYBRID: 도로와 라벨이 포함된 위성 이미지입니다.
// MAP_TYPE_TERRAIN: 지형 데이터입니다.
// MAP_TYPE_NONE: 기본 지도 타일이 없습니다.
// ***/
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(YN, 13));
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL );     // 지도 유형 설정
//
//    }


}

