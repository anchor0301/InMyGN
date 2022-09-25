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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class mapSeason extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_season);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapSeason.this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        Context context = this;
        // final LatLng Pocheon = new LatLng(37.894936, 127.200344);   // 마커 추가

        Location cityHallLocation = addrToPoint(context);
        final LatLng Pocheon = new LatLng(cityHallLocation.getLatitude(), cityHallLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Pocheon);
        markerOptions.title("포천시청");                                    // 마커 옵션 추가
        googleMap.addMarker(markerOptions);                                 // 마커 등록

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Pocheon, 12));
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);     // 지도 유형 설정
            }
        }); // 구글맵 로딩이 완료되면 카메라 위치 조정
    }

    public static Location addrToPoint(Context context) {
        Location location = new Location("");
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName("창원시/창녕군/합천군", 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            for (int i = 0; i < addresses.size(); i++) {
                Address lating = addresses.get(i);
                System.out.print("성민"+lating);
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

