package com.example.inmygn;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class mapSeason extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    ArrayList<TourData> Tours;
    ArrayList<Location> Tour_Address;
    Context context = this;
    final String TAG = "LogMainActivity";
    View bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_season);

        bottomSheet = findViewById(R.id.bottomSheet);

        Tours = (ArrayList<TourData>) getIntent().getSerializableExtra("Tour");
        Tour_Address = (ArrayList<Location>) getIntent().getSerializableExtra("Tour_addr");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        //클러스터 받기

        //바텀시트 레이아웃을 BottomSheetBehavior과 연결한다
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        //맵 마커 추가
        updateMarker(Tours, Tour_Address);

        //맵 로딩이 성공한다면
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {  //맵 제한 범위 설정
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.4605301, 128.2131958), 9));//카메라 위치 설정
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(
                        new LatLng(33.986, 125.98), // 남서 방향
                        new LatLng(35.726, 129.20)  // 북동 방향
                ));
                mMap.setMinZoomPreference(9.0f);
                mMap.setMaxZoomPreference(12.0f);
            }
        });


        //맵을 클릭시 행동
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
                public boolean onMarkerClick(@NonNull Marker marker) {

                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Toast.makeText(getApplicationContext(),
                            marker.getTitle(),
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
        });

    }


    private void updateMarker(ArrayList<TourData> Tour, ArrayList<Location> TourAddress) {
        for (int i = 0; i < Tour.size(); i++) {

            Location location = TourAddress.get(i);

            String Title= Tour.get(i).getData_title();
            String Season = Tour.get(i).getSeason();
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .title(Title)
                    .snippet(Season));
        }
    }

}




